package com.prueba.core.base.component;

import android.content.Context;
import android.content.res.Resources;

import io.reactivex.Scheduler;

//import com.google.android.gms.analytics.Tracker;

//import io.realm.Realm;
//import io.realm.RealmConfiguration;

/**
 * Created by prueba on 6/2/17.
 */

public interface AppComponentType {
    Context appContext();
    Resources resources();
    Scheduler subscribeScheduler();

    //RealmConfiguration realmConfiguration();
    //Realm realm();

    //Tracker tracker();

}
