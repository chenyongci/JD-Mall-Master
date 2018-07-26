package com.android.jd.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.android.jd.R;
import com.android.jd.mvp.base.IBasePresenter;
import com.android.jd.mvp.base.IBaseView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.partnfire.rapiddeveloplibrary.view.Toasty;
import com.partnfire.rapiddeveloplibrary.widget.ProgressActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author android
 * @date 2018/6/6
 * @return
 */

public abstract class BaseActivity<T extends IBasePresenter, V extends BaseQuickAdapter>
        extends RxAppCompatActivity implements IBaseView {
    public Context mContext;
    protected Activity mActivity;

    @Nullable
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    /**
     * 把 ProgressActivity 放在基类统一处理，@Nullable 表明 View 可以为 null，详细可看 ButterKnife
     */
    @Nullable
    @BindView(R.id.progress)
    ProgressActivity progress;

    protected T mPresenter;
    public V mAdapter;


    /**
     * Common Toast
     *
     * @param showText
     */
    public void showToast(String showText) {
        Toasty.normal(this, showText).show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mContext = this;
        mActivity = this;
        initView();
        initListener();
        updateViews(false);//刷新
        initRefresh();
    }

    protected abstract int getLayoutId();

    //protected abstract void initInjector();//Dagger 注入

    protected abstract void initView();

    protected abstract void initListener();

    /**
     * 更新视图控件
     *
     * @param isRefresh 新增参数，用来判断是否为下拉刷新调用，下拉刷新的时候不应该再显示加载界面和异常界面
     */
    protected abstract void updateViews(boolean isRefresh);

    protected abstract void moreRequested();

    @Nullable
    private void initRefresh() {
        if (refreshLayout == null) {
            return;
        }
        //设置 Header 为 经典 样式
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        //设置 Footer 为 经典 样式
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        //refreshLayout.setEnableAutoLoadMore(true);//开启自动加载功能（非必须）
        //设置是否启用上拉加载更多（默认启用）
        refreshLayout.setEnableLoadMore(true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateViews(true);//刷新
                        //mAdapter.refresh(initData());
                        refreshLayout.finishRefresh();
                        refreshLayout.setNoMoreData(false);
                    }
                }, 2000);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        moreRequested();
                        /*if (mAdapter.getItemCount() > 30) {
                            Toast.makeText(getApplication(), "数据全部加载完毕", Toast.LENGTH_SHORT).show();
                            refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                        } else {
                            mAdapter.loadMore(initData());
                            refreshLayout.finishLoadMore();
                        }*/
                    }
                }, 2000);
            }
        });
    }

    /**
     * 判断是否加载更多
     *
     * @param total
     */
    public void isLoadMoreEnd(int total) {
        int size = mAdapter.getData().size();
        if (size == total) {
            mAdapter.loadMoreEnd(false);
        } else {
            mAdapter.loadMoreComplete();
        }
    }

    /**
     * ProgressActivity 运用方法
     */
    @Override
    public void showProgress() {
        if (progress != null) {
            progress.showLoading();
        }
    }

    @Override
    public void hideProgress() {
        if (progress != null) {
            progress.showContent();
        }
    }

    @Override
    public void finishRefresh() {
        /*if (refreshLayout != null) {
            refreshLayout.finishRefresh();//结束刷新
            refreshLayout.setNoMoreData(false);
        }*/
    }

    @Override
    public void showLoadFailMsg() {
        //设置加载错误页显示
        if (progress != null) {
            progress.showError(mContext, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateViews(false);//刷新
                }
            });
        }
    }

    @Override
    public void showLoadCompleteAllData() {

    }

    @Override
    public void showNoData(int resId, String emptyTextTitle, String emptyTextContent) {
        //设置无数据显示页面
        if (progress != null) {
            progress.showEmpty(emptyTextTitle, emptyTextContent);
        }
    }

    @Override
    public  LifecycleTransformer<T> bindToLife() {
        return this.bindToLifecycle();
    }
}
