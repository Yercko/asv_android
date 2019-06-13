package com.asv.champions.ui.adapters;

import android.view.View;

import com.asv.champions.BR;
import com.asv.champions.databinding.ItemClubBinding;
import com.prueba.core.base.view.BaseViewHolder;


public class ClubTypeViewHolderAdapter extends BaseViewHolder<ItemClubBinding, ItemClubTypeViewModel> {

    public ClubTypeViewHolderAdapter(View itemView) {
        super(itemView);
        viewModel = new ItemClubTypeViewModel(itemView.getContext(),viewHolderComponent().coordinator());
        bindContentView(itemView, BR.vm);
    }


}