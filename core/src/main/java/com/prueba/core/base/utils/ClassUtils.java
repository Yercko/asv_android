package com.prueba.core.base.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.Nullable;

/**
 * Created by prueba on 6/2/17.
 */

public class ClassUtils {

    // Tries to cast an Activity Context to another type
    @SuppressWarnings("unchecked")
    @Nullable
    public static <T> T castActivityFromContext(Context context, Class<T> castClass) {
        if(castClass.isInstance(context)) {
            return (T) context;
        }

        while(context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();

            if(castClass.isInstance(context)) {
                return (T) context;
            }
        }

        return null;
    }
}
