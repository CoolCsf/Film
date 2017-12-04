package com.tool.util;


import android.text.TextUtils;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 文件描述：数据工具类
 * Created by sf.chen on 2017/2/24.
 */
public class DataUtils {
    //判断数据是否为null
    public static <T> T checkNotNull(T reference, String errorMessage) {
        if (null == reference)
            throw new NullPointerException(errorMessage);
        else return reference;
    }

    public static <T> T checkNotNull(T reference) {
        if (null == reference)
            throw new NullPointerException();
        else return reference;
    }

    //判断字符串是否为空
    public static boolean checkStrNotNull(String str) {
        return !TextUtils.isEmpty(str);
    }

    /**
     * 根据出生日期计算年龄
     *
     * @param birthDay
     * @return 未来日期返回0
     * @throws Exception
     */
    private static int getAge(Date birthDay) {

        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            return 0;
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                age--;
            }
        }
        age = age > 0 ? age : 0;
        return age;
    }

    /**
     * 根据出生日期计算年龄
     *
     * @param strBirthDay 字符串型日期
     * @param format      日期格式 "1999-10-10","yyyy-MM-dd"
     * @return 未来日期返回0
     * @throws Exception
     */
    public static int getAge(String strBirthDay, String format) {
        DateFormat df = new SimpleDateFormat(format);
        Date birthDay = null;
        try {
            birthDay = df.parse(strBirthDay);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            return getAge(birthDay);
        }
    }

    // 保留两位小数
    public static double toBigDecimal(double value) {
        long l1 = Math.round(value * 100); //四舍五入
        double ret = l1 / 100.0; //注意:使用 100.0 而不是 100
        return ret;
    }

    /**
     * 根据“-”截取字符串
     *
     * @param str 字符串
     * @return String  【】
     */
    public static String[] splitString(String str) {
        String[] arr = new String[2];
        arr = str.split("-");
        Log.e("splitString", Double.valueOf(arr[0]) + "");
        return arr;
    }
}
