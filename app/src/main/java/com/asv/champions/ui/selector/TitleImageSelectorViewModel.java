package com.asv.champions.ui.selector;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.asv.champions.app.Application;
import com.asv.champions.network.AsvService;
import com.prueba.core.base.view.ViewType;
import com.prueba.core.base.viewmodel.ListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

public class TitleImageSelectorViewModel extends ListViewModel<TitleImage, AsvService, ViewType> {

    PublishSubject<Integer> mSelectCountry = PublishSubject.create();
    PublishSubject<CharSequence> mSearchText = PublishSubject.create();
    Observable<List<TitleImage>> mFilterCountrys;

    @Inject
    public TitleImageSelectorViewModel() {
        Application.getInstance().getViewModelComponent().inject(this);
        mFilterCountrys = mSearchText
                .map(this::updateDataList);

    }

    @Override
    public void addToModel(@NonNull List<TitleImage> items) {
        super.addToModel(items);
    }

    public void bindSearchEvent( Observable<CharSequence> valorBuscado){
        valorBuscado
                .throttleLast(300, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(mSearchText);

    }

    public List<TitleImage> updateDataList(CharSequence texto){
        List<TitleImage> result = new ArrayList<>();
        if ( !TextUtils.equals(texto.toString().trim(), ""))
            for(int i=0;i<model().size();i++){
                if (model().get(i).getTitle().toLowerCase().contains(texto.toString().toLowerCase()
                )) {
                    result.add(model().get(i));
                }
            }
        else{
            result = model();
        }
        return result;
    }

    public void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }


    public void selectCountry(int pos ) {
        mSelectCountry.onNext(pos);
    }

    public Observable<List<TitleImage>> emitFilterCountrys() {
        return mFilterCountrys;
    }

    public Observable<TitleImage> emitSelectedCountry() {
        return mSelectCountry.withLatestFrom(mFilterCountrys, (p,l)-> l.get(p));
    }


}