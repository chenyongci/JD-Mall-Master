package com.android.jd;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.android.jd.app.BaseApplication;
import com.android.jd.base.SimpleActivity;
import com.android.jd.module.portlet.fragment.HomeFragment;
import com.android.jd.module.portlet.fragment.MineFragment;
import com.android.jd.module.portlet.fragment.ShopFragment;
import com.android.jd.module.portlet.fragment.TypeFragment;
import com.partnfire.rapiddeveloplibrary.view.RxDialog;
import com.partnfire.rapiddeveloplibrary.widget.bottomnavigation.BadgeItem;
import com.partnfire.rapiddeveloplibrary.widget.bottomnavigation.BottomNavigationBar;
import com.partnfire.rapiddeveloplibrary.widget.bottomnavigation.BottomNavigationItem;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

public class MainActivity extends SimpleActivity implements BottomNavigationBar.OnTabSelectedListener{
    private static final int TIME = 2000;
    boolean isExit = false;



    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    @BindView(R.id.main_container)
    FrameLayout mainContainer;
    private HomeFragment homeFragment;
    private TypeFragment typeFragment;
    private ShopFragment shopFragment;
    private MineFragment mimeFragment;

    private FragmentManager mFragmentManager;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mFragmentManager = getSupportFragmentManager();
        initTab();
        initFragment();

        iniDialog();
    }

    private void iniDialog() {
        final RxDialog rxDialog = new RxDialog(mContext, R.style.tran_dialog);
        View view1 = LayoutInflater.from(mContext).inflate(R.layout.view_webview, null);
        WebView webView = (WebView) view1.findViewById(R.id.webview_detail);
        WebSettings ws = webView.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        view1.findViewById(R.id.iv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rxDialog.dismiss();
            }
        });

        // 这行代码一定加上否则效果不会出现
        ws.setJavaScriptEnabled(true);
        webView.loadUrl("https://h5.m.jd.com/dev/4A4UWDX5mxSFSFS6h95hZr6eNeFr/index.html?app_home_xview=1");
        webView.setBackgroundColor(Color.parseColor("#00000000"));
        rxDialog.setFullScreenWidth();
        rxDialog.setContentView(view1);
        rxDialog.show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //奔溃前保存位置
        /*if (mTabLayout != null) {
            outState.putInt(AppConstant.HOME_CURRENT_TAB_POSITION, mTabLayout.getCurrentTab());
        }*/
    }



    /**
     * 初始化碎片
     */
    private void initFragment() {
        homeFragment = (HomeFragment) mFragmentManager.findFragmentByTag("home_fg");
        typeFragment = (TypeFragment) mFragmentManager.findFragmentByTag("class_fg");
        shopFragment = (ShopFragment) mFragmentManager.findFragmentByTag("find_fg");
        mimeFragment = (MineFragment) mFragmentManager.findFragmentByTag("mine_fg");

        if(homeFragment == null){
            homeFragment = HomeFragment.newInstance();
            addFragment(R.id.main_container, homeFragment, "home_fg");
        }
        if(typeFragment == null){
            typeFragment = TypeFragment.newInstance();
            addFragment(R.id.main_container, typeFragment, "class_fg");
        }

        if(shopFragment == null){
            shopFragment = ShopFragment.newInstance();
            addFragment(R.id.main_container, shopFragment, "find_fg");
        }

        if(mimeFragment == null){
            mimeFragment = MineFragment.newInstance();
            addFragment(R.id.main_container, mimeFragment, "mine_fg");
        }
        mFragmentManager.beginTransaction().show(homeFragment).hide(typeFragment).hide(shopFragment).hide(mimeFragment)
                .commitAllowingStateLoss();

        initBottomNavigation();
    }

    private void initBottomNavigation() {
        BadgeItem numberBadgeItem = new BadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.colorAccent)
                .setText("99+")
                .setHideOnSelect(false);

        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        //bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        //bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        //bottomNavigationBar.setAutoHideEnabled(true);



        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.ic_tab_home_pressed, "").setInactiveIconResource(R.mipmap.ic_tab_home_unpressed).setActiveColorResource(R.color.colorTheme))
                .addItem(new BottomNavigationItem(R.mipmap.ic_tab_type_pressed, "").setInactiveIconResource(R.mipmap.ic_tab_type_unpressed).setActiveColorResource(R.color.colorTheme))
                .addItem(new BottomNavigationItem(R.mipmap.ic_tab_shop_pressed, "").setInactiveIconResource(R.mipmap.ic_tab_shop_unpressed).setActiveColorResource(R.color.colorTheme))
                .addItem(new BottomNavigationItem(R.mipmap.ic_tab_mine_pressed, "").setInactiveIconResource(R.mipmap.ic_tab_mine_unpressed).setActiveColorResource(R.color.colorTheme).setBadgeItem(numberBadgeItem))
                .setFirstSelectedPosition(0)
                .initialise();

        bottomNavigationBar.setTabSelectedListener(this);
    }


    private void initTab() {

    }


    @Override
    protected void initListener() {


    }

    /**
     * 切换
     */
    private void switchTo(int position) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        /*setIntent(intent);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentTabIndex = extras.getInt(AppConstant.TABINDEX);
            switchTo(currentTabIndex);
            mTabLayout.setCurrentTab(currentTabIndex);
        }*/
    }


    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByTwoClick();      //调用双击退出函数
        }
        return false;
    }

    private void exitByTwoClick() {
        if (!isExit) {
            isExit = true;
            showToast(getString(R.string.home_exit));
            Timer tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, TIME);

        } else {
            BaseApplication.getInstance().exitApp();
        }
    }

    @Override
    public void onTabSelected(int position) {
        if(position == 0){
            mFragmentManager.beginTransaction().show(homeFragment).hide(typeFragment).hide(shopFragment)
                    .commitAllowingStateLoss();
        }
        else if(position == 1){
            mFragmentManager.beginTransaction().hide(homeFragment).show(typeFragment).hide(shopFragment)
                    .commitAllowingStateLoss();
        }
        else if(position == 2){
            mFragmentManager.beginTransaction().hide(homeFragment).hide(typeFragment).show(shopFragment)
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
