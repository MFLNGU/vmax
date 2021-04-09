package com.xhn.util.common;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author: 86188
 * @date: 2021/3/25
 * @desc
 * * 描述: Twitter的分布式自增ID雪花算法snowflake (Java版)
 *  * snowflake的结构如下(每部分用-分开):
 *  *
 *  * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000
 *  * 第一位为未使用，接下来的41位为毫秒级时间(41位的长度可以使用69年)，
 *  * 然后是5位datacenterId和5位workerId(10位的长度最多支持部署1024个节点） ，
 *  * 最后12位是毫秒内的计数（12位的计数顺序号支持每个节点每毫秒产生4096个ID序号）
 *  *
 *  * 一共加起来刚好64位，为一个Long型。(转换成字符串长度为18)
 *  *
 *  * snowflake生成的ID整体上按照时间自增排序，
 *  * 并且整个分布式系统内不会产生ID碰撞（由datacenter和workerId作区分，每台服务器启动调用构造方法），并且效率较高。
 *  * 据说：snowflake每秒能够产生26万个ID。
 *
 */
public class IDWorker {
    private static final String WORKER_KEY = "id_generator_by_snow";
    // ==============================Fields===========================================
    /**
     * 开始时间截 (2021-01-14)
     */
    private final long twepoch = 1610614875157L;

    /**
     * 机器id所占的位数
     */
    private final long workerIdBits = 10L;

    /**
     * 数据标识id所占的位数
     */
    //private final long datacenterIdBits = 5L;

    /**
     * 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /**
     * 支持的最大数据标识id，结果是31
     */
    //private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    /**
     * 序列在id中占的位数
     */
    private final long sequenceBits = 12L;

    /**
     * 机器ID向左移12位
     */
    private final long workerIdShift = sequenceBits;

    /**
     * 数据标识id向左移17位(12+5)
     */
    private final long datacenterIdShift = sequenceBits + workerIdBits;

    /**
     * 时间截向左移22位(5+5+12)
     */
    private final long timestampLeftShift = sequenceBits + workerIdBits;
    //private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    /**
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * 工作机器ID(0~31)
     */
    private long workerId;

    /**
     * 数据中心ID(0~31)
     */
    //private long datacenterId;

    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence = 0L;

    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    /**
     * 每一部分占用的位数
     */
    private final static long SEQUENCE_BIT = 12; //序列号占用的位数
    private final static long MACHINE_BIT = 8;   //机器标识占用的位数，256个机器
    private final static long DATACENTER_BIT = 2;//数据中心占用的位数，4个数据中心

    private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

    //==============================Constructors=====================================

    /**
     * 无参构造
     */
    public IDWorker() {
        RedisTemplate redisTemplate = (RedisTemplate) SpringContextUtils.getBean("redisTemplate");
        Long workNum = redisTemplate.opsForValue().increment(WORKER_KEY, 1);
        workerId = workNum == null ? 0 : workNum % 1024;
    }

    /**
     * 构造函数
     *
     * @param workerId 工作ID (0~31)
     *                 //* @param datacenterId 数据中心ID (0~31)
     */
    public IDWorker(long workerId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
       /* if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }*/
        this.workerId = workerId;
        //this.datacenterId = datacenterId;
    }


    /**
     * 启动调用该构造方法，因为每个服务器的数据中心和机器码不同保证分布式下生成的id也是唯一的
     *
     * @param datacenterId
     * @param machineId
     *//*
    public IDWorker(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than "+MAX_DATACENTER_NUM+" or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than "+MAX_MACHINE_NUM+" or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }*/

    // ==============================Methods==========================================

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        //获取当前时间戳
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - twepoch) << timestampLeftShift) //
                // | (datacenterId << datacenterIdShift) //
                | (workerId << workerIdShift) //
                | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }
}
