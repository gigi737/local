package com.example.web.enums;

import java.util.HashMap;

/**
 * 出库状态枚举
 */
public enum StockOutStatusEnum {
    /**
     * 待出库
     */
    待出库(1),

    /**
     * 完成分配
     */
    完成分配(2),

    /**
     * 拣货中
     */
    拣货中(3),

    /**
     * 拣货完成
     */
    拣货完成(4),
    /**
     * 出库完成
     */
    出库完成(5);


    private final int index;

    StockOutStatusEnum(int index) {
        this.index = index;
    }

    public int index() {
        return index;
    }

    private static final HashMap<Integer, StockOutStatusEnum> MY_MAP = new HashMap<Integer, StockOutStatusEnum>();

    static {
        for (StockOutStatusEnum myEnum : values()) {
            MY_MAP.put(myEnum.index(), myEnum);
        }
    }

    public static StockOutStatusEnum GetEnum(Integer v) {
        if (v == null) {
            return MY_MAP.values().stream().findFirst().get();
        }
        return MY_MAP.get(v);
    }

}
