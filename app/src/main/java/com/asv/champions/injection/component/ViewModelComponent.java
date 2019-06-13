package com.asv.champions.injection.component;


import com.asv.champions.injection.module.ViewModelModule;
import com.asv.champions.ui.adapters.ItemClubTypeViewModel;
import com.asv.champions.ui.clubadd.ClubAddViewModel;
import com.asv.champions.ui.clubslist.ClubListViewModel;
import com.asv.champions.ui.selector.TitleImageSelectorViewModel;

import dagger.Subcomponent;

/**
 * Created by yercko on 22/3/18.
 */


@Subcomponent(modules = ViewModelModule.class)
public interface ViewModelComponent {
    @Subcomponent.Builder
    interface Builder {
        ViewModelComponent.Builder viewModelModule(ViewModelModule module);
        ViewModelComponent build();
    }

    void inject(ClubListViewModel viewModel);
    void inject(ItemClubTypeViewModel viewModel);
    void inject(ClubAddViewModel viewModel);
    void inject(TitleImageSelectorViewModel viewModel);

}
