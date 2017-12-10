package com.wrj.film.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/9.
 */

public enum SortTypeEnum {
    /**
     * 全部
     */
    ALL(0, "全部"),
    /**
     * 爱情
     */
    LOVE(1, "爱情"),
    /**
     * 喜剧
     */
    COMEDY(2, "喜剧"),
    /**
     * 动画
     */
    ANIMATION(3, "动画"),
    /**
     * 科幻
     */
    SCIENCE(4, "科幻"),
    /**
     * 恐怖
     */
    TERROR(5, "恐怖"),
    /**
     * 推理
     */
    REASONING(6, "推理");
    private int status;
    private String state;
    private static List<String> allType;

    SortTypeEnum(int status, String state) {
        this.state = state;
        this.status = status;
    }

    public static String getState(int status) {
        String s;
        switch (status) {
            case 0:
                s = ALL.state;
                break;
            case 1:
                s = LOVE.state;
                break;
            case 2:
                s = COMEDY.state;
                break;
            case 3:
                s = ANIMATION.state;
                break;
            case 4:
                s = SCIENCE.state;
                break;
            case 5:
                s = TERROR.state;
                break;
            case 6:
                s = REASONING.state;
                break;
            default:
                s = "";
        }
        return s;
    }

    public static List<String> getAllType() {
        if (allType == null) {
            allType = new ArrayList<>();
            for (SortTypeEnum e : SortTypeEnum.values()) {
                allType.add(e.state);
            }
        }
        return allType;
    }

//    public int getStatus() {
//        return status;
//    }
}
