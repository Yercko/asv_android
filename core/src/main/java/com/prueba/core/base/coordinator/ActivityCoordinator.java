package com.prueba.core.base.coordinator;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.asv.core.R;


/**
 * Created by prueba on 6/2/17.
 */

public class ActivityCoordinator implements CoordinatorType {

    protected final FragmentActivity activity;

    public ActivityCoordinator(FragmentActivity activity) {
        this.activity = activity;
    }


    @Override
    public void finishActivity() {
        activity.finish();
    }

    @Override
    public final void startActivity(@NonNull Intent intent) {
        activity.startActivity(intent);
    }

    @Override
    public final void startActivity(@NonNull String action) {
        activity.startActivity(new Intent(action));
    }

    @Override
    public final void startActivity(@NonNull String action, @NonNull Uri uri) {
        activity.startActivity(new Intent(action, uri));
    }

    @Override
    public final void startActivity(@NonNull Class<? extends Activity> activityClass) {
        Intent intent = new Intent(activity, activityClass);
        startActivityInternal(intent, null);
    }


    @Override
    public void startActivity(@NonNull Class<? extends Activity> activityClass, Bundle args) {
        Intent intent = new Intent(activity, activityClass);
        intent.putExtra(EXTRA_ARG, args);
        startActivityInternal(intent, null);
    }

    @Override
    public final void startActivity(@NonNull Class<? extends Activity> activityClass, @NonNull Parcelable arg) {
        Intent intent = new Intent(activity, activityClass);
        intent.putExtra(EXTRA_ARG, arg);
        startActivityInternal(intent, null);
    }

    @Override
    public final void startActivity(@NonNull Class<? extends Activity> activityClass, @NonNull String arg) {
        Intent intent = new Intent(activity, activityClass);
        intent.putExtra(EXTRA_ARG, arg);
        startActivityInternal(intent, null);
    }

    @Override
    public final void startActivity(@NonNull Class<? extends Activity> activityClass, int arg) {
        Intent intent = new Intent(activity, activityClass);
        intent.putExtra(EXTRA_ARG, arg);
        startActivityInternal(intent, null);
    }

    @Override
    public final void startActivityForResult(@NonNull Class<? extends Activity> activityClass, int requestCode) {
        Intent intent = new Intent(activity, activityClass);
        startActivityInternal(intent, requestCode);
    }


    @Override
    public final void startActivityForResult(@NonNull Class<? extends Activity> activityClass, @NonNull Parcelable arg, int requestCode) {
        Intent intent = new Intent(activity, activityClass);
        intent.putExtra(EXTRA_ARG, arg);
        startActivityInternal(intent, requestCode);
    }

    @Override
    public final void startActivityForResult(@NonNull Class<? extends Activity> activityClass, @NonNull String arg, int requestCode) {
        Intent intent = new Intent(activity, activityClass);
        intent.putExtra(EXTRA_ARG, arg);
        startActivityInternal(intent, requestCode);
    }

    @Override
    public final void startActivityForResult(@NonNull Class<? extends Activity> activityClass, int arg, int requestCode) {
        Intent intent = new Intent(activity, activityClass);
        intent.putExtra(EXTRA_ARG, arg);
        startActivityInternal(intent, requestCode);
    }

    private void startActivityInternal(Intent intent, Integer requestCode) {
        if(requestCode != null) {
            activity.startActivityForResult(intent, requestCode);
        } else {
            activity.startActivity(intent);
        }
    }

    @Override
    public final void replaceFragment(@IdRes int containerId, Fragment fragment, Bundle args) {
        replaceFragmentInternal(activity.getSupportFragmentManager(), containerId, fragment, null, args, false, null);
    }

    @Override
    public final void replaceFragment(@IdRes int containerId, @NonNull Fragment fragment, @NonNull String fragmentTag, Bundle args) {
        replaceFragmentInternal(activity.getSupportFragmentManager(), containerId, fragment, fragmentTag, args, false, null);
    }

    @Override
    public final void replaceFragmentAndAddToBackStack(@IdRes int containerId, Fragment fragment, Bundle args, String backstackTag) {
        replaceFragmentInternal(activity.getSupportFragmentManager(), containerId, fragment, null, args, true, backstackTag);
    }

    @Override
    public final void replaceFragmentAndAddToBackStack(@IdRes int containerId, @NonNull Fragment fragment, @NonNull String fragmentTag, Bundle args, String backstackTag) {
        replaceFragmentInternal(activity.getSupportFragmentManager(), containerId, fragment, fragmentTag, args, true, backstackTag);
    }

    protected final void replaceFragmentInternal(FragmentManager fm, @IdRes int containerId, Fragment fragment, String fragmentTag, Bundle args, boolean addToBackstack, String backstackTag) {
        if(args != null) { fragment.setArguments(args);}
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.from_right, R.anim.to_left, R.anim.from_left, R.anim.to_right);
        ft.replace(containerId, fragment, fragmentTag);
        if(addToBackstack) {
            ft.addToBackStack(backstackTag).commit();
            fm.executePendingTransactions();
        } else {
            ft.commit();
        }
    }

}
