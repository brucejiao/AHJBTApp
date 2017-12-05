package com.bruce.android.fragment.mainpage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bruce.android.R;
import com.bruce.android.js.JSKit;
import com.bruce.android.ui.UIHelper;
import com.bruce.android.utils.CommUtil;
import com.bruce.android.utils.Contents;
import com.bruce.android.utils.LogUtil;
import com.bruce.android.utils.SharedPreferences;
import com.squareup.picasso.Picasso;

import java.net.URLDecoder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 通知通告
 */
public class SecondFragment extends Fragment {

    private Activity context;
    private boolean isLoadAll = false;


    @Bind(R.id.first_page_wv)
    WebView mWebShow;
    private JSKit js;
    String mResult = "";
    private ProgressDialog progress;
    @Bind(R.id.btnBack)
    Button mBtnBack;
    @Bind(R.id.textHeadTitle)
    TextView mHeaderTitle;
    @Bind(R.id.layout_load_error)
    RelativeLayout mLayoutLoadError;
    SharedPreferences sharedPreferences ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_page, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        sharedPreferences = SharedPreferences.getInstance();
        initView();
        initUI();
        loadData();
//        Alerter.create(getActivity())
//                .setTitle("电子发票申请")
//                .setText("登录成功")
//                .setBackgroundColor(R.color.my_bg_color)
//                .setIcon(R.drawable.ic_face)
//                .show();

    }

    void initView() {
        mHeaderTitle.setText(Contents.TZTB);

    }

    private void initUI(){
        String url = sharedPreferences.getString(Contents.SECOND_PAGE_URL,"");
        mWebShow.loadUrl(url);
    }

    private void loadData() {
        WebSettings webSettings = mWebShow.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //实例化js对象
//        js = new JSKit(getActivity());


// User settings

        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);//关键点

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        webSettings.setDisplayZoomControls(true);
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setBuiltInZoomControls(false); // 设置显示缩放按钮
        webSettings.setSupportZoom(false); // 支持缩放
        webSettings.setLoadWithOverviewMode(true);


/**
 * 用WebView显示图片，可使用这个参数 设置网页布局类型： 1、LayoutAlgorithm.NARROW_COLUMNS ：
 * 适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
 */
//        webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);

        //把js绑定到全局的myjs上，myjs的作用域是全局的，初始化后可随处使用
      /*  mWebShow.addJavascriptInterface(js, "myjs");
        mWebShow.setWebChromeClient(new WebChromeClient());*/

        //调用接口
/*        boolean flag = getActivity().getIntent().getBooleanExtra("flag",false);
        String id = getActivity().getIntent().getStringExtra("id");*/
//        if(flag){
//            initNetData(id);
//        }


        // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        mWebShow.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
            {
                //6.0以下执行
                mWebShow.setVisibility(View.GONE);
                mLayoutLoadError.setVisibility(View.VISIBLE);
                CommUtil.showToast("数据获取失败",context);
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
                LogUtil.i("拦截URL======>",url);
                if (url.contains("_title="))
                {
                    //获取标题title
                    try {
                        //截取userId
                        String userId = CommUtil.getParamsValue(url,"userId");
                        sharedPreferences.putString(Contents.USER_ID,userId);
                        //截取urlTitle
                        String urlTitle = CommUtil.getParamsValue(url,"_title");
                        String keyWord = URLDecoder.decode(urlTitle,"UTF-8");
                        //跳转二级界面
                        UIHelper.showJHYJ(context,url,keyWord);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                return true;

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Picasso.with(context).resumeTag(context);
    }

    @Override
    public void onPause() {
        super.onPause();
        Picasso.with(context).pauseTag(context);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Picasso.with(context).cancelTag(context);
    }


    @OnClick(R.id.btnBack)
    public void btnBack(View view){
        context.finish();
    }

}