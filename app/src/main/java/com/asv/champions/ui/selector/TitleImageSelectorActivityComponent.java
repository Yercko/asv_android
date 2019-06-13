package com.asv.champions.ui.selector;


import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent(modules = TitleImageSelectorActivityModule.class)
public interface TitleImageSelectorActivityComponent extends AndroidInjector<TitleImageSelectorActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<TitleImageSelectorActivity>{}
}
