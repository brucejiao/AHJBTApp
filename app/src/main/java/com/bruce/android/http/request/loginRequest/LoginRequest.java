package com.bruce.android.http.request.loginRequest;

import com.alibaba.fastjson.JSONObject;
import com.bruce.android.activity.LoginActivity;
import com.bruce.android.dointerface.INetDataModel;
import com.bruce.android.http.Caller;
import com.bruce.android.http.HttpClient;
import com.bruce.android.http.HttpResponseHandler;
import com.bruce.android.utils.LogUtil;

import java.util.HashMap;

import okhttp3.Request;

/**
 * Created by JiaoJianJun on 2017/11/17.
 * 登录请求
 */

public class LoginRequest {

    private static INetDataModel mINetDataModel;
    public static void initData(HashMap<String, String> params, final LoginActivity activity){
        mINetDataModel = activity;
        HttpClient.get(Caller.LOGIN,params, new HttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                String resp = response.toString();
                LogUtil.i("=====登录返回信息=====",resp.trim());
                mINetDataModel.setValue(resp.trim());
                mINetDataModel.setProgress(true);
            }
            @Override
            public void onFailure(Request request, Exception e)
            {
                mINetDataModel.setProgress(true);
            }
        });
    }
}
