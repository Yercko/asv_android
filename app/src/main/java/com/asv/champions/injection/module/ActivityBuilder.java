package com.asv.champions.injection.module;


import com.asv.champions.ui.clubadd.ClubAddActivity;
import com.asv.champions.ui.clubadd.ClubAddModule;
import com.asv.champions.ui.clubslist.ClubListActivity;
import com.asv.champions.ui.clubslist.ClubListModule;
import com.asv.champions.ui.selector.TitleImageSelectorActivity;
import com.asv.champions.ui.selector.TitleImageSelectorActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by yercko on 17/3/18.
 */

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = ClubListModule.class)
    abstract ClubListActivity bindClubListActivity();

    @ContributesAndroidInjector(modules = ClubAddModule.class)
    abstract ClubAddActivity bindClubAddActivity();

    @ContributesAndroidInjector(modules = TitleImageSelectorActivityModule.class)
    abstract TitleImageSelectorActivity bindTitleImageActivity();

}