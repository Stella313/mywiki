package com.wiki.mywiki.req;

public class UserQueryReq extends PageReq{
    private String loginName;

    public String getLoginName() {
        return loginName;
    }

    @Override
    public String toString() {
        return "UserQueryReq{" +
                "loginName='" + loginName + '\'' +
                '}';
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

}