package com.prueba.core.base.viewmodel;

import android.support.annotation.NonNull;

import com.prueba.core.base.model.ModelType;
import com.prueba.core.base.view.ViewType;

/**
 * Created by prueba on 8/2/17.
 */
public interface SingleViewModelType<M extends ModelType, S, V extends ViewType> extends BaseViewModelType<V> {

    /**
     * Actualiza el modelo y notifica el cambio a la vista
     * @param model Modelo a actualizar
     */
    void updateModel(@NonNull M model);

    /**
     * Establece el servicio a usar por el SingleViewModel
     * @param service Servicio de recuperacion de datos
     */
    public void setService( S service );

}
