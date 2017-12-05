package com.bruce.android.activity.baseActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bruce.android.R;
import com.bruce.android.utils.SharedPreferences;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 公用二级web展示界面
 * 二级界面
 */
public class SecondWebActivity extends AppCompatActivity implements com.bruce.android.dointerface.INetDataModel {
    private SecondWebActivity mContext ;
    @Bind(R.id.jhyj_page_wv)
    WebView mWebShow;
    @Bind(R.id.btnBack)
    Button mBtnBack;
    @Bind(R.id.textHeadTitle)
    TextView mTextHeadTitle;
    @Bind(R.id.layout_main_header)
    RelativeLayout mLayMainHeader;
    @Bind(R.id.layout_load_error)
    RelativeLayout mLayoutLoadError;

    SharedPreferences sharedPreferences ;

    /**
     * 0 我要举报
     * 1 我要追逃
     * 2 我要破案
     */
    private int mSubData = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jhyj_web);
        ButterKnife.bind(this);
        mContext = this;
        sharedPreferences = com.bruce.android.utils.SharedPreferences.getInstance();
        initUI();
        loadData();
    }

    //加载界面
    private void initUI()
    {
        String title = getIntent().getStringExtra("title");
        String url = getIntent().getStringExtra("url");

        mBtnBack.setVisibility(View.VISIBLE);
        mTextHeadTitle.setText(title);
        mWebShow.loadUrl(url);

    }

    private void loadData() {
        WebSettings webSettings = mWebShow.getSettings();
        //实例化js对象
        //js = new JSKit(getActivity());
        // User settings
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);//关键点
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setDisplayZoomControls(true);
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setBuiltInZoomControls(false); // 设置显示缩放按钮
        webSettings.setSupportZoom(false); // 支持缩放
        webSettings.setLoadWithOverviewMode(true);

        // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        mWebShow.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
            {
                //6.0以下执行
                mWebShow.setVisibility(View.GONE);
                mLayoutLoadError.setVisibility(View.VISIBLE);
                com.bruce.android.utils.CommUtil.showToast("数据获取失败",mContext);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error)
            {
                super.onReceivedError(view, request, error);
                //6.0以上执行
                mWebShow.setVisibility(View.GONE);
                mLayoutLoadError.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器

                //江淮义警拦截URL======>: :shouldOverrideUrlLoading:2017-11-20 04:21:50112---->
                // http://jhyj.ahga.gov.cn/wx/wxview/mine_jubao_list.htm?openid=04707317F4284C718BBE9B634492E9D6
                //http://jhyj.ahga.gov.cn/wx/wxview/mine_jubao_list.htm?openid=04707317F4284C718BBE9B634492E9D6
                com.bruce.android.utils.LogUtil.i("江淮义警拦截URL======>",url);
                mWebShow.loadUrl(url);
                //进入举报界面
                if (url.contains("http://jhyj.ahga.gov.cn/wx/wxview/jubao.htm")) {
                    // 获取当前页面地址并提取reportTypeCode参数值
                    //获取reportTypeCode
                    String  reportTypeCode = com.bruce.android.utils.CommUtil.getParamsValue(url,"reportTypeCode");

                    // 我要举报
                    if (null == reportTypeCode || "".equals(reportTypeCode) )
                    {
                        mSubData = 0 ;
//                        CommUtil.showAlert("江淮义警拦截URL======>"+url,mContext);
                    }
                    // 我要追逃
                    else if ("02".equals(reportTypeCode))
                    {
                        mSubData = 1 ;
                    }
                    // 我要破案
                    else if ("01".equals(reportTypeCode))
                    {
                        mSubData = 2 ;
                    }
                }
                // 我要当义警
                else if (url.startsWith("http://jhyj.ahga.gov.cn/wx/weixin/weixinUser/modifyJmt.json")) {

//                    CommUtil.showAlert("我要当义警",mContext);
                    submitData(com.bruce.android.utils.Contents.JHYJ_WYDYJ_LX, com.bruce.android.utils.Contents.JHYJ_WYDYJ_MC,"");
                }
                //提交数据到后台
                if (url.contains("http://jhyj.ahga.gov.cn/wx/wxview/mine_jubao_list.htm"))
                {
                    switch (mSubData){
                        case 0:
//                            CommUtil.showAlert(" 我要举报",mContext);
                            submitData(com.bruce.android.utils.Contents.JHYJ_WYJB_LX, com.bruce.android.utils.Contents.JHYJ_WYJB_MC,"");
                            break;
                        case 1:
//                            CommUtil.showAlert("我要追逃", mContext);
                            submitData(com.bruce.android.utils.Contents.JHYJ_WYZT_LX, com.bruce.android.utils.Contents.JHYJ_WYZT_MC, "");
                            break;
                        case 2:
//                            CommUtil.showAlert("我要破案", mContext);
                            submitData(com.bruce.android.utils.Contents.JHYJ_WYPA_LX, com.bruce.android.utils.Contents.JHYJ_WYPA_MC, "");
                            break;
                        default:break;
                    }
                }

                return true;

            }
        });
    }

    /**
     * 提交数据
     * @param jblx      举报类型
     * @param jbmc      举报名称
     * @param formData  form表单内容
     */
    private void submitData(String jblx,String jbmc,String formData)
    {
        String idCard = sharedPreferences.getString(com.bruce.android.utils.Contents.USER_ID_CARD,"");//身份证号码
        String userName =  sharedPreferences.getString(com.bruce.android.utils.Contents.USER_NAME,"");//姓名
        String userId = sharedPreferences.getString(com.bruce.android.utils.Contents.USER_ID,"");//USER_ID

        HashMap<String, String> params = new HashMap<>();
        params.put("applog.czrzh", idCard);//身份证号码
        params.put("applog.czrxm", userName);//姓名
        params.put("applog.exparams", userId);//存放江淮义警地址里的 userid
        params.put("applog.czlx", jblx);//举报类型    10:我要举报
        params.put("applog.czlxmc", jbmc);//举报名称
        params.put("applog.params", formData);//举报表单内容
        params.put("applog.rylx", "1");//人员类型
        com.bruce.android.http.request.SecondPageRequest.SecondPageRequest.initData(params,mContext);
    }


    @OnClick(R.id.btnBack)
    public void onBtnBack()
    {
        if(mWebShow.canGoBack()){
            mWebShow.goBack();
        }else{
            com.bruce.android.ui.UIHelper.showHome(mContext);
        }
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            com.bruce.android.ui.UIHelper.showHome(mContext);
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }






    @Override
    public void setProgress(boolean progress) {

    }

    @Override
    public void setObject(Object object) {

    }

    @Override
    public void setValue(String value) {

    }

    @Override
    public void setList(List list) {

    }
}
