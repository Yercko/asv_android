package com.prueba.core.base.component;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.prueba.core.base.coordinator.CoordinatorType;
import io.reactivex.Scheduler;

/**
 * Created by prueba on 6/2/17.
 */

public interface ActivityComponentType extends AppComponentType {
    Context activityContext();
    FragmentManager fragmentManager();
    CoordinatorType coordinator();
    Scheduler subscribeScheduler();
}
