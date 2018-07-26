package com.android.jd.module.portlet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.jd.R;
import com.android.jd.base.SimpleActivity;
import com.android.jd.constants.ConstantsImageUrl;
import com.android.jd.utils.CommonUtils;
import com.bumptech.glide.Glide;

import java.lang.ref.WeakReference;
import java.util.Random;

import butterknife.BindView;

/**
 * @Author : chenyongci
 * @date : 2018/7/26
 * @desc :
 */

public class SplashActivity extends SimpleActivity {
    private boolean isIn;
    private MyHandler handler;

    @BindView(R.id.tv_jump)
    TextView tvJump;
    @BindView(R.id.iv_defult_pic)
    ImageView ivDefultPic;
    @BindView(R.id.iv_pic)
    ImageView ivPic;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        // 后台返回时可能启动这个页面 http://blog.csdn.net/jianiuqi/article/details/54091181
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        showImage();
    }

    private void showImage() {
        int i = new Random().nextInt(ConstantsImageUrl.TRANSITION_URLS.length);
        // 先显示默认图
        ivDefultPic.setImageDrawable(CommonUtils.getDrawable(R.mipmap.bg_splash));
        Glide.with(this)
                .load(ConstantsImageUrl.TRANSITION_URLS[i])
                //.placeholder(R.drawable.img_transition_default)
                //.error(R.mipmap.bg_splash)
                .into(ivPic);



        handler = new MyHandler(this);
        handler.sendEmptyMessageDelayed(0, 1500);
        handler.sendEmptyMessageDelayed(1, 3500);
    }

    @Override
    protected void initListener() {
        tvJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toMainActivity();
            }
        });
    }

    class MyHandler extends Handler {
        private WeakReference<SplashActivity> mOuter;

        MyHandler(SplashActivity activity) {
            mOuter = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SplashActivity outer = mOuter.get();
            if (outer != null) {
                something(outer, msg);
            }
        }

        private void something(SplashActivity outer, Message msg) {
            switch (msg.what) {
                case 0:
                    ivDefultPic.setVisibility(View.GONE);
                    break;
                case 1:
                    outer.toMainActivity();
                    break;
                default:
                    break;
            }
        }
    }

    private void toMainActivity() {
        if (isIn) {
            return;
        }
        Intent intent = new Intent(this, GuideActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        finish();
        isIn = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }
}
