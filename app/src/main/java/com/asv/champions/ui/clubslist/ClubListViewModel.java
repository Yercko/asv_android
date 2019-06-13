package com.asv.champions.ui.clubslist;

import android.content.Context;
import android.content.Intent;
import android.databinding.Bindable;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.asv.champions.R;
import com.asv.champions.app.Application;
import com.asv.champions.model.ClubItem;
import com.asv.champions.model.DeleteRequest;
import com.asv.champions.network.AsvService;
import com.asv.champions.ui.adapters.ClubTypeAdapter;
import com.prueba.core.base.view.ViewType;
import com.prueba.core.base.viewmodel.ListViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class ClubListViewModel extends ListViewModel<ClubItem, AsvService, ViewType> {
    private ClubTypeAdapter adapter;
    private BehaviorSubject<Boolean> mLoading = BehaviorSubject.createDefault(false);
    private PublishSubject<String> idRemove = PublishSubject.create();

    @Inject
    public ClubListViewModel(Context context) {
        Application.getInstance().getViewModelComponent().inject(this);
        adapter = new ClubTypeAdapter(this);

        updateList();
    }

    public void updateList(){
        mLoading.onNext(true);
        compositeDisposable.add(
                service().getAllClubs()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(s -> {
                            mLoading.onNext(false);
                            setModel(s);
                            notifyChange();
                        }, error ->{
                            mLoading.onNext(false);
                        })
        );
    }

    public void deleteItem(DeleteRequest request){
        mLoading.onNext(true);
        compositeDisposable.add(
                service().deleteClub(request)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(s -> {
                            updateList();
                        }, error ->{
                            mLoading.onNext(false);
                        })
        );
    }


    public void nextRemoveId(String name){
        idRemove.onNext(name);
    }

    public Observable<String> emitRemove(){
        return idRemove;
    }

    @Bindable
    public List<ClubItem> getData() {
        return model();
    }

    @Bindable
    public ClubTypeAdapter getAdapter() {
        return adapter;
    }

    public Observable<Boolean> emitLoading(){
        return mLoading;
    }
}