package com.xhn;

import com.xhn.util.DateUtils;

import javax.sound.midi.Soundbank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author: 86188
 * @date: 2021/3/25
 * @desc
 */
public class Test {

    public static void main(String[] args) {
        Date date = new Date();
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        //DateUtils.to(now,"yyyy-MM-dd hh:mm:ss");
    }
}
