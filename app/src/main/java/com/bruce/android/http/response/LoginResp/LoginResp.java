package com.bruce.android.http.response.LoginResp;

/**
 * Created by JiaoJianJun on 2017/8/7.
 * 登录成功
 */

public class LoginResp
{

    private String address;
    private boolean success;
    private String username;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
