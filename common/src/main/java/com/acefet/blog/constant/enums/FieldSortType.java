package com.acefet.blog.constant.enums;

import lombok.Getter;

@Getter
public enum FieldSortType {
    TOP(99,"置顶"),
    BOTTOM(-99,"底部"),
    NORMAL(0,"");

    private Integer code;
    private String name;

    FieldSortType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 获取排序字段值对象
     * @param code
     * @return
     */
    public static FieldSortType getFieldSortType(String code){
        if(code==null || "".equals(code))code = "0";
        FieldSortType[] fieldSortTypes = FieldSortType.values();
        for(FieldSortType fieldSortType : fieldSortTypes){
            if(code.equals(fieldSortType.getCode())){
                return fieldSortType;
            }
        }
        return null;
    }


}