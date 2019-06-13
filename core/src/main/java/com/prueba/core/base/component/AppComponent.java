package com.prueba.core.base.component;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

//import com.google.android.gms.analytics.GoogleAnalytics;
//import com.google.android.gms.analytics.Tracker;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
//import io.realm.Realm;
//import io.realm.RealmConfiguration;

/**
 * Created by prueba on 6/2/17.
 */

public class AppComponent implements AppComponentType {

    private final Application mApp;

    private Scheduler scheduler;

  //  private RealmConfiguration realmConfiguration;

  //  private Tracker mTracker;

    public AppComponent(Application app) {
        mApp = app;
    }

    @Override
    public Context appContext() {
        return mApp;
    }

    @Override
    public Resources resources() {
        return mApp.getResources();
    }


    public Scheduler subscribeScheduler() {
        if (scheduler == null) {
            scheduler = Schedulers.io();
        }

        return scheduler;
    }

//    @Override
//    public RealmConfiguration realmConfiguration() {
//        if (realmConfiguration==null) {
//            RealmConfiguration.Builder builder = new RealmConfiguration.Builder();
//            builder.name(Realm.DEFAULT_REALM_NAME);
//            builder.schemaVersion(0);
//            if(BuildConfig.DEBUG) {
//                builder = builder.deleteRealmIfMigrationNeeded();
//            }
//            realmConfiguration = builder.build();
//        }
//        return realmConfiguration;
//    }

    //@Override
    //public Realm realm() {
    //    return Realm.getInstance(realmConfiguration());
    //}


//    @Override
//    synchronized public Tracker tracker() {
//        if (mTracker == null) {
//            GoogleAnalytics analytics = GoogleAnalytics.getInstance(mApp);
//            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
//            mTracker = analytics.newTracker(R.xml.global_tracker);
//        }
//        return mTracker;
//    }
}
