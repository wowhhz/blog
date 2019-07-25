package com.acefet.blog.constant.enums;

import lombok.Getter;

@Getter
public enum UserStatus {
    NORMAL("normal","正常"),
    ERROR("error","异常"),
    DISABLE("disable","禁用");

    private String statusCode;

    private String statusName;

    UserStatus(String statusCode,String statusName) {
        this.statusCode = statusCode;
        this.statusName = statusName;
    }

    /**
     * 获取用户状态的值对象
     * @param statusCode
     * @return
     */
    public static UserStatus getUserStatus(String statusCode){
        UserStatus[] statuses = UserStatus.values();
        for(UserStatus userStatus : statuses){
            if(statusCode.equals(userStatus.getStatusCode())){
                return userStatus;
            }
        }
        return null;
    }

}
