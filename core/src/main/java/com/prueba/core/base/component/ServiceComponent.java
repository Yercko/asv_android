package com.prueba.core.base.component;

import android.content.Context;
import android.content.res.Resources;
//import io.realm.Realm;
//import io.realm.RealmConfiguration;

/**
 * Created by prueba on 13/2/17.
 */

public class ServiceComponent implements ServiceComponentType{

    private final AppComponentType mAppComponent;

    public ServiceComponent(AppComponentType appComponent) {
        mAppComponent = appComponent;
    }

    @Override
    public Context appContext() {
        return mAppComponent.appContext();
    }

    @Override
    public Resources resources() {
        return mAppComponent.resources();
    }

//    @Override
//    public RealmConfiguration realmConfiguration() {
//        return mAppComponent.realmConfiguration();
//    }
//
//    @Override
//    public Realm realm() {
//        return mAppComponent.realm();
//    }

}
