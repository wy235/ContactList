package com.example.user.contactlist.view;

/**
 * Created by 王宇 on 2016/2/18.
 */

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Selection;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.contactlist.R;
import com.example.user.contactlist.activity.AppAplication;

public class SendInputView extends FrameLayout {

    private TextView mHintTextView = null;
    private EditText mEditText = null;
    private ImageView mAddView = null;
    private ImageView mBook = null;
    private OnAddClickListener mOnAddClickListener = null;
    private OpenBookClickListener mOpenBookClickListener = null;

    public SendInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        inflate(context, R.layout.input, this);
        mHintTextView = (TextView) findViewById(R.id.input_hint_tv);
        mEditText = (EditText) findViewById(R.id.input_et);
        AppAplication.getApplication().showSoftInput(mEditText, context);
        mAddView = (ImageView) findViewById(R.id.input_add_iv);
        mBook = (ImageView) findViewById(R.id.input_book);
        mAddView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnAddClickListener != null) {
                    mOnAddClickListener.onAddClick(SendInputView.this);
                }
            }
        });
        mBook.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOpenBookClickListener != null){
                    mOpenBookClickListener.openBookClick();
                }
            }
        });
    }

    public View getImageClick(){
        if(mAddView != null){
            return mAddView;
        }
        return null;
    }

    public void setNoEnabled(){
        if(mEditText != null){
            mEditText.setEnabled(false);
        }
    }


    public void setHintTextViewWidth(int width) {
        if (mHintTextView != null) {
            ViewGroup.LayoutParams lp = mHintTextView.getLayoutParams();
            lp.width = width;
            mHintTextView.setLayoutParams(lp);
        }
    }

    public void setHintText(String text) {
        if (mHintTextView != null) {
            if (text == null) {
                mHintTextView.setVisibility(View.GONE);
            } else {
                mHintTextView.setText(text);
            }
        }
    }

    public void setBookVisible(){
        mBook.setVisibility(VISIBLE);
    }

    public void setBookGone(){
        mBook.setVisibility(GONE);
    }

    public int getBookVisibi(){
        return mBook.getVisibility();
    }

    public void setEditHintText(String text) {
        if (mEditText != null) {
            mEditText.setHint(text);
        }
    }

    public void setInputType(int type) {
        if (mEditText != null) {
            mEditText.setInputType(type);
        }
    }

    public void setPassword() {
        if (mEditText != null) {
            mEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            Editable etable = mEditText.getText();
            Selection.setSelection(etable, etable.length());
        }
    }

    public void setLines(boolean lies) {
        if (mEditText != null) {
            //mEditText.setLines(lies);
            mEditText.setSingleLine(lies);
        }
    }

    public void showHintTextView(boolean show) {
        if (mEditText != null) {
            mHintTextView.setVisibility(show ? VISIBLE : GONE);
        }
    }

    public View getEt(){
        return mEditText;
    }

    public String getEditText() {
        if (mEditText != null) {
            return mEditText.getText().toString();
        }
        return null;
    }

    public void setEditText(String text) {
        if (mEditText != null) {
            mEditText.setText(text);

        }
    }


    public void setEditTextEnabled(boolean enabled) {
        if (mEditText != null) {
            mEditText.setEnabled(enabled);
        }
    }

    public void setMaxLength(int len) {
        if (mEditText != null) {
            mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(len)});
        }
    }

    public void setOnAddClickListener(OnAddClickListener listener) {
        mOnAddClickListener = listener;
    }

    public void setOpenBookClickListener(OpenBookClickListener openListener){
        mOpenBookClickListener = openListener;
    }

    public static interface OnAddClickListener {
        public void onAddClick(SendInputView view);
    }

    public static interface OpenBookClickListener{
        public void openBookClick();
    }
}
