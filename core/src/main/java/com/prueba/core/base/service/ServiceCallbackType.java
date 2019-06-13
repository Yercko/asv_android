package com.prueba.core.base.service;

/**
 * Created by prueba on 31/1/17.
 */

public interface ServiceCallbackType<T> {

    void success(T result);
    void failure(String error);

}
