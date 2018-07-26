package com.android.jd.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.android.jd.R;
import com.android.jd.mvp.base.IBaseSimpleView;
import com.android.jd.utils.UIUtils;
import com.partnfire.rapiddeveloplibrary.view.TitleBar;
import com.partnfire.rapiddeveloplibrary.view.Toasty;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author android
 * @date 2018/6/7
 * @return
 */

public abstract class SimpleActivity extends RxAppCompatActivity implements IBaseSimpleView {

    @Nullable
    @BindView(R.id.title_bar)
    TitleBar titleBar;

    public Context mContext;
    protected Activity mActivity;

    private boolean isShowLeftImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mContext = this;
        mActivity = this;
        initView(savedInstanceState);
        initListener();
        initTitle();
    }


/*******************************************titleBar***********************************************/
    @Nullable
    private void initTitle() {
        if (titleBar != null && !isShowLeftImage) {
            //titleBar.setImmersive(isImmersive);//如果是19以上计算标题高度
            titleBar.setLeftImageResource(R.mipmap.ic_back);
            titleBar.setLeftClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (titleBarActionListener != null) {
                        titleBarActionListener.onTitleBarLeftClick();
                    }
                }
            });
        }
    }

    /**
     * 背景颜色值
     *
     * @param res
     */
    public void setTitleColor(int res) {
        titleBar.setBackgroundColor(UIUtils.getColor(res));
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        if (titleBar != null) {
            titleBar.setTitle(title);
        }
    }

    /**
     * 设置标题
     *
     * @param res
     */
    public void setTitle(int res) {
        if (titleBar != null) {
            titleBar.setTitle(res);
        }
    }

    /**
     * 设置左图标
     *
     * @param res
     */
    public void setLeftImg(int res,boolean isShowLeftImage) {
        this.isShowLeftImage = isShowLeftImage;
        if (titleBar != null) {
            //titleBar.setImmersive(isImmersive);//如果是19以上计算标题高度
            titleBar.setLeftVisible(true);
            titleBar.setLeftImageResource(res);
            titleBar.setLeftClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (titleBarActionListener != null) {
                        titleBarActionListener.onTitleBarLeftClick();
                    }
                }
            });
        }
    }

    /**
     * 设置右图标
     *
     * @param res
     */
    ImageView rightImage;

    public void setRightImg(int res) {
        rightImage = (ImageView) titleBar.addAction(new TitleBar.ImageAction(res) {
            @Override
            public void performAction(View view) {
                if (titleBarActionListener != null) {
                    titleBarActionListener.onTitleBarRightClick();
                }
            }
        });
    }


/*******************************************标题点击回调***********************************************/
    private OnTitleBarActionListener titleBarActionListener;

    public void setonTitleBarActionListener(OnTitleBarActionListener listener) {
        titleBarActionListener = listener;
    }

    public interface OnTitleBarActionListener {
        void onTitleBarLeftClick();

        void onTitleBarRightClick();
    }
/*******************************************标题点击回调***********************************************/

    /**
     * Common Toast
     *
     * @param showText
     */
    public void showToast(String showText) {
        Toasty.normal(this, showText).show();
    }

    protected abstract int getLayoutId();

    //protected abstract void initInjector();//Dagger 注入

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initListener();

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.<T>bindToLifecycle();
    }

 /*******************************************转跳***********************************************/
    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        ((Activity) mContext).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        ((Activity) mContext).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
