package com.prueba.core.base.view;

/**
 * Created by prueba on 12/2/17.
 */

public interface BaseView extends ViewType {

    void showError(final String error);

    void dataDidLoad();
}
