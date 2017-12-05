package com.bruce.android.http;

/**
 * Created by tiansj on 2016/11/30.
 */

public class RestApiResponse {

    private String msg;
    private String success;
    private String address;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
