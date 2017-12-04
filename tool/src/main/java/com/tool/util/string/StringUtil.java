package com.tool.util.string;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 宁松柏
 * @time 2017/6/7 0007  8:38
 * @desc ${TODD}
 */
public class StringUtil {

    /**
     * @param inputStr 输入的字符串
     * @return 将该字符串转矫正成能被正常转换的Str
     */
    public static String reviseStr(String inputStr) {
        String reviseString = inputStr;
        if (inputStr.contains("-")) {
            reviseString = inputStr.replace("-", "");
        }
        if (inputStr.contains(" ")) {
            reviseString = inputStr.replace(" ", "");
        }
        if (reviseString.contains(".")) {
            if (reviseString.contains(".") && reviseString.length() == 1)//只有一个点 .
                reviseString = "0";
            else if (reviseString.split(".").length == 1 && reviseString.indexOf(".") == reviseString.lastIndexOf(".") && reviseString.length() == 2) {//只有一边，且数据长度为2，如x.   .x
                if (reviseString.indexOf(".") == 0)//.1
                    reviseString = "0" + reviseString;
                else reviseString += "0";
            } else if (!(reviseString.indexOf(".") == reviseString.lastIndexOf("."))) {//不是正常数据x.x
                reviseString = "0";
            }
        }
        return reviseString;
    }

    /**
     * @param str 需要判断是否能转换成数字的字符串
     * @return 是否是数字
     */
    public static boolean isNumeric(String str) {
        String trim = str.trim();
        Pattern pattern = Pattern.compile("[0-9]*");
        if (str != null) {
            Matcher isNum = pattern.matcher(trim);
            if (!isNum.matches()) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * @param data 需要判断的数值
     * @param area 该数值所要比对的正常范围
     * @return 是否在该范围
     */
    public static boolean judgeDataIsNormal(String data, String area) {
        if (!TextUtils.isEmpty(data)) {
            String[] split = area.split("-");
            if (Float.valueOf(data) >= Float.valueOf(split[0]) && Float.valueOf(data) <= Float.valueOf(split[1])) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public static CharSequence getSpannableString(String content, int sp, int color) {
        SpannableString spannableString = new SpannableString(content);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        spannableString.setSpan(colorSpan, 0, content.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(sp, true);
        spannableString.setSpan(sizeSpan, 0, content.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }


    /**
     * 从字符串中筛选出数字
     */
    public static String getNumFromString(String str) {
        String result;
        String regEx = "[^0-9\\.]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        if (m.replaceAll("").trim().equals("")) {
            result = 0 + "";
        } else
            result = m.replaceAll("").trim();
        return result;
    }

    /**
     * 从字符串中筛选出数字
     */
    public static String[] getMultiNumFromString(String str) {
        String regEx = "[^0-9\\.]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        //将输入的字符串中非数字部分用空格取代并存入一个字符串
        String string = m.replaceAll(" ").trim();
        //以空格为分割符在讲数字存入一个字符串数组中
        String[] strArr = string.split(" ");

        return strArr;
    }

    /**
     *  判断一个字符串是否含有数字
     * @param content
     * @return
     */
    public static boolean HasDigit(String content) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches()) {
            flag = true;
        }
        return flag;
    }
}
