package com.dhc.library.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dhc.library.di.IDaggerListener;
import com.dhc.library.di.module.FragmentModule;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

/**
 * 创建者：邓浩宸
 * 时间 ：2016/11/15 16:07
 * 描述 ：MVP Fragment基类
 */
public abstract class XDaggerFragment<T extends IBasePresenter> extends BaseFragment implements IBaseView, IDaggerListener {

    @Inject
    protected T mPresenter;
    public boolean isShowView=false;
    protected FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        if (layoutId > 0)
            mView = inflater.inflate(layoutId, null);
        initInject(savedInstanceState);
        return mView;
    }

    @Override
    public <E> LifecycleTransformer<E> bindLifecycle() {
        return this.<E>bindToLifecycle();
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (mPresenter != null) {
            isShowView = true;
            mPresenter.attachView(this);
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public Context getAContext() {
        return _mActivity;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }
}