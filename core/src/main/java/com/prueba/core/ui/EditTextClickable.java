package com.prueba.core.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.asv.core.R;


@BindingMethods({
        @BindingMethod(type = EditTextClickable.class, attribute = "onClickLeft", method = "setOnClickLeft"),
        @BindingMethod(type = EditTextClickable.class, attribute = "onClickRight", method = "setOnClickRight"),
        @BindingMethod(type = EditTextClickable.class, attribute = "iconRight", method = "setIconRight"),
        @BindingMethod(type = EditTextClickable.class, attribute = "iconLeft", method = "setIconLeft"),
        @BindingMethod(type = EditTextClickable.class, attribute = "iconLeftrounded", method = "setIconRounded")
})
public class EditTextClickable extends LinearLayout{
    private LinearLayout llContent;
    private ImageView ivIconLeft;
    private ImageView ivIconRight;
    private EditText editText;
    private String mTextWrited = "";
    private EditText editTextContact;


    public EditTextClickable(Context context) {
        super(context);
        initView(context);
    }

    public EditTextClickable(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initValues(context,attrs);

    }

    public EditTextClickable(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initValues(context,attrs);
    }

    private void initView(Context context){
        llContent = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.et_with_drawable_clickable, this);
        ivIconLeft = (ImageView) llContent.findViewById(R.id.iv_left);
        ivIconRight = (ImageView) llContent.findViewById(R.id.iv_right);
        editText = (EditText)llContent.findViewById(R.id.et_content);
        editTextContact = (EditText)llContent.findViewById(R.id.et_content_contact);
    }

    public void initValues(Context context,AttributeSet attrs){
        Drawable iconLeft;
        Drawable iconLeftRounded;
        Drawable iconRight;
        int inputType;
        boolean enabled;
        int maxLength;
        int textAlignment;
        CharSequence hint;

        if(attrs == null){
            return;
        }

        //get attr
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.styleEditTextClickable,
                0, 0);
        try {
            //config attr values
            iconLeft = a.getDrawable(R.styleable.styleEditTextClickable_iconLeft);
            iconLeftRounded = a.getDrawable(R.styleable.styleEditTextClickable_iconLeftrounded);
            if (iconLeft == null && iconLeftRounded == null){
                ivIconLeft.setVisibility(GONE);
            }
            iconRight = a.getDrawable(R.styleable.styleEditTextClickable_iconRight);
            if (iconRight == null){
                ivIconRight.setVisibility(GONE);
            }

            inputType = a.getInt(R.styleable.styleEditTextClickable_android_inputType, InputType
                    .TYPE_CLASS_TEXT);
            enabled = a.getBoolean(R.styleable.styleEditTextClickable_android_enabled, true);
            maxLength = a.getInt(R.styleable.styleEditTextClickable_android_maxLength, -1);
            textAlignment = a.getInt(R.styleable
                    .styleEditTextClickable_android_textAlignment, TEXT_ALIGNMENT_INHERIT);
            hint = a.getText(R.styleable.styleEditTextClickable_android_hint);

        } finally {
            if(a != null) {
                a.recycle();
            }
        }

        editText.setInputType(inputType);
        editText.setEnabled(enabled);
        editText.setTextAlignment(textAlignment);
        editText.setHint(hint);

        if( maxLength>0 ) {
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        }
        if(iconLeft != null ) {
            ivIconLeft.setImageDrawable(iconLeft);
        }
        if(iconLeftRounded != null ) {
            setIconRounded(iconLeftRounded);
        }
        if(iconRight != null ) {
            ivIconRight.setImageDrawable(iconRight);
        }

    }

    public void setIconRounded(Drawable drawable){
        if (drawable != null) {
            Bitmap bitmap;
            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                if (bitmapDrawable.getBitmap() != null) {
                    bitmap = bitmapDrawable.getBitmap();

                    RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                    dr.setCircular(true);
                    ivIconLeft.setImageDrawable(dr);
                    ivIconLeft.setVisibility(VISIBLE);
                }
            }
        }
        else {
            ivIconLeft.setVisibility(GONE);
        }

    }
    public void setIconRounded(Bitmap bitmap){
        if (bitmap != null) {
            RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
            dr.setCircular(true);
            ivIconLeft.setImageDrawable(dr);
            ivIconLeft.setVisibility(VISIBLE);
        }
        else {
            ivIconLeft.setVisibility(GONE);
        }

    }

    public void setIconLeft(@DrawableRes int resId) {
        if(resId != 0) {
            ivIconLeft.setVisibility(VISIBLE);
        } else {
            ivIconLeft.setVisibility(INVISIBLE);
        }
        ivIconLeft.setImageResource(resId);
    }


    public void setIconLeft(Drawable drawable) {
        if(drawable != null) {
            ivIconLeft.setVisibility(VISIBLE);
        } else {
            ivIconLeft.setVisibility(INVISIBLE);
        }
        ivIconLeft.setImageDrawable(drawable);
    }


    public void setIconRight(@DrawableRes int resId) {
        if(resId != 0) {
            ivIconRight.setVisibility(VISIBLE);
        } else {
            ivIconRight.setVisibility(INVISIBLE);
        }
        ivIconRight.setImageResource(resId);
    }

    public void setIconRight(Drawable drawable) {
        if(drawable != null) {
            ivIconRight.setVisibility(VISIBLE);
            ivIconRight.setImageDrawable(drawable);
        } else {
            ivIconRight.setVisibility(INVISIBLE);
        }

    }

    public void setOnClickLeft(OnClickListener onClickLeft) {
        if (ivIconLeft != null){
            ivIconLeft.setOnClickListener(onClickLeft);
        }

    }

    public ImageView getIvIconLeft() {
        return ivIconLeft;
    }

    public ImageView getIvIconRight() {
        return ivIconRight;
    }

    public EditText getEditText() {
        return editText;
    }

    public EditText getEditTextContact(){
        return editTextContact;
    }

    public void setOnClickRight(OnClickListener onClickRight) {
        if (ivIconRight != null){
            ivIconRight.setOnClickListener(onClickRight);
        }
    }

    public void setEnabled(boolean enabled) {
        editText.setEnabled(enabled);
    }

}