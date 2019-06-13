package com.asv.champions.injection.component;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

import com.asv.champions.app.Application;
import com.asv.champions.injection.module.ActivityBuilder;
import com.asv.champions.injection.module.AppModule;

/**
 * Created by yercko on 17/3/18.
 */

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityBuilder.class})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance Builder application(android.app.Application application);
        AppComponent build();
    }

    ViewModelComponent.Builder viewModelComponentBuilder();


    void inject(Application app);
}

