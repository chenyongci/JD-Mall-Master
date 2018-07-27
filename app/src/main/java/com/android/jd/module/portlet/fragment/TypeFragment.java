package com.android.jd.module.portlet.fragment;

import com.android.jd.R;
import com.android.jd.base.SimpleFragment;

/**
 * @Author : chenyongci
 * @date : 2018/7/26
 * @desc :
 */

public class TypeFragment extends SimpleFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_type;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    public static TypeFragment newInstance() {
        return new TypeFragment();
    }
}
