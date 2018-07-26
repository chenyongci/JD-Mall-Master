package com.android.jd.mvp.base;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * @Author android
 * @date 2018/6/7
 * @return
 */

public interface IBaseView {
    /**
     * 显示加载动画
     */
    void showProgress();
    /**
     * 隐藏加载
     */
    void hideProgress();
    /**
     * 显示加载失败
     */
    void showLoadFailMsg();

    /**
     *显示已加载所有数据
     */
    void showLoadCompleteAllData();

    /**
     * 取消刷新
     */
    void finishRefresh();

    /**
     *显示无数据
     */
    void showNoData(int resId, String emptyTextTitle, String emptyTextContent);

    /**
     * 绑定生命周期
     * @param
     * @return
     */

    LifecycleTransformer bindToLife();
}
