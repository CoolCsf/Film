package com.wrj.film.model;

/**
 * Created by Administrator on 2017/12/18.
 */

public enum OrderTypeEnum {
    /**
     * 已完成
     */
    COMPLETE(0, "已完成"),
    /**
     * 已支付
     */
    UNDONE(1, "已支付"),
    /**
     * 待支付
     */
    TO_BE_PAID(2, "待支付");
    private int index;
    private String type;

    OrderTypeEnum(int status, String state) {
        this.type = state;
        this.index = status;
    }

    public static String getState(int index) {
        String s;
        switch (index) {
            case 0:
                s = COMPLETE.type;
                break;
            case 1:
                s = UNDONE.type;
                break;
            case 2:
                s = TO_BE_PAID.type;
                break;
            default:
                s = "";
        }
        return s;
    }
}
