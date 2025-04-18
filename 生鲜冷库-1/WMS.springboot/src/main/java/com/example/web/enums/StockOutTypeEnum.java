package com.example.web.enums;

import java.util.HashMap;

 /**
   *出库类型枚举
   */
public enum StockOutTypeEnum 
  {
    /**
     * 销售出库
     */    
    销售出库(1), 
     
    /**
     * 普通出库
     */    
    普通出库(2), 
     
    /**
     * 报损出库
     */    
    报损出库(3); 
     
            
    private final int index;
    
    StockOutTypeEnum(int index) 
    {
      this.index = index;
    }

    public int index() {
      return index;
    }
     private static final HashMap<Integer,StockOutTypeEnum> MY_MAP = new HashMap<Integer,StockOutTypeEnum>();
     static {
            for (StockOutTypeEnum myEnum : values()) {
                MY_MAP.put(myEnum.index(), myEnum);
            }
      }
     public static StockOutTypeEnum GetEnum(Integer v)
        {
           if(v==null){
                return MY_MAP.values().stream().findFirst().get();
            }
            return MY_MAP.get(v);
        }
     
 }
