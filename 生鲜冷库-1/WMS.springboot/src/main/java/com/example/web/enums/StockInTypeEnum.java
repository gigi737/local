package com.example.web.enums;

import java.util.HashMap;

 /**
   *入库类型枚举
   */
public enum StockInTypeEnum 
  {
    /**
     * 正常入库
     */    
    正常入库(1), 
     
    /**
     * 采购入库
     */    
     采购入库(2), 
     
    /**
     * 报损入库
     */    
    报损入库(3), 
     
    /**
     * 调拨入库
     */    
    调拨入库(4); 
     
            
    private final int index;
    
    StockInTypeEnum(int index) 
    {
      this.index = index;
    }

    public int index() {
      return index;
    }
     private static final HashMap<Integer,StockInTypeEnum> MY_MAP = new HashMap<Integer,StockInTypeEnum>();
     static {
            for (StockInTypeEnum myEnum : values()) {
                MY_MAP.put(myEnum.index(), myEnum);
            }
      }
     public static StockInTypeEnum GetEnum(Integer v)
        {
           if(v==null){
                return MY_MAP.values().stream().findFirst().get();
            }
            return MY_MAP.get(v);
        }
     
 }
