package com.android.jd.mvp.base;

/**
 * Created by android on 2017/1/17.
 * 基础 Presenter
 */
public abstract class IBasePresenter<T> {

    public T iBaseView;

    public void attach(T view){
        this.iBaseView = view;
    }

    public void detach(){
        this.iBaseView = null;
    }
}
