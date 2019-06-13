package com.asv.champions.ui;

import android.content.Context;
import android.support.annotation.NonNull;

import com.prueba.core.base.view.ViewType;
import com.prueba.core.base.viewmodel.BaseViewModel;

import javax.inject.Inject;

public class EmptyViewModel extends BaseViewModel<ViewType> {

    @Inject
    public EmptyViewModel(@NonNull Context context) {
        super(context);
    }


}
