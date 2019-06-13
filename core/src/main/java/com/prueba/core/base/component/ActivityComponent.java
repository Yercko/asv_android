package com.prueba.core.base.component;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

//import com.google.android.gms.analytics.Tracker;

import com.prueba.core.base.coordinator.ActivityCoordinator;
import com.prueba.core.base.coordinator.CoordinatorType;
import io.reactivex.Scheduler;
//import io.realm.Realm;
//import io.realm.RealmConfiguration;

/**
 * Created by prueba on 6/2/17.
 */

public class ActivityComponent implements ActivityComponentType {

    private final FragmentActivity mActivity;
    private final AppComponentType mAppComponent;


    public ActivityComponent(FragmentActivity activity, AppComponentType appComponent) {
        mActivity = activity;
        mAppComponent = appComponent;
    }

    @Override
    public Context activityContext() {
        return mActivity;
    }

    @Override
    public FragmentManager fragmentManager() {
        return mActivity.getSupportFragmentManager();
    }

    @Override
    public CoordinatorType coordinator() {
        return new ActivityCoordinator(mActivity);
    }

    @Override
    public Scheduler subscribeScheduler() {
        return mAppComponent.subscribeScheduler();
    }

    @Override
    public Context appContext() {
        return mAppComponent.appContext();
    }

    @Override
    public Resources resources() {
        return mAppComponent.resources();
    }





   // @Override
   // public RealmConfiguration realmConfiguration() {
   //     return mAppComponent.realmConfiguration();
   // }

    //@Override
    //public Realm realm() {
    //    return mAppComponent.realm();
    //}

    //@Override
    //public Tracker tracker() {
    //    return mAppComponent.tracker();
    //}


}
