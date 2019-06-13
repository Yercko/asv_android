package com.prueba.core.base.component;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.FragmentManager;
import android.view.View;

//import com.google.android.gms.analytics.Tracker;

import com.prueba.core.base.BaseApplication;
import com.prueba.core.base.coordinator.CoordinatorType;
import com.prueba.core.base.utils.ClassUtils;
import com.prueba.core.base.view.BaseActivity;
import io.reactivex.Scheduler;
//import io.realm.Realm;
//import io.realm.RealmConfiguration;

/**
 * Created by prueba on 6/2/17.
 */

public class ViewHolderComponent implements ViewHolderComponentType {

    private ActivityComponentType mActivityComponent;

    public ViewHolderComponent(View view) {
        mActivityComponent = new ActivityComponent(ClassUtils.castActivityFromContext(view.getContext(), BaseActivity.class), BaseApplication.getAppComponent());
    }


    @Override
    public Context activityContext() {
        return mActivityComponent.activityContext();
    }

    @Override
    public FragmentManager fragmentManager() {
        return mActivityComponent.fragmentManager();
    }

    @Override
    public CoordinatorType coordinator() {
        return mActivityComponent.coordinator();
    }

    @Override
    public Context appContext() {
        return mActivityComponent.appContext();
    }

    @Override
    public Resources resources() {
        return mActivityComponent.resources();
    }


    @Override
    public Scheduler subscribeScheduler() {
        return mActivityComponent.subscribeScheduler();
    }

    //@Override
    //public RealmConfiguration realmConfiguration() {
    //    return mActivityComponent.realmConfiguration();
    //}

    //@Override
    //public Realm realm() {
    //    return mActivityComponent.realm();
    //}

    //@Override
    //public Tracker tracker() {
    //    return mActivityComponent.tracker();
    //}
}
