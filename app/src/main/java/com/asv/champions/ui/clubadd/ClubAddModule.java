package com.asv.champions.ui.clubadd;

import com.asv.champions.model.ClubItem;

import dagger.Module;
import dagger.Provides;

@Module
public class ClubAddModule {

    @Provides
    ClubItem provideViewModel(ClubAddActivity activity) {
        return activity.getClubItem();
    }


}

