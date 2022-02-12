package com.Deepak_pandey.labexp_9.view;

import android.content.Context;
import android.graphics.Paint;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatCheckedTextView;


import com.google.developer.taskmaker.R;


public class TaskTitleView extends AppCompatCheckedTextView {
    public static final int NORMAL = 0;
    public static final int DONE = 1;
    public static final int OVERDUE = 2;
    private int mState;

    public TaskTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TaskTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TaskTitleView(Context context) {
        super(context);
    }


    public int getState() {
        return mState;
    }

    public void setState(int state) {
        switch (state) {
            case DONE:
                setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            case NORMAL:
                setPaintFlags(0);
                setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                break;
            case OVERDUE:
                setPaintFlags(0);
                setTextColor(ContextCompat.getColor(getContext(), R.color.red));
                break;
            default:
                return;
        }

        mState = state;
    }
}
