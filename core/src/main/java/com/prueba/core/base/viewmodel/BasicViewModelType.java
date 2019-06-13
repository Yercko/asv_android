package com.prueba.core.base.viewmodel;

import android.support.annotation.NonNull;

import com.prueba.core.base.model.ModelType;
import com.prueba.core.base.view.ViewType;

/**
 * Created by prueba on 8/3/18.
 */

public interface BasicViewModelType <M extends ModelType, V extends ViewType> extends BaseViewModelType<V> {

    /**
     * Actualiza el modelo y notifica el cambio a la vista
     * @param model Modelo a actualizar
     */
    void updateModel(@NonNull M model);

}
