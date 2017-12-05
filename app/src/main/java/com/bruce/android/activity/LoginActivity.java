package com.bruce.android.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.bruce.android.R;
import com.bruce.android.common.AppContext;
import com.bruce.android.dointerface.INetDataModel;
import com.bruce.android.http.request.loginRequest.LoginRequest;
import com.bruce.android.http.request.loginRequest.UrlResp;
import com.bruce.android.http.response.LoginResp.LoginResp;
import com.bruce.android.ui.UIHelper;
import com.bruce.android.ui.swipebacklayout.SwipeBackActivity;
import com.bruce.android.ui.viewpagerindicator.SlipButton;
import com.bruce.android.utils.CommUtil;
import com.bruce.android.utils.Contents;
import com.bruce.android.utils.LogUtil;
import com.bruce.android.utils.SharedPreferences;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 login
 */
public class LoginActivity extends SwipeBackActivity implements INetDataModel {
    private LoginActivity mContext;
    @Bind(R.id.idCard_edit)
    EditText mIdCard;
    @Bind(R.id.chk_save_pwd)
    SlipButton chk_Save_Pwd;

    @Bind(R.id.btnSure)
    Button mBtnSure;
    SharedPreferences sharedPreferences ;

    private boolean isLogin = false;//登录状态
    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_tax);
        ButterKnife.bind(this);
        AppContext.getInstance();
        mContext = this;
        sharedPreferences = SharedPreferences.getInstance();
//        mIdCard.setText("341602199209036553");
        setChk_Save_Pwd();
    }

    /**
     * 是否记住密码
     */
    private void setChk_Save_Pwd()
    {
        chk_Save_Pwd.setOnChangedListener(new SlipButton.OnChangedListener() {
            @Override
            public void OnChanged(boolean CheckState) {
                if (CheckState) {
                    LogUtil.i("savePwd", "记住密码已选中！");
                    sharedPreferences.putBoolean(Contents.ISCHECK, true);
                } else {
                    LogUtil.i("savePwd", "记住密码没有选中！");
                    sharedPreferences.putBoolean(Contents.ISCHECK, false);
                }
            }
        });
        Boolean isMemory = sharedPreferences.getBoolean(Contents.ISCHECK, false);
        // 进入界面时，这个if用来判断SharedPreferences里面name和password有没有数据，有的话则直接打在EditText上面
        if (isMemory) {
            String id_card = sharedPreferences.getString(Contents.USER_ID_CARD, "");
            mIdCard.setText(id_card);
            chk_Save_Pwd.setState(true);
        }
    }

    @OnClick(R.id.btnSure)
    public void setmBtnSure(View view){
        progress = CommUtil.showProgress(mContext, "正在登录，请稍候...");
        String userName = mIdCard.getText().toString();
        sharedPreferences.putString(Contents.USER_ID_CARD,userName);
        HashMap<String, String> params = new HashMap<>();
        params.put("loginType", "1");
        params.put("user.username", userName);
        LoginRequest.initData(params,mContext);
    }


    private void hideProgress(){
        if (progress != null)
        {
            progress.dismiss();
        }
    }

    @Override
    public void setProgress(boolean progress) {
        if(progress){
            hideProgress();
        }
    }

    @Override
    public void setObject(Object object) {

    }

    @Override
    public void setValue(String value) {
        LoginResp loginResp = JSONObject.parseObject(value,LoginResp.class);
        boolean  success = loginResp.isSuccess();
//        CommUtil.showAlert("success-->"+success+"\nvalue-->"+value,mContext);
        LogUtil.i("======LoginResp=======","success-->"+success+"\nvalue-->"+value);
        //SUCCESS
        if (success)
        {
            String  address =loginResp.getAddress();
            String  username =  loginResp.getUsername();

            //获取URL地址
            UrlResp urlResp = JSONObject.parseObject(address,UrlResp.class);
            String  homeUrl =  urlResp.getHome();
            String  listUrl =  urlResp.getList();
            String  meUrl =   urlResp.getMe();
            //保存获取到的数据
            sharedPreferences.putString(Contents.USER_NAME,username);
            sharedPreferences.putString(Contents.FRIST_PAGE_URL,homeUrl);
            sharedPreferences.putString(Contents.SECOND_PAGE_URL,listUrl);
            sharedPreferences.putString(Contents.THIRD_PAGE_URL,meUrl);
            //跳转到主页
            UIHelper.showHome(mContext);

        }
        //FALSE
        else
        {
            String  msg =  loginResp.getMsg();
            CommUtil.showAlert(msg,mContext);
        }
    }

    @Override
    public void setList(List list) {

    }
}
