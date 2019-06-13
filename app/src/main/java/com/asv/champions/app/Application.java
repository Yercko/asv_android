package com.asv.champions.app;

import android.app.Activity;
import android.databinding.DataBindingUtil;

import com.asv.champions.injection.component.AppComponent;
import com.asv.champions.injection.component.DaggerAppComponent;
import com.asv.champions.injection.component.ViewModelComponent;
import com.asv.champions.injection.module.ViewModelModule;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

import com.asv.champions.ui.AppDataBindingComponent;
import com.prueba.core.base.BaseApplication;

/**
 * Created by yercko on 7/3/18.
 */

public class Application extends BaseApplication  implements HasActivityInjector /*,
        HasSupportFragmentInjector */ {


    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    protected static Application instance;

    AppComponent appComponent;
    ViewModelComponent viewModelComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appComponent = DaggerAppComponent
                .builder()
                .application(this)
                .build();
        appComponent.inject(this);
        DataBindingUtil.setDefaultComponent(new AppDataBindingComponent());

    }

    public static Application getInstance() {
        return instance;
    }


    public ViewModelComponent getViewModelComponent() {
        if(viewModelComponent ==null) {
            viewModelComponent =  appComponent.viewModelComponentBuilder()
                    .viewModelModule(new ViewModelModule())
                    .build();
        }
        return viewModelComponent;
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }


}


