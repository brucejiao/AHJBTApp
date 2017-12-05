package com.bruce.android.js;

import android.app.ProgressDialog;
import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;


/**
 * Created by Administrator on 2017/7/16.
 */

public class JSKit {
    private Context ma;
    private ProgressDialog progress;
    public JSKit(Context context) {
        this.ma = context;

    }

    @JavascriptInterface
    public void showMsg(String msg) {
        Toast.makeText(ma, msg, Toast.LENGTH_SHORT).show();
    }

//粮情列表
    @JavascriptInterface
    public void getData(String msg) {
//        Toast.makeText(ma, "粮情列表--->"+msg, Toast.LENGTH_SHORT).show();
    }

}
