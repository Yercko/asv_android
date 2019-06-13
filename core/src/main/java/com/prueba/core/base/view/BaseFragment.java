package com.prueba.core.base.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asv.core.R;
import com.prueba.core.base.component.FragmentComponent;
import com.prueba.core.base.component.FragmentComponentType;
import com.prueba.core.base.viewmodel.BaseViewModelType;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

import static android.app.Activity.RESULT_OK;

/**
 * Created by prueba on 2/2/17.
 */

public abstract class BaseFragment<B extends ViewDataBinding, V extends BaseViewModelType>
        extends Fragment implements ViewType {

    protected B binding;

    // Rx
    protected CompositeDisposable compositeDisposable;


    @Inject
    protected V viewModel;

    private FragmentComponentType mFragmentComponent;

    @Override
    @CallSuper
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(viewModel != null) { viewModel.saveInstanceState(outState); }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (compositeDisposable == null || compositeDisposable.isDisposed()) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.clear();
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

    @Override
    @CallSuper
    public void onDestroyView() {
        super.onDestroyView();
        if(viewModel != null) {
            viewModel.detachView();
        }
    }


    /* Sets the content view, creates the binding and attaches the view to the view model */
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

    protected final FragmentComponentType fragmentComponent() {
        if(mFragmentComponent == null) {
            mFragmentComponent = new FragmentComponent(this);
        }
        return mFragmentComponent;
    }

    public int dimen(@DimenRes int resId) {
        return (int) getResources().getDimension(resId);
    }

    public int color(@ColorRes int resId) {
        return getResources().getColor(resId);
    }

    public int integer(@IntegerRes int resId) {
        return getResources().getInteger(resId);
    }

    public String string(@StringRes int resId) {
        return getResources().getString(resId);
    }

    public final V viewModel() {
        return viewModel;
    }

    public void pushActivity(Intent intent) {
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.from_right, R.anim
                .to_left);
    }

    public void pushActivity(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
        getActivity().overridePendingTransition(R.anim.from_right, R.anim
                .to_left);
    }

    public void pushActivityToParent(Intent intent, int requestCode) {
        getActivity().startActivityForResult(intent, requestCode);
        getActivity().overridePendingTransition(R.anim.from_right, R.anim
                .to_left);
    }

    public void popActivity() {
        getActivity().setResult(RESULT_OK);
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.from_left, R.anim.to_right);
    }


    public void clearDisposable(){
        compositeDisposable.dispose();
    }

    public void popActivity(Intent intent) {
        getActivity().setResult(RESULT_OK, intent);
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.from_left, R.anim.to_right);
    }
}
