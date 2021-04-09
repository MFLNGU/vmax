package com.xhn.config;/**
 * @author: 86188
 * @date: 2021/3/28
 * @desc
 */

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @program: vmax
 * @description: redis配置类
 * @author: mfl
 * @create: 2021-03-28 08:56
 **/
@Configuration
public class RedisConfig {

    @Value("${redis.nodes}")
    private String redisNodes;

    @Value("${nodes}")
    private String nodes;


    /**
     * @return
     */
    @Bean
    public JedisCluster jedisCluster() {
        Set<HostAndPort> nodes = getRedisNodes();
        return new JedisCluster(nodes);

    }

    private Set<HostAndPort> getRedisNodes() {
        Set<HostAndPort> nodesSet = new HashSet<>();
        String[] strNode = redisNodes.split(",");
        for (String redisNode : strNode) {
            String host = redisNode.split(":")[0];
            int port = Integer.parseInt(redisNode.split(":")[1]);
            nodesSet.add(new HostAndPort(host, port));
        }
        return nodesSet;
    }

    @Bean
    public RedissonClient getConfig() {
        Config config = new Config();
        List<String> nodes = getNodes();
        nodes.stream().forEach(node -> {
            config.useClusterServers().addNodeAddress(node);
        });
        return Redisson.create(config);
    }


    private List<String> getNodes() {
        String[] nodeArray = nodes.split(",");
        return Arrays.asList(nodeArray);
    }

}
