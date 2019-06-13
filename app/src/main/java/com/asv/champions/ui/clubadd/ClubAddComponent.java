package com.asv.champions.ui.clubadd;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;


@Subcomponent(modules = ClubAddModule.class)
public interface ClubAddComponent extends
        AndroidInjector<ClubAddActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<ClubAddActivity>{}

}