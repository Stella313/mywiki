package com.wiki.mywiki.req;

public class DocQueryReq extends PageReq{
    private String name;
    @Override
    public String toString() {
        return "DocQueryReq{} " + super.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
