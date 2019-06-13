package com.prueba.core.base.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.asv.core.R;


public class UIUtils {


    public static int dpToPx(Context context, float dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int pxToDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }


    public static void recycleDrawable(Drawable d, boolean realRecycle) {
        if (d!=null) {
            d.setCallback(null);
            if (realRecycle && d instanceof BitmapDrawable) {
                Bitmap bmp = ((BitmapDrawable) d).getBitmap();
                recycleBitmap(bmp);
            }
        }
    }

    public static void recycleBitmap(Bitmap bmp) {
        if(bmp!=null && !bmp.isRecycled()) {
            bmp.recycle();
        }
    }

    public static void unbindDrawables(View v) {
        unbindDrawables(v, false);
    }

    @SuppressWarnings("deprecation")
    public static void unbindDrawables(View v, boolean recycleBitmaps) {
        if (v==null) return;
        if (v.getBackground()!=null) {
            Drawable d = v.getBackground();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                v.setBackground(null);
            else
                v.setBackgroundDrawable(null);
            recycleDrawable(d, recycleBitmaps);
        }
        if (v instanceof ImageView) {
            Drawable d = ((ImageView)v).getDrawable();
            ((ImageView)v).setImageDrawable(null);
            recycleDrawable(d, recycleBitmaps);
        }
        if (v instanceof ViewGroup) {
            for (int i=0; i<((ViewGroup)v).getChildCount(); i++) {
                unbindDrawables(((ViewGroup)v).getChildAt(i), recycleBitmaps);
            }
            if (!(v instanceof AdapterView<?>))
                ((ViewGroup)v).removeAllViews();
        }
    }

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(true);
        return progressDialog;
    }

}
