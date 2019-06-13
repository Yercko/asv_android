package com.prueba.core.base.viewmodel;

import com.prueba.core.base.model.ModelType;
import com.prueba.core.base.view.ViewType;

/**
 * Created by prueba on 8/3/18.
 */
public abstract class BasicViewModel<M extends ModelType, V extends ViewType> extends BaseViewModel<V>
        implements  BasicViewModelType<M, V> {

    // Modelo
    private M mModel;

    public BasicViewModel() {
        super();
    }


    @Override
    public void updateModel( M model ) {
        mModel = model;
        notifyChange();
    }

    public final M model() {
        return mModel;
    }

}
