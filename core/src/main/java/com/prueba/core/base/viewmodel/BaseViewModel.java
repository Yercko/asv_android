package com.prueba.core.base.viewmodel;

/**
 * Created by prueba on 2/2/17.
 */

import android.content.Context;
import android.databinding.BaseObservable;
import android.os.Bundle;
import android.support.annotation.CallSuper;

import com.prueba.core.base.BaseApplication;
import com.prueba.core.base.view.ViewType;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseViewModel<V extends ViewType> extends BaseObservable implements BaseViewModelType<V> {

    // Vista
    protected V mView;

    @Inject
    protected Context context;

    // Rx
    protected CompositeDisposable compositeDisposable;

    public BaseViewModel() {
        this.compositeDisposable = new CompositeDisposable();
    }

    public BaseViewModel(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @CallSuper
    public void attachView(V view, Bundle sis) {
        this.mView = view;
        if (sis != null) {
            restoreInstanceState(sis);
        }
        if(compositeDisposable==null) {
            compositeDisposable = new CompositeDisposable();
        }
    }

    @CallSuper
    public void detachView() {
        this.mView = null;
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    protected void restoreInstanceState(Bundle sis) {
    }

    public void saveInstanceState(Bundle outState) {
    }

    protected final V view() {
        return mView;
    }

    @Override
    public Scheduler subscribeScheduler() {
        return BaseApplication.getAppComponent().subscribeScheduler();
    }

    public void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

}
