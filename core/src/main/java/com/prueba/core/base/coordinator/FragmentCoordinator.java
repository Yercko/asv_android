package com.prueba.core.base.coordinator;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

/**
 * Created by prueba on 6/2/17.
 */

public class FragmentCoordinator extends ActivityCoordinator implements FragmentCoordinatorType {
    private final Fragment fragment;

    public FragmentCoordinator(Fragment fragment) {
        super(fragment.getActivity());
        this.fragment = fragment;
    }

    @Override
    public final void replaceChildFragment(@IdRes int containerId, @NonNull Fragment fragment, Bundle args) {
        replaceFragmentInternal(this.fragment.getChildFragmentManager(), containerId, fragment, null, args, false, null);
    }

    @Override
    public final void replaceChildFragment(@IdRes int containerId, @NonNull Fragment fragment, @NonNull String fragmentTag, Bundle args) {
        replaceFragmentInternal(this.fragment.getChildFragmentManager(), containerId, fragment, fragmentTag, args, false, null);
    }

    @Override
    public final void replaceChildFragmentAndAddToBackStack(@IdRes int containerId, @NonNull Fragment fragment, Bundle args, String backstackTag) {
        replaceFragmentInternal(this.fragment.getChildFragmentManager(), containerId, fragment, null, args, true, backstackTag);
    }

    @Override
    public final void replaceChildFragmentAndAddToBackStack(@IdRes int containerId, @NonNull Fragment fragment, @NonNull String fragmentTag, Bundle args, String backstackTag) {
        replaceFragmentInternal(this.fragment.getChildFragmentManager(), containerId, fragment, fragmentTag, args, true, backstackTag);
    }
}
