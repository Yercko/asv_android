package com.asv.champions.ui.selector;


import dagger.Module;
import dagger.Provides;

@Module
public class TitleImageSelectorActivityModule {

    @Provides
    TitleImageSelectorViewModel provideViewModel() {
        return new TitleImageSelectorViewModel();
    }

}
