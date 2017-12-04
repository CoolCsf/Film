package com.tool.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 文件描述：正则表达式工具类
 * Created by sf.chen on 2017/2/27.
 */
public class RegulrlyUtils {
    private final static String USERNAME_REG = "^[a-zA-Z0-9]{6,16}$";
    private final static String PWD_REG = "^[\\@A-Za-z0-9\\!\\#\\$\\%\\^\\&\\*\\.\\~]{6,16}$";
    private final static String CELL_PHONE = "^[1][3,4,5,7,8][0-9]{9}$";
    private final static String NUMBER_REG = "[0-9]";
    private final static String ALL_NUMBER_REG = "[0-9]{1,}";
    private final static String DATE_REG = "[^-0-9]";
    private final static String POSITIVE_DECIMAL_INTEGER_REG = "([0-9]+)|([0-9]+.[0-9]+)";
    private final static String RECEIVER_NAME_REG = "^([a-zA-Z0-9]|[\\u4e00-\\u9fa5]){1,20}$";//收货人姓名正则
    private final static String RECEIVER_NUMBER_REG = "^((\\\\+86)|(86))?[1][3,4,5,7,8][0-9]{9}$";//收货人手机号码正则
    private final static String USERNAME_REGS = "^([a-zA-Z0-9]|[\\u4e00-\\u9fa5]){1,15}$";

    //检验收货人姓名是否正确
    public static boolean checkReceiverName(String str) {
        return checkStrRegulrly(str, RECEIVER_NAME_REG);
    }

    //检验收货人手机号码是否正确
    public static boolean checkReceiverNumber(String str) {
        return checkStrRegulrly(str, RECEIVER_NUMBER_REG);
    }

    //检验用户名是否符合规则
    public static boolean checkUserNameReg(String str) {
        return checkStrRegulrly(str, USERNAME_REG);
    }

    public static String getDateReg() {
        return DATE_REG;
    }

    //检验是否为手机号
    public static boolean isCellphone(String str) {
        return checkStrRegulrly(str, CELL_PHONE);
    }

    //检验密码是否符合规则
    public static boolean checkPwdReg(String str) {
        return checkStrRegulrly(str, PWD_REG);
    }

    //检验是否包含数字
    public static boolean hasNumber(String str) {
        return checkStrRegulrly(str, NUMBER_REG);
    }

    //检验是否为数字
    public static boolean isNumber(String str) {
        return checkStrRegulrly(str, ALL_NUMBER_REG);
    }

    //检验是否为正整数，正小数
    public static boolean isPOSITIVE_DECIMAL_INTEGER_REG(String str) {
        return checkStrRegulrly(str, POSITIVE_DECIMAL_INTEGER_REG);
    }

    public static boolean checkStrRegulrly(String str, String reg) throws PatternSyntaxException {
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    //校验用户名是否合法
    public static boolean isUserName(String name) {
        return checkStrRegulrly(name, USERNAME_REGS);
    }
}
