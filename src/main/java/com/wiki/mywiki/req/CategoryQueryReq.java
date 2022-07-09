package com.wiki.mywiki.req;

public class CategoryQueryReq extends PageReq{
    private String name;
    @Override
    public String toString() {
        return "CategoryQueryReq{} " + super.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
