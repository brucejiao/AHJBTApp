package com.bruce.android.http;

import com.bruce.android.utils.LogUtil;
import com.scret.utils.SafaUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by Administrator on 2017/6/11.
 * 接口
 */

public class Caller  {

    /**
     Picasso.with(mContext).load("http://pic.nipic.com/2008-07-11/20087119630716_2.jpg").resize(DeviceUtil.dp2px(mContext,73), DeviceUtil.dp2px(mContext,73)).placeholder(R.drawable.default_image).into(mIssueImgOne);

     */
    // IP & PORT
    //登录
    public static String LOGIN         = "http://222.190.120.106:8099/AHPLSuper/orgsec/loginMobile.action";
    //江淮义警-提交操作
    public static String JHYJ_SUB_DATA = "http://222.190.120.106:8099/AHPLSuper/app/addLog.action";


    /**
     * 登录参数
     * @param userName 用户名
     * @return
     */
    public static String getLoginParams(String userName){
        Map<String,String> map = new HashMap<String,String>();
        map.put("loginType", "1");
        map.put("json.toString()", userName);
        JSONObject json = new JSONObject(map);
        LogUtil.i("====登录数据====", json.toString());
        return json.toString();
    }






    /**
     * 检测版本更新
     * @param version  版本号
     * @return
     */
    public static String getUpdateParams(String version){
        Map<String,String> map = new HashMap<String,String>();
        map.put("type", "version.api");
        map.put("os_type", "1");//移动系统类型：android：1  ios:0
        map.put("version", version);
        JSONObject json = new JSONObject(map);
        String secret = SafaUtils.encrypt(json.toString());
        LogUtil.i("====版本更新====", json.toString());
        return secret;
    }
}
