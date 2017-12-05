package com.bruce.android.http.request.SecondPageRequest;

import com.alibaba.fastjson.JSONObject;
import com.bruce.android.activity.baseActivity.SecondWebActivity;
import com.bruce.android.dointerface.INetDataModel;
import com.bruce.android.http.Caller;
import com.bruce.android.http.HttpClient;
import com.bruce.android.http.HttpResponseHandler;
import com.bruce.android.utils.LogUtil;

import java.util.HashMap;

import okhttp3.Request;

/**
 * Created by JiaoJianJun on 2017/11/20.
 * 二级界面提交接口
 */

public class SecondPageRequest {

    private static INetDataModel mINetDataModel;
    public static void initData(HashMap<String, String> params, final SecondWebActivity activity){
        mINetDataModel = activity;
        HttpClient.get(Caller.JHYJ_SUB_DATA,params, new HttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                String resp = response.toString();
                LogUtil.i("=====提交返回信息=====",resp);
                mINetDataModel.setValue(resp);
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
