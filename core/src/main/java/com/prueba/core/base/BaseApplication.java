package com.prueba.core.base;

import android.support.multidex.MultiDexApplication;

import com.prueba.core.base.component.AppComponent;
import com.prueba.core.base.component.AppComponentType;
//import io.realm.Realm;

/**
 * Created by prueba on 13/2/17.
 */

public abstract class BaseApplication extends MultiDexApplication {
    private static AppComponentType mAppComponent = null;

    public BaseApplication() {
        super();
        mAppComponent = new AppComponent(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
   //     Realm.init(this);
    }

    public static AppComponentType getAppComponent() {
        return mAppComponent;
    }
}
