package com.example.web.enums;

import java.util.HashMap;

 /**
   *入库状态枚举
   */
public enum StockInStatusEnum 
  {
    /**
     * 待入库
     */    
    待入库(1), 
     
    /**
     * 入库中
     */    
    入库中(2), 
     
    /**
     * 入库完成
     */    
    入库完成(3), 
     
    /**
     * 取消
     */    
    取消(4); 
     
            
    private final int index;
    
    StockInStatusEnum(int index) 
    {
      this.index = index;
    }

    public int index() {
      return index;
    }
     private static final HashMap<Integer,StockInStatusEnum> MY_MAP = new HashMap<Integer,StockInStatusEnum>();
     static {
            for (StockInStatusEnum myEnum : values()) {
                MY_MAP.put(myEnum.index(), myEnum);
            }
      }
     public static StockInStatusEnum GetEnum(Integer v)
        {
           if(v==null){
                return MY_MAP.values().stream().findFirst().get();
            }
            return MY_MAP.get(v);
        }
     
 }
