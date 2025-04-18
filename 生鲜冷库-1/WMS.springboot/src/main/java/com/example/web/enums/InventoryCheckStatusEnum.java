package com.example.web.enums;

import java.util.HashMap;

 /**
   *盘点状态枚举
   */
public enum InventoryCheckStatusEnum 
  {
    /**
     * 待盘点
     */    
    待盘点(1), 
     
    /**
     * 盘点中
     */    
    盘点中(2), 
     
    /**
     * 盘点完成
     */    
    盘点完成(3), 
     
    /**
     * 取消
     */    
    取消(4); 
     
            
    private final int index;
    
    InventoryCheckStatusEnum(int index) 
    {
      this.index = index;
    }

    public int index() {
      return index;
    }
     private static final HashMap<Integer,InventoryCheckStatusEnum> MY_MAP = new HashMap<Integer,InventoryCheckStatusEnum>();
     static {
            for (InventoryCheckStatusEnum myEnum : values()) {
                MY_MAP.put(myEnum.index(), myEnum);
            }
      }
     public static InventoryCheckStatusEnum GetEnum(Integer v)
        {
           if(v==null){
                return MY_MAP.values().stream().findFirst().get();
            }
            return MY_MAP.get(v);
        }
     
 }
