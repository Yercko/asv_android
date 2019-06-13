package com.prueba.core.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asv.core.R;


public class LoadingButton extends RelativeLayout {


    View rootView;
    private CharSequence mText;
    private Boolean mEnabled;
    private Boolean mLoading;
    private int mTextColor;

    private TextView mTextviewTitle;
    private ProgressBar mProgress;

    public LoadingButton(Context context) {
        super(context);
        init(context);
    }


    public LoadingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttributes(context, attrs);
        init(context);
    }

    public LoadingButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttributes(context, attrs);
        init(context);
    }

    @Override
    protected void dispatchSetPressed(boolean pressed) {
        super.dispatchSetPressed(pressed);
        invalidate();
    }

    public void setLoading(Boolean loading) {
        if(loading) {
            setClickable(false);
            mTextviewTitle.setVisibility(INVISIBLE);
            mProgress.setVisibility(VISIBLE);
        } else {
            setClickable(true);
            mTextviewTitle.setVisibility(VISIBLE);
            mProgress.setVisibility(INVISIBLE);
        }
        invalidate();
    }

    public void setText(CharSequence text) {
        mTextviewTitle.setText(text);
    }

    private void setAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LoadingButton, 0, 0);
        try {
            mText = ta.getText(R.styleable.LoadingButton_android_text);
            mEnabled = ta.getBoolean(R.styleable.LoadingButton_android_enabled, true);
            mLoading = ta.getBoolean(R.styleable.LoadingButton_loading, false);
            mTextColor = ta.getColor(R.styleable.LoadingButton_android_textColor, context.getResources().getColor(android.R.color.darker_gray));
        } finally {
            ta.recycle();
        }
    }



    private void init(Context context) {
        rootView = inflate(context, R.layout.loading_button, this);
        mTextviewTitle = (TextView) rootView.findViewById(R.id.titleLoadingButton);
        mProgress = (ProgressBar) rootView.findViewById(R.id.progress);
        setClickable(true);
        mTextviewTitle.setTextColor(mTextColor);
        setEnabled(mEnabled);
        setText(mText);
        setLoading(mLoading);

    }



}
