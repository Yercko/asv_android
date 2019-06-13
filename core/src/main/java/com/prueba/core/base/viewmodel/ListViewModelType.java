package com.prueba.core.base.viewmodel;

import android.support.annotation.NonNull;

import java.util.List;

import com.prueba.core.base.model.ModelType;
import com.prueba.core.base.view.ViewType;

/**
 * Created by prueba on 8/2/17.
 */
public interface ListViewModelType<M extends ModelType, S, V extends ViewType>
        extends BaseViewModelType<V> {

    /**
     * Actualiza el modelo y notifica el cambio a la vista
     * @param model Modelo a actualizar
     */
    void setModel(@NonNull List<M> model);

    /**
     * Añade items al modelo
     * @param
     */
    void addToModel(@NonNull List<M> items);

    /**
     * Establece el servicio a usar por el ListViewModel
     * @param service Servicio de recuperacion de datos
     */
    void setService(@NonNull S service );
}
