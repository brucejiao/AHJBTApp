
package com.bruce.android.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bruce.android.R;
import com.bruce.android.fragment.mainpage.FirstFragment;
import com.bruce.android.fragment.mainpage.SecondFragment;
import com.bruce.android.fragment.mainpage.ThirdFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseFragmentActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private MainActivity mContext ;
    private static final String CURR_INDEX = "currIndex";
    private static int currIndex = 0;

    private RadioGroup group;

    private ArrayList<String> fragmentTags;
    private FragmentManager fragmentManager;

    @Bind(R.id.guideImage)
    ImageView mGuideImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        initData(savedInstanceState);
        initView();

        //启动界面
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mGuideImage.setVisibility(View.GONE);
//            }
//        }, 3000);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURR_INDEX, currIndex);
    }

    private void initData(Bundle savedInstanceState) {
        fragmentTags = new ArrayList<>(Arrays.asList("HomeFragment", "ImFragment", "InterestFragment", "MemberFragment"));
        currIndex = 0;
        if(savedInstanceState != null) {
            currIndex = savedInstanceState.getInt(CURR_INDEX);
            hideSavedFragment();
        }
    }

    private void hideSavedFragment() {
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTags.get(currIndex));
        if(fragment != null) {
            fragmentManager.beginTransaction().hide(fragment).commit();
        }
    }

    private void initView() {
        group = (RadioGroup) findViewById(R.id.group);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.foot_bar_home: currIndex = 0; break;//首页
//                    case R.id.foot_bar_find: currIndex = 1; break;//发现
                    case R.id.foot_bar_message: currIndex = 2; break;//通知通报
                    case R.id.foot_bar_mine: currIndex = 3; break;//个人中心
                    default: break;
                }
                showFragment();
            }
        });
        showFragment();
    }

    private void showFragment() {
//        if (currIndex == 3) {
//            UIHelper.showLogin(MainActivity.this);
//        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTags.get(currIndex));
        if(fragment == null) {
            fragment = instantFragment(currIndex);
        }
        for (int i = 0; i < fragmentTags.size(); i++) {
            Fragment f = fragmentManager.findFragmentByTag(fragmentTags.get(i));
            if(f != null && f.isAdded()) {
                fragmentTransaction.hide(f);
            }
        }
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(R.id.fragment_container, fragment, fragmentTags.get(currIndex));
        }
        fragmentTransaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }

    private Fragment instantFragment(int currIndex) {
        switch (currIndex) {
            case 0:
                return new FirstFragment();//   MainPagerFragment()  LXMainFragment  MainFragment
//            case 1:
//                return new MainPagerFragment();
            case 2:
                return new SecondFragment();//BufferKnifeFragment();
            case 3:
                return new ThirdFragment();//ThirdFragment  MemberFragment
            default: return null;
        }
    }


    /**
     * 判断是否退出app
     */
    private static Boolean isExit = false;
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Timer tExit = null;
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (isExit == false) {
                isExit = true; // 准备退出
                Toast.makeText(mContext, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                tExit = new Timer();
                tExit.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isExit = false; // 取消退出
                    }
                }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
            } else {

                // 完全退出
                finish();
            }
        }
        return false;
    }
}
