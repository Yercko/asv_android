package com.asv.champions.injection.module;

import android.app.Application;
import android.content.Context;


import com.asv.champions.network.ApiFactory;
import com.asv.champions.network.AsvService;
import com.asv.champions.ui.clubadd.ClubAddComponent;
import com.asv.champions.ui.clubslist.ClubListComponent;
import com.asv.champions.ui.selector.TitleImageSelectorActivityComponent;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yercko on 17/3/18.
 */
@Module(subcomponents = {
        ClubListComponent.class,
        ClubAddComponent.class,
        TitleImageSelectorActivityComponent.class
})
public class AppModule {
    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    AsvService provideService() {
        return ApiFactory.createService();
    }
}
