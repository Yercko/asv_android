package com.prueba.core.base.coordinator;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

/**
 * Created by prueba on 6/2/17.
 */

public interface CoordinatorType {
    String EXTRA_ARG = "_args";

    void finishActivity();
    void startActivity(@NonNull Intent intent);
    void startActivity(@NonNull String action);
    void startActivity(@NonNull String action, @NonNull Uri uri);
    void startActivity(@NonNull Class<? extends Activity> activityClass);
    void startActivity(@NonNull Class<? extends Activity> activityClass, Bundle args);
    void startActivity(@NonNull Class<? extends Activity> activityClass, Parcelable args);
    void startActivity(@NonNull Class<? extends Activity> activityClass, @NonNull String arg);
    void startActivity(@NonNull Class<? extends Activity> activityClass, int arg);

    void startActivityForResult(@NonNull Class<? extends Activity> activityClass, int requestCode);
    void startActivityForResult(@NonNull Class<? extends Activity> activityClass, @NonNull Parcelable arg, int requestCode);
    void startActivityForResult(@NonNull Class<? extends Activity> activityClass, @NonNull String arg, int requestCode);
    void startActivityForResult(@NonNull Class<? extends Activity> activityClass, int arg, int requestCode);

    void replaceFragment(@IdRes int containerId, @NonNull Fragment fragment, Bundle args);
    void replaceFragment(@IdRes int containerId, @NonNull Fragment fragment, @NonNull String fragmentTag, Bundle args);
    void replaceFragmentAndAddToBackStack(@IdRes int containerId, @NonNull Fragment fragment, Bundle args, String backstackTag);
    void replaceFragmentAndAddToBackStack(@IdRes int containerId, @NonNull Fragment fragment, @NonNull String fragmentTag, Bundle args, String backstackTag);

}
