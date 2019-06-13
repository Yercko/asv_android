package com.prueba.core.base.component;

import android.support.v4.app.FragmentManager;

import com.prueba.core.base.coordinator.FragmentCoordinatorType;

/**
 * Created by prueba on 6/2/17.
 */

public interface FragmentComponentType extends ActivityComponentType {
    FragmentManager childFragmentManager();

    FragmentCoordinatorType fragmentNavigator();
}
