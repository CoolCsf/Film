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
     * 惊悚
     */
    TERROR(5, "惊悚"),
    /**
     * 推理
     */
    REASONING(6, "推理");
    private int index;
    private String type;
    private static List<String> allType;

    SortTypeEnum(int status, String state) {
        this.type = state;
        this.index = status;
    }

    public static String getState(int index) {
        String s;
        switch (index) {
            case 0:
                s = ALL.type;
                break;
            case 1:
                s = LOVE.type;
                break;
            case 2:
                s = COMEDY.type;
                break;
            case 3:
                s = ANIMATION.type;
                break;
            case 4:
                s = SCIENCE.type;
                break;
            case 5:
                s = TERROR.type;
                break;
            case 6:
                s = REASONING.type;
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
                allType.add(e.type);
            }
        }
        return allType;
    }

    public int getIndex() {
        return index;
    }

    public String getType() {
        return type;
    }
}
