package com.android.jd.mvp.base;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * 简单的View
 * @Author android
 * @date 2018/6/7
 * @return
 */

public interface IBaseSimpleView {
    /**
     * 绑定生命周期
     * @param <T>
     * @return
     */
    <T> LifecycleTransformer<T> bindToLife();
}
