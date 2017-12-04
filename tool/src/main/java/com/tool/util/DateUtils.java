package com.tool.util;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author Administrator
 */
@SuppressLint("SimpleDateFormat")
public class DateUtils {

//    private static StringBuffer sbTime;
//    private static String now, year, month, day, hour, min, ss;
//    private static SimpleDateFormat sdf = new SimpleDateFormat(
//            "yyyyMMddHHmmssSSS");
//
//    private static String resultString = null;
//    private static String resultTrimData = null;
//
//    public static DecimalFormat df = new DecimalFormat("0.00");
//    private static String TAG = "DateUtils";
//
//    public DateUtils() {
//    }

    // 获取系统时间2015-11-08 15:50:30
    public static String getNowDateTime2() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");// 设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    //返回系统日期，如08-06
    public static String getNowMonthAndDay() {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd");// 设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    //返回系统日期，如08-06
    public static String getNowYear() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy");// 设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    // 获取系统时间2015-11-08 15:50:30
    public static String getNowDateTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    // 获取系统时间20 15 11 08 15 50 30
    public static String getYMD() {
        SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");// 设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    // 获取系统时间2015年-11月-08日
    public static String getYearMonthDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");// 设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    // 获取系统时间2015年11月
    public static String getYearMonth() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月");// 设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    // 获取系统时间2015-11-08
    public static String getNowYearMonthDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    // 获取系统时间20:08
    public static String getHourMin() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");// 设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    // 获取系统时间20:08
    public static String getMinSec() {
        SimpleDateFormat df = new SimpleDateFormat("mm:ss SSS");// 设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    // 获取系统时间20:08
    public static String getSs() {
        SimpleDateFormat df = new SimpleDateFormat("ss");// 设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    // 获取系统时间
    public static String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }

    // 获取系统日期
    public static String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }

    //根据日期获取毫秒数
    public static long getMillionSeconds(String format, String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        long millionSeconds = 0;//毫秒
        try {
            millionSeconds = sdf.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return millionSeconds;
    }

    /**
     * 根据分钟数算出小时和分钟 如:405分钟=6小时45分钟
     *
     * @param minute 分钟
     * @return
     */
    public static int[] getHoursAndMinute(int minute) {
        int[] data = new int[2];
        if (minute != 0) {
            data[0] = minute / 60;
            data[1] = minute - data[0] * 60;
        }
        return data;
    }

    /**
     * 根据传入的两个double类型的时间戳, 后者比较大, 算出他们之间隔了多少小时
     */
    public static double getHours(double date1, double date2) {
        double hours = (date2 - date1) / (1000 * 60 * 60);
        DecimalFormat df = new DecimalFormat("0.00");
        hours = Double.parseDouble(df.format(hours));
        // hours = Math.round((hours * 100) / 100);
        return hours;
    }

    /**
     * 根据传过来的时间String转成毫秒级的时间数据double型 数据格式可能为2:00:25或者01:25
     */
    public static long getMillsTime(String time) {
        long hh, mm, ss;
        int l = time.length();
        if (l <= 5 && l > 0) {
            hh = 0;
            mm = Long.valueOf(time.substring(0, 2));
            ss = Long.valueOf(time.substring(3, 5));
            return (mm * 60 + ss) * 1000;
        } else if (l == 7) {
            hh = Long.valueOf(time.substring(0, 1));
            mm = Long.valueOf(time.substring(2, 4));
            ss = Long.valueOf(time.substring(5, 7));
            return (hh * 60 * 60 + mm * 60 + ss) * 1000;
        } else if (l == 8) {
            hh = Long.valueOf(time.substring(0, 2));
            mm = Long.valueOf(time.substring(3, 5));
            ss = Long.valueOf(time.substring(6, 8));
            return (hh * 60 * 60 + mm * 60 + ss) * 1000;
        } else {
            return 0;
        }
    }

    /**
     * 根据传入的耗秒数, 转换成为HH:MM:SS的字符串返回
     */
    public static String getHHMMSS(long time) {
        String hhmmss = "00:00:00";
        StringBuffer bf = new StringBuffer();
        long hh = time / 1000 / 60 / 60;
        long mm = (time % (1000 * 60 * 60)) / 1000 / 60;
        long ss = ((time % (1000 * 60 * 60)) % (1000 * 60)) / 1000;

        if (hh < 0) {
            bf.append("00:");
        } else if (hh < 10) {
            bf.append("0" + hh + ":");
        } else {
            bf.append(hh + ":");
        }
        if (mm < 0) {
            bf.append("00:");
        } else if (mm < 10) {
            bf.append("0" + mm + ":");
        } else {
            bf.append(mm + ":");
        }
        if (ss < 0) {
            bf.append("00");
        } else if (ss < 10) {
            bf.append("0" + ss);
        } else {
            bf.append(ss);
        }
        hhmmss = bf.toString();
        System.out.println(hhmmss);
        return hhmmss;
    }

    /**
     * 16进制转换为2进制
     */
    public static String hexString2binaryString(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0) {
            return null;
        }
        String bString = "", tmp;
        for (int i = 0; i < hexString.length(); i++) {
            tmp = "0000"
                    + Integer.toBinaryString(Integer.parseInt(
                    hexString.substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
    }

    /**
     * 取当前的时间, 返回int型的小时, 比如 23:59:59 返回 23的int
     */
    public static int getCurrentHour() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        return hour;
    }

    /**
     * 取得当前的分钟数
     *
     * @return
     */
    public static int getCurrentMinute() {
        Calendar c = Calendar.getInstance();
        int minute = c.get(Calendar.MINUTE);
        return minute;
    }

    /**
     * 取得当前的秒数
     */
    public static int getCurrentSecond() {
        Calendar c = Calendar.getInstance();
        int minute = c.get(Calendar.SECOND);
        return minute;
    }

    /**
     * 根据传入的数，计算返回整百值 如传入156，返回200
     *
     * @param snore_count
     * @return
     */
    public static int getMaxbySnorecount(int snore_count) {
        String str = (snore_count + "");
        int length = str.length();
        if (length < 3) {
            return 100;
        } else {
            str = str.substring(0, length - 2);
            int max = (Integer.parseInt(str) + 1) * 100;
            System.out.println("转换后的max:" + max);
            return max;
        }
    }

    /**
     * 计算两个时间相差的小时数
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 相差的小时(long)
     */
    public static long getTwoTimeSpacingHour(Date startTime, Date endTime) {
        final long NH = 1000 * 60 * 60;//一小时的毫秒数
        final long ND = 1000 * 24 * 60 * 60;
//        final long NM = 1000 * 60;
        long diff = endTime.getTime() - startTime.getTime();
        // 计算差多少小时
        long hour = diff % ND / NH;
//        long hour = diff % ND % NH / NM;
        return hour;
    }

    public static long getTwoTimeSpacingDay(Date startTime, Date endTime) {
        long diff = endTime.getTime() - startTime.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        Log.e("getTwoTimeSpacingDay", diffDays + "");
        return diffDays;
    }

    public static long getTwoTimeSpacingMinute(Date startTime, Date endTime) {
        final long NH = 1000 * 60 * 60;//一小时的毫秒数
        final long ND = 1000 * 24 * 60 * 60;
        final long NM = 1000 * 60;
        long diff = endTime.getTime() - startTime.getTime();
        // 计算差多少小时
//        long hour = diff % ND / NH;
        long minute = diff % ND % NH / NM;
        return minute;
    }

    public static String DayToMMSS(String time) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);

        String t = new SimpleDateFormat("HH:mm")
                .format(c.getTime());
        return t;
    }

    public static String DateToStringHourDay(Date time) {
        return DateToString(time, "MM-dd");
    }

    public static String DateToString(Date time, String f) {
        return new SimpleDateFormat(f).format(time);
    }

    public static Date StringToDateHourDay(String time) {
        return StringToDate(time, "MM-dd");
    }

    public static Date StringToDate(String time, String f) {
        Date date = null;
        try {
            date = new SimpleDateFormat(f).parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("StringToDate", e.toString());
        }
        return date;
    }

    /**
     * 获得指定日期的前一天
     *
     * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static String getSpecifiedDayBefore(String specifiedDay) {//可以用new Date().toLocalString()传递参数
        return getSpecified(specifiedDay, "MM-dd", "-");
    }

    /**
     * @param specifiedDay
     * @param type         "MM-dd"
     * @param fore         "+","-" 加减操作
     * @return
     */
    public static String getSpecified(String specifiedDay, String type, String fore) {//可以用new Date().toLocalString()传递参数
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat(type).parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int intdate = 0;
        switch (type) {
            case "MM-dd":
                intdate = c.get(Calendar.DATE);
                if (fore.equals("-"))
                    c.set(Calendar.DATE, intdate - 1);
                else c.set(Calendar.DATE, intdate + 1);
                break;
            case "yyyy年MM月":
                intdate = c.get(Calendar.MONTH);
                if (fore.equals("-"))
                    c.set(Calendar.MONTH, intdate - 1);
                else c.set(Calendar.MONTH, intdate + 1);
                break;
            case "yyyy-MM-dd":
                intdate = c.get(Calendar.DATE);
                if (fore.equals("-"))
                    c.set(Calendar.DATE, intdate - 1);
                else c.set(Calendar.DATE, intdate + 1);
                break;
        }
        String dayBefore = new SimpleDateFormat(type).format(c
                .getTime());
        return dayBefore;
    }

    /**
     * 获得指定日期的后一天
     *
     * @param specifiedDay
     * @return
     */
    public static String getSpecifiedDayAfter(String specifiedDay) {
        return getSpecified(specifiedDay.trim(), "MM-dd", "+");
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

    /**
     * 根据指定日期转换为周数。注意日期格式必须为MM-dd或者是yyyy-MM-dd。否则会转换错误
     *
     * @param date
     * @return
     */
    public static String getWeek(String date) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        try {//如果格式为月-日的情况下
            format.setLenient(false);
            format.parse(date);
            date = getNowYear() + "-" + date;//如果为月日，则加上当年的日期
        } catch (ParseException e) {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            try {
                format1.setLenient(false);
                format1.parse(date);
            } catch (ParseException e1) {
                e1.printStackTrace();
                Log.i("DateUtils.getWeek", "日期转星期失败！输入的日期为：" + date);
                return "";
            }
        }
        Calendar c = Calendar.getInstance();
        c.setTime(StringToDate(date, "yyyy-MM-dd"));
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        String week = "";
        switch (dayOfWeek) {
            case 1:
                week = "周日";
                break;
            case 2:
                week = "周一";
                break;
            case 3:
                week = "周二";
                break;
            case 4:
                week = "周三";
                break;
            case 5:
                week = "周四";
                break;
            case 6:
                week = "周五";
                break;
            case 7:
                week = "周六";
                break;
        }
        return week;
    }

}
