package com.jinandaxue.entity;

import java.io.Serializable;

public class LoginBean implements Serializable {

    /**
     * success : false
     * flag : false
     * message : null
     * resultcode : 200
     * reason : 登录成功
     * data : {"id":16,"name":null,"nickname":null,"phone":"13001991537","password":null,"groupid":null,"gender":null,"idcard":null,"createtime":"2019-04-04 16:08:54","email":"1017693431@qq.com","headlogo":null}
     */

    private int resultcode;
    private String reason;
    private UserBean data;

    public int getResultcode() {
        return resultcode;
    }

    public void setResultcode(int resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public UserBean getData() {
        return data;
    }

    public void setData(UserBean data) {
        this.data = data;
    }
}
