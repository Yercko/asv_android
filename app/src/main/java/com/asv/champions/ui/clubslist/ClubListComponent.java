package com.asv.champions.ui.clubslist;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;


@Subcomponent(modules = ClubListModule.class)
public interface ClubListComponent extends
        AndroidInjector<ClubListActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<ClubListActivity>{}

}