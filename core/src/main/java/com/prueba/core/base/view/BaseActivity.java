package com.prueba.core.base.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.asv.core.R;
import com.prueba.core.base.BaseApplication;
import com.prueba.core.base.component.ActivityComponent;
import com.prueba.core.base.component.ActivityComponentType;
import com.prueba.core.base.utils.UIUtils;
import com.prueba.core.base.viewmodel.BaseViewModelType;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by prueba on 2/2/17.
 */

public abstract class BaseActivity<B extends ViewDataBinding, V extends BaseViewModelType>
        extends AppCompatActivity implements ViewType {

    protected B binding;

    protected CompositeDisposable compositeDisposable;
    private ProgressDialog mProgressDialog;

    @Inject
    protected V viewModel;


    private ActivityComponentType mActivityComponent;

    protected final ActivityComponentType activityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = new ActivityComponent(this, BaseApplication.getAppComponent());
        }
        return mActivityComponent;

    }

    protected final void setAndBindContentView(@LayoutRes int layoutResId,
                                               int variableId,
                                               @Nullable Bundle sis) {
        binding = DataBindingUtil.setContentView(this, layoutResId);
        binding.setVariable(variableId, viewModel);
        try {
            //noinspection unchecked
            viewModel.attachView((ViewType) this, sis);
        } catch(ClassCastException e) {
            throw new RuntimeException(getClass().getSimpleName() + " must implement ViewType subclass as declared in " + viewModel.getClass().getSimpleName());
        }
    }

    @Override @CallSuper protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(viewModel != null) { viewModel.saveInstanceState(outState); }
    }


    @Override
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (compositeDisposable == null || compositeDisposable.isDisposed()) {
            compositeDisposable = new CompositeDisposable();
        }
    }

    @Override @CallSuper protected void onDestroy() {
        super.onDestroy();
        if(viewModel != null) { viewModel.detachView(); }
        binding = null;
        viewModel = null;
        if( compositeDisposable!=null ) {
            compositeDisposable.dispose();
        }
    }

    protected void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        overridePendingTransition(R.anim.from_left, R.anim.to_right);
        return true;
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    public void showLoading() {
        hideLoading();
        mProgressDialog = UIUtils.showLoadingDialog(this);
        mHandler.postDelayed(runnable, 8000);
    }


    private Handler mHandler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            hideLoading();
        }
    };

    public void pushActivity(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.from_right, R.anim
                .to_left);
    }

    public void pushActivity(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.from_right, R.anim
                .to_left);
    }

    public void pushActivity(Intent intent, int requestCode, boolean activate) {
        if (activate) {
            startActivityForResult(intent, requestCode);
            overridePendingTransition(R.anim.from_right, R.anim
                    .to_left);
        }
    }
    public void popActivity() {
        setResult(RESULT_OK);
        finish();
        overridePendingTransition(R.anim.from_left, R.anim.to_right);
    }


    public void popActivity(Intent intent) {
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.from_left, R.anim.to_right);
    }

}