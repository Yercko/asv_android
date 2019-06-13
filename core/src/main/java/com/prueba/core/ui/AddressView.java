package com.prueba.core.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;

import com.asv.core.R;


/**
 * Created by yercko on 16/5/18.
 */

public class AddressView extends AppCompatTextView {
    private int mDigitColor = Color.RED;



    public AddressView(Context context) {
        super(context);
    }

    public AddressView(Context context, AttributeSet attrs) {
        super(new ContextThemeWrapper(context, R.style.AddressView), attrs);
        setAttributes(context, attrs);
    }

    public AddressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super( new ContextThemeWrapper(context, R.style.AddressView), attrs, defStyleAttr);
        setAttributes(context, attrs);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        SpannableString address = createAddressSpannable(text.toString());
        super.setText(address, BufferType.SPANNABLE);
    }



    private void setAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.styleAddressView, 0, 0);
        try {
            int digitColor = R.styleable.styleAddressView_digitColor;
            if(ta.hasValue(digitColor)) {
                int referenceColor = ta.getResourceId(digitColor, 0);
                if(referenceColor > 0) {
                    mDigitColor = context.getResources().getColor(referenceColor);
                }
            }
            int text = R.styleable.styleAddressView_android_text;
            if (ta.hasValue(text)) {
                setText(ta.getText(text));
            }

        } finally {
            ta.recycle();
        }


    }

    private SpannableString createAddressSpannable(String text) {
        String addressStr = separateByLength(text, 4);
        SpannableString spannableString = new SpannableString(addressStr);
        int startIndex = -1;
        int endIndex = -1;
        for (int i=0; i<spannableString.length(); i++) {
            if (Character.isDigit(spannableString.charAt(i))) {
                if(startIndex<0) {
                    startIndex = i;
                }
            } else if(startIndex>=0) {
                spannableString.setSpan(new ForegroundColorSpan(mDigitColor),startIndex, i, 0);
                startIndex = -1;
            }
        }
        if(startIndex>=0) {
            spannableString.setSpan(new ForegroundColorSpan(Color.BLUE),startIndex, spannableString.length()-1, 0);
        }

        return spannableString;
    }

    /**
     * Split text into n number of characters.
     *
     * @param text the text to be split.
     * @param size the split size.
     * @return an array of the split text.
     */
    private String separateByLength(String text, int size) {
        String result = "";
        int length = text.length();
        for (int i = 0; i < length; i += size) {
            result += (text.substring(i, Math.min(length, i + size)))+ " ";
        }
        return result;
    }
}
