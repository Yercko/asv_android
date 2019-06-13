package com.prueba.core.base.viewmodel;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.prueba.core.base.view.ViewType;

import io.reactivex.Scheduler;

/**
 * Created by prueba on 2/2/17.
 */

public interface BaseViewModelType<V extends ViewType> {

    void attachView(V view, Bundle savedInstanceState);

    void detachView();

    void saveInstanceState(@NonNull Bundle outState);

    Scheduler subscribeScheduler();

}
