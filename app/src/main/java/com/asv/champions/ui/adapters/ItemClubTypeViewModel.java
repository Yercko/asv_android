package com.asv.champions.ui.adapters;

import android.content.Context;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.asv.champions.R;
import com.asv.champions.app.Application;
import com.asv.champions.model.ClubItem;
import com.asv.champions.model.DeleteRequest;
import com.asv.champions.ui.clubadd.ClubAddActivity;
import com.asv.champions.ui.clubslist.ClubListViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.prueba.core.base.coordinator.CoordinatorType;
import com.prueba.core.base.view.ViewType;
import com.prueba.core.base.viewmodel.BasicViewModel;


public class ItemClubTypeViewModel extends BasicViewModel<ClubItem, ViewType> {

    private CoordinatorType mCoordinator;
    private ClubListViewModel parentViewModel;

    public ItemClubTypeViewModel(Context context, CoordinatorType coordinator) {
        Application.getInstance().getViewModelComponent().inject(this);
        mCoordinator = coordinator;
        this.context = context;


    }

    public void setParentViewModel(ClubListViewModel parentViewModel) {
        this.parentViewModel = parentViewModel;
    }

    public void onItemClick(){
        mCoordinator.startActivity(ClubAddActivity.getStartIntent(context,model()));
    }

    public boolean onItemLongClick(){
        parentViewModel.nextRemoveId(model().getName());

        return false;
    }

    @BindingAdapter({"app:onClick"})
    public static void setOnClick(View view, View.OnClickListener clickListener) {
        view.setOnClickListener(clickListener);
    }

    @BindingAdapter({"app:onLongClick"})
    public static void setOnLongClick(View view, View.OnLongClickListener clickListener) {
        view.setOnLongClickListener(clickListener);
    }


    @Bindable
    public String getName(){
        return model().getName();
    }

    @Bindable
    public String getUrl(){
        return model().getPhoto();
    }

    @Bindable
    public String getRival(){
        return model().getRival();
    }

    @Bindable
    public String getCountry(){
        return model().getCountry();
    }

    @Bindable
    public String getCountChampions(){
        return model().getChampions().size()+"";
    }


    public boolean onLongClickFriend(View view) {
        return true;
    }

}
