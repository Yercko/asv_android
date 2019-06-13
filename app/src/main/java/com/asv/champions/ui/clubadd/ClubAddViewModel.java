package com.asv.champions.ui.clubadd;

import android.content.Context;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Pair;
import android.widget.ImageView;

import com.asv.champions.BR;
import com.asv.champions.R;
import com.asv.champions.app.Application;
import com.asv.champions.model.BaseResponse;
import com.asv.champions.model.ClubItem;
import com.asv.champions.model.CountrySorted;
import com.asv.champions.network.AsvService;
import com.asv.champions.ui.selector.TitleImage;
import com.asv.champions.utils.CountryUtils;
import com.bumptech.glide.Glide;
import com.prueba.core.base.view.ViewType;
import com.prueba.core.base.viewmodel.SingleViewModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class ClubAddViewModel extends SingleViewModel<ClubItem, AsvService, ViewType> {
    private BehaviorSubject<Boolean> mLoading = BehaviorSubject.createDefault(false);
    private BehaviorSubject<String> mError = BehaviorSubject.createDefault("");

    private Observable<Boolean> mValidName;

    private BehaviorSubject<CharSequence> mName = BehaviorSubject.createDefault("");
    private BehaviorSubject<CharSequence> mRival = BehaviorSubject.createDefault("");
    private BehaviorSubject<String> mCountry = BehaviorSubject.createDefault("");
    private Observable<List<CountrySorted>> countries;
    private PublishSubject<List<TitleImage>> mShowCountry =  PublishSubject.create();
    private PublishSubject<List<String>> mChampionsObservable =  PublishSubject.create();

    private PublishSubject<Bitmap> image = PublishSubject.create();
    private String urlImage = "";

    private PublishSubject<Boolean> mFinish = PublishSubject.create();

    private PublishSubject<Object> mSubmit = PublishSubject.create();

    @Inject
    public ClubAddViewModel(Context context,ClubItem clubItem) {
        Application.getInstance().getViewModelComponent().inject(this);
        updateModel(new ClubItem());

        countries = loadCountries();

        if (clubItem !=null && clubItem.getName()!=null){
            setUrlImage(clubItem.getPhoto());

        }

        mCountry.onNext(context.getString(R.string.country));

        compositeDisposable.add(
                mName.map(CharSequence::toString).subscribe(model()::setName)
        );

        compositeDisposable.add(
                mRival.map(CharSequence::toString).subscribe(model()::setRival)
        );

        mValidName = mName.map( this::isValidName);

        if (clubItem!= null && clubItem.getName() !=null){
            compositeDisposable.add(
                    mSubmit
                            .doOnNext(p -> {
                                mLoading.onNext(true);
                            })
                            .flatMap(s -> updateRequest(model()))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::checkValidationdResponse, s -> {
                                mLoading.onNext(false);
                            })
            );
        } else {
            compositeDisposable.add(
                    mSubmit
                            .doOnNext(p -> {
                                mLoading.onNext(true);
                            })
                            .flatMap(s -> createRequest(model()))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::checkValidationdResponse, s -> {
                                mLoading.onNext(false);
                            })
            );
        }
        compositeDisposable.add(
                image.map(this::bitmapOrientationBase64).subscribe(s-> {
                    model().setPhoto(s);
                    setUrlImage(s);
                })
        );

    }

    public void bindName(Observable<CharSequence> text){
        text.subscribe(mName);
    }
    public void bindRival(Observable<CharSequence> text){
        text.subscribe(mRival);
    }

    public void parseDate(List<Date> list) {
        List<String> result = new ArrayList<>();
        for (Date item:list){
            result.add(DateFormat.getDateInstance(DateFormat.MEDIUM).format(item));
        }
        mChampionsObservable.onNext(result);
    }

    public void setChampion(int day, int month, int year){
        Date date = new GregorianCalendar(year, month, day).getTime();
        model().getChampions().add(date);
        parseDate(model().getChampions());
    }

    private Observable<Notification<BaseResponse>> createRequest(ClubItem request) {
        return service()
                .createClub(
                        request
                )
                .materialize()
                .subscribeOn(Schedulers.io());
    }

    private Observable<Notification<BaseResponse>> updateRequest(ClubItem request) {
        return service()
                .updateClub(
                        request
                )
                .materialize()
                .subscribeOn(Schedulers.io());
    }
    private void checkValidationdResponse(Notification<BaseResponse> notification) {
        mLoading.onNext(false);
        if (notification.isOnNext()) {
            BaseResponse response = notification.getValue();
            if (response.isHasError()) {
                showErrorMessage(context.getString(R.string.generic_error));
            } else {
                mFinish.onNext(true);
            }
        } else if(notification.isOnError()) {
            showErrorMessage(context.getString(R.string.generic_error));
        }
    }

    public Observable<List<CountrySorted>> loadCountries(){
        return Observable.just("").map(s -> {
            return CountryUtils.loadCountriesFromJSON(context);
        }).subscribeOn(Schedulers.io());
    }

    public void showErrorMessage(String message){
        mError.onNext(message);
    }


    public void bindShowCountries(Observable<Object> clicks) {
        clicks
                .withLatestFrom(countries, (o,c) -> c)
                .filter(c -> !c.isEmpty())
                .map(s -> {
                    List<TitleImage> items = new ArrayList<>();
                    for (CountrySorted item:s){
                        items.add(new TitleImage(item.getName_code(),item.getName(),0,null));
                    }
                    return items;
                })
                .subscribe(mShowCountry);
    }

    public Observable<List<TitleImage>> emitShowCountries() {
        return mShowCountry;
    }

    public void nextPhoto(Bitmap content){
        image.onNext(content);
    }

    public String bitmapOrientationBase64(Bitmap path){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        path.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        return  encoded;
    }

    @BindingAdapter("srcCompat")
    public static void setSrcCompat(ImageView imageView, String url){
        if (url!=null && !url.isEmpty()) {
            byte[] decodedBytes = Base64.decode(url, Base64.DEFAULT);
            Glide.with(imageView.getContext()).load(decodedBytes).crossFade().fitCenter()
                    .into(imageView);
        }
    }

    @Bindable
    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
        notifyPropertyChanged(BR.urlImage);
    }

    public void bindSubmit(Observable<Object> btSubmit ){
        btSubmit.subscribe(mSubmit);
    }

    public boolean isValidName(CharSequence text){
        return text.length()> 4 && text.length()<24 ;
    }

    public void nextCountry(String item){
        mCountry.onNext(item);
    }

    public Observable<String> emitCountry(){
        return mCountry;
    }

    public Observable<String> emitError(){
        return mError;
    }

    public Observable<Boolean> emitFinish(){
        return mFinish;
    }

    public Observable<List<String>> emitChampions(){
        return mChampionsObservable;
    }

    public Observable<Boolean> emitValidName(){
        return mValidName;
    }
}
