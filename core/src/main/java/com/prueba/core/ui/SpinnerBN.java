package com.prueba.core.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asv.core.R;


@BindingMethods({@BindingMethod(type = SpinnerBN.class, attribute = "onClickLeft", method = "setOnClickLeft"),@BindingMethod(type = SpinnerBN.class, attribute = "onClickRight", method = "setOnClickRight")})
public class SpinnerBN extends LinearLayout {

//    private static final int[] DISABLE_STATE_SET = {R.attr.state_disabled};

    private LinearLayout llContent;
    private ImageView ivIconLeft;
    private ImageView ivIconRight;
    private TextView titulo;
    private String mTextWrited = "";
    private boolean disabled = false;



    public SpinnerBN(Context context) {
        super(context);
        initView(context);
    }

    public SpinnerBN(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initValues(context,attrs);

    }

    public SpinnerBN(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initValues(context,attrs);
    }

    private void initView(Context context){
        //llContent = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.sp_spinnerbn,
         //       this);
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        llContent = (LinearLayout) layoutInflater.inflate(R.layout.sp_spinnerbn, this, true);

        ivIconLeft = (ImageView) llContent.findViewById(R.id.iv_left);
        ivIconRight = (ImageView) llContent.findViewById(R.id.iv_right);
        titulo = (TextView) llContent.findViewById(R.id.tv_content_spinnerbn);
        setBackgroundResource(R.drawable.bg_round_border);
    }

    public void initValues(Context context,AttributeSet attrs){
        Drawable iconLeft;
        Drawable iconRight;
        boolean enabled;
        CharSequence text;


        if(attrs == null){
            return;
        }

        //get attr
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.styleSpinnerBN,
                0, 0);
        try {
            //config attr values
            iconLeft = a.getDrawable(R.styleable.styleSpinnerBN_icLeft);
            if (iconLeft == null){
                ivIconLeft.setVisibility(GONE);
            }
            iconRight = a.getDrawable(R.styleable.styleSpinnerBN_icRight);
            if (iconRight == null){
                ivIconRight.setVisibility(GONE);
            }
            enabled = a.getBoolean(R.styleable.styleSpinnerBN_android_enabled, isEnabled());
            text = a.getText(R.styleable.styleSpinnerBN_android_text);
            disabled = a.getBoolean(R.styleable.styleSpinnerBN_disabled, disabled);

        } finally {
            if(a != null) {
                a.recycle();
            }
        }

        if(iconLeft != null ) {
            ivIconLeft.setImageDrawable(iconLeft);
        }
        if(iconRight != null ) {
            ivIconRight.setImageDrawable(iconRight);
        }
        setEnabled(enabled);
        setText(text);
    }

    public void setText(CharSequence text) {
        titulo.setText(text);
    }

    public CharSequence getText() {
        return titulo.getText();
    }


    public void setIconLeft(@DrawableRes int resourceId) {
        ivIconLeft.setImageResource(resourceId);
        ivIconLeft.setVisibility(VISIBLE);
    }

    public void setIconRight(@DrawableRes int resourceId) {
        ivIconRight.setImageResource(resourceId);
        ivIconRight.setVisibility(VISIBLE);
    }

    public ImageView getIvIconLeft() {
        return ivIconLeft;
    }

    public ImageView getIvIconRight() {
        return ivIconRight;
    }

    public TextView getTitulo() {
        return titulo;
    }

    //    @Override
//    protected int[] onCreateDrawableState(int extraSpace) {
//        // If the message is unread then we merge our custom message unread state into
//        // the existing drawable state before returning it.
//        if (disabled) {
//            // We are going to add 1 extra state.
//            final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
//
//            mergeDrawableStates(drawableState, DISABLE_STATE_SET);
//
//            return drawableState;
//        } else {
//            return super.onCreateDrawableState(extraSpace);
//        }
//    }
//
//    public boolean isDisabled() {
//        return this.disabled;
//    }
//
//    public void setDisabled(boolean disabled) {
//        this.disabled = disabled;
//        refreshDrawableState();
//    }
}