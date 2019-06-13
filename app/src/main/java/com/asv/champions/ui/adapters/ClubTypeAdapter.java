package com.asv.champions.ui.adapters;


import com.asv.champions.R;
import com.asv.champions.model.ClubItem;
import com.asv.champions.ui.clubslist.ClubListViewModel;
import com.prueba.core.base.view.BaseAdapter;

public class ClubTypeAdapter extends BaseAdapter<ClubTypeViewHolderAdapter, ClubItem> {

    ClubListViewModel parentViewModel;

    public ClubTypeAdapter(ClubListViewModel parentViewModel) {
        super(R.layout.item_club);
        this.parentViewModel = parentViewModel;
    }

    @Override
    public void onBindViewHolder(ClubTypeViewHolderAdapter holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.viewModel().setParentViewModel(parentViewModel);

    }


}
