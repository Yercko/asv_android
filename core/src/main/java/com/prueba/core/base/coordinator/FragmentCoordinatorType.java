package com.prueba.core.base.coordinator;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

/**
 * Created by prueba on 6/2/17.
 */

public interface FragmentCoordinatorType extends CoordinatorType {

    void replaceChildFragment(@IdRes int containerId, @NonNull Fragment fragment, Bundle args);
    void replaceChildFragment(@IdRes int containerId, @NonNull Fragment fragment, @NonNull String fragmentTag, Bundle args);
    void replaceChildFragmentAndAddToBackStack(@IdRes int containerId, @NonNull Fragment fragment, Bundle args, String backstackTag);
    void replaceChildFragmentAndAddToBackStack(@IdRes int containerId, @NonNull Fragment fragment, @NonNull String fragmentTag, Bundle args, String backstackTag);

}
