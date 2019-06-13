package com.prueba.core.base.viewmodel;

import com.prueba.core.base.model.ModelType;
import com.prueba.core.base.view.ViewType;

import javax.inject.Inject;

/**
 * Created by prueba on 8/2/17.
 */

public abstract class SingleViewModel<M extends ModelType, S, V extends ViewType> extends BaseViewModel<V>
        implements  SingleViewModelType<M, S, V> {

    // Modelo
    private M mModel;

    // Servicio de recuperacion de datos
    private S mService;

    public SingleViewModel() {
        super();
    }

    @Override
    public void updateModel( M model ) {
        mModel = model;
        notifyChange();
    }

    @Inject
    @Override
    public void setService( S service ) {
        mService = service;
    }

    public final M model() {
        return mModel;
    }

    public final S service() {
        return mService;
    }
}
