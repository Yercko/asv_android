package com.prueba.core.base.view;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;

import com.prueba.core.base.component.FragmentComponentType;
import com.prueba.core.base.viewmodel.BaseViewModelType;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by amitshekhar on 10/07/17.
 */

public abstract class BaseDialog<B extends ViewDataBinding, V extends BaseViewModelType> extends DialogFragment implements ViewType {

    private BaseActivity mActivity;

    protected B binding;

    // Rx
    protected CompositeDisposable compositeDisposable;


    @Inject
    protected V viewModel;

    private FragmentComponentType mFragmentComponent;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity mActivity = (BaseActivity) context;
            this.mActivity = mActivity;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(compositeDisposable==null) {
            compositeDisposable = new CompositeDisposable();
        }
        // the content
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        // creating the fullscreen dialog
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }

    @Override
    public void onDetach() {
        if(viewModel != null) {
            viewModel.detachView();
        }
        mActivity = null;
        super.onDetach();
    }

    public void show(FragmentManager fragmentManager, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment prevFragment = fragmentManager.findFragmentByTag(tag);
        if (prevFragment != null) {
            transaction.remove(prevFragment);
        }
        transaction.addToBackStack(null);
        show(transaction, tag);
    }

    public void dismissDialog(String tag) {
        dismiss();
    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        }
    }

    public void hideLoading() {
        if (mActivity != null) {
            mActivity.hideLoading();
        }
    }

    @Override
    @CallSuper
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(viewModel != null) { viewModel.saveInstanceState(outState); }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        binding = null;
        viewModel = null;
    }


    protected final View setAndBindContentView(@NonNull LayoutInflater inflater,
                                               @Nullable ViewGroup container,
                                               @Nullable Bundle savedInstanceState,
                                               @LayoutRes int layoutResID,
                                               int variableId) {
        if(viewModel == null) { throw new IllegalStateException("viewModel must already be set"); }
        binding = DataBindingUtil.inflate(inflater, layoutResID, container, false);
        binding.setVariable(variableId, viewModel);

        try {
            viewModel.attachView((ViewType) this, savedInstanceState);
        } catch(ClassCastException e) {
            throw new RuntimeException(getClass().getSimpleName() + " must implement ViewType subclass as declared in " + viewModel.getClass().getSimpleName());
        }

        return binding.getRoot();
    }

    /*
    public void openActivityOnTokenExpire() { 
        if (mActivity != null) {
            mActivity.openActivityOnTokenExpire();
        }
    }
*/

    public void showLoading() {
        if (mActivity != null) {
            mActivity.showLoading();
        }
    }
}