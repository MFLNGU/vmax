package com.xhn.util;

import java.util.regex.Pattern;

/**
 * @author: 86188
 * @date: 2021/3/25
 * @desc
 */
public class ValidUtils {

    /**
     * /*判断邮箱格式:
     * ^\\w+：邮箱可以以数字或字母开始，出现一次或者多次
     * ([-+.]\\w+)*：（后面可以跟着-号、+号或者.号在拼接数字或字母一次
     * 或多次），括号中的内容可以出现零次或者多次
     *
     * @param email
     * @return
     * @：中间拼接上@符号 \\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$：这部分和前面部分类似
     * 例如邮箱：abc-def+ghi.jkl.mno@pq.com.cn
     * 因为不同的邮箱服务器，域名的命名规则是有差异的，这个是比较通用的一种
     */
    public static boolean isEmail(String email) {
        if (email == null) {
            return false;
        }

        return Pattern.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", email);
    }

    /**
     * 通过正则表达式判断该字符串是否为手机格式
     *
     * @param phoneNum
     * @return
     */
    public static boolean isPhone(String phoneNum) {
        if (phoneNum == null) {
            return false;
        }
        //验证规则，手机号第一位为1，后面十位为数字
        return Pattern.matches("^1(\\d{10})$", phoneNum);
    }

    /**
     * 判断身份证号码是否有效
     * <p>
     * 身份证分为15位身份证和18位身份证号码
     * 18位身份证号：xxxxxx yyyy MM dd *** 0     十八位
     * 15位身份证号：xxxxxx yy   MM dd **  0     十五位
     * 首先身份证是以1-9开头，前6位表示区域：^[1-9]/d{5}
     * 年的前两位：(18|19|([23]\d))
     * 年的后两位： \d{2}
     * 月份：((0[1-9])|(10|11|12))
     * 天：(([0-2][1-9])|10|20|30|31)
     * 三位顺序码：\d{3}
     * 两位顺序码：\d{2}
     * 校验码：[0-9Xx]
     * 十八位： ^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$
     * 十五位： ^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$
     *
     * @param idCard
     * @return
     */
    public static boolean isIdCard(String idCard) {
        if (idCard == null) {
            return false;
        }
        return Pattern.matches("(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\" +
                "d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$)", idCard);
    }

}
