package com.prueba.core.base.viewmodel;

import android.support.annotation.NonNull;

import com.prueba.core.base.model.ModelType;
import com.prueba.core.base.view.ViewType;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by prueba on 8/2/17.
 */

public abstract class ListViewModel<M extends ModelType, S, V extends ViewType>
        extends BaseViewModel<V>
        implements  ListViewModelType<M, S, V> {

    // Contendra una lista de modelos
    protected List<M> mModel;

    // Servicio de recuperacion de datos

    protected S mService;


    public ListViewModel() {
        super();
        mModel = new ArrayList<M>();
    }

    @Override
    public void setModel(@NonNull List<M> items) {
        mModel.clear();
        mModel.addAll(items);
    }

    @Override
    public void addToModel(@NonNull List<M> items) {
        mModel.addAll(items);
    }

    @Inject
    @Override
    public void setService( S service ) {
        mService = service;
    }

    public final List<M> model() {
        return mModel;
    }

    public final S service() {
        return mService;
    }
}
