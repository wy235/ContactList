package com.example.user.contactlist.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.user.contactlist.bean.SendWay;
import com.example.user.contactlist.utils.TabCheckUtils;

import java.util.ArrayList;

/**
 * Created by 王宇 on 15/6/21.
 */
public class ChooseWayEditView extends LinearLayout {
    private int mCount = 0;
    private OnChooseOpenBookListener mOnChooseOpenBookListener = null;
    private OnChangeListener mListener = null;
    private String mHint = null;
    private int mHintWidth = 0;
    private int maxLen = 0;
    private String hinText = "";
    private boolean lines = false;
    private Context context;
    private Toast toast;
    private int type = 0;
    private boolean mBook = false;
    private int way;


    public ChooseWayEditView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ChooseWayEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
    }

    public void setHintInfo(String hint, int hintWidth) {
        mHint = hint;
        mHintWidth = hintWidth;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setAddressBook(boolean isBook) {
        this.mBook = isBook;
    }

    public void setMaxLence(int maxLen) {
        this.maxLen = maxLen;
    }

    public void setMyHint(String text) {
        hinText = text;
    }

    public void setMyLines(boolean lines) {
        this.lines = lines;
    }

    public void setWayType(int way){
        this.way = way;
    }

    public void addInputView(String text) {
        int count = getChildCount();
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                View childView = getChildAt(i);
                if (childView instanceof SendInputView) {
                    SendInputView iv = (SendInputView) childView;
                }
            }
        }
        if (((SendInputView) getChildAt(count - 1)) != null) {
            if (!text.equals("") && ((SendInputView) getChildAt(count - 1)).getEditText().equals("")) {
                ((SendInputView) getChildAt(count - 1)).setEditText(text);
                return;
            }
        }
        if (count > 0 && ((SendInputView) getChildAt(count - 1)).getEditText().toString().equals("")) {
            Toast.makeText(context, "请填写上一个", Toast.LENGTH_SHORT).show();
            return;
        }
        SendInputView inputView = null;
        if (count <= 8) {
            inputView = new SendInputView(getContext(), null);
            if (mHint != null) {
                inputView.setHintText(mHint);
            }
            if (maxLen != 0) {
                inputView.setMaxLength(maxLen);
            }
            if (hinText != null && !hinText.equals("")) {
                inputView.setEditHintText(hinText);
            }
            if (mBook && count == 0) {
                inputView.setBookVisible();
            }
            inputView.setLines(lines);
            inputView.setEditText(text);
            if (type != 0) {
                inputView.setInputType(type);
                if (mOnChooseOpenBookListener != null) {
                    inputView.setOpenBookClickListener(new SendInputView.OpenBookClickListener() {
                        @Override
                        public void openBookClick() {
                            mOnChooseOpenBookListener.onOpenAdressBook();
                        }
                    });
                }
            }
            final EditText et = (EditText) inputView.getEt();
            et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(way == SendWay.toInt(SendWay.SENDWAY_SMS) || way == SendWay.toInt(SendWay.SENDWAY_PHONE)){
                        if (maxLen == et.getText().length()) {
                            if (mListener != null) {
                                mListener.OnChange();
                            }
                        }
                    }else if(way == SendWay.toInt(SendWay.SENDWAY_EMAIL)){
                        if(TabCheckUtils.isEmail(et.getText().toString())){
                            if (mListener != null) {
                                mListener.OnChange();
                            }
                        }
                    }else{
                        if (mListener != null) {
                            mListener.OnChange();
                        }
                    }
                }
                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            if(!text.equals("") && getEditText().contains(text)){
                Toast.makeText(context, "不能有重复的发送方式", Toast.LENGTH_SHORT).show();
                return;
            }
            inputView.requestFocus();
            inputView.setOnAddClickListener(onClickListener);
            inputView.setHintTextViewWidth(mHintWidth);
            addView(inputView);
            mCount++;
            if(!text.equals("")){
                if (mListener != null) {
                    mListener.OnChange();
                }
            }
        } else {
            if (toast == null) {
                toast = Toast.makeText(context, "最多只能添加9个联系人", Toast.LENGTH_SHORT);
            } else {
                toast.setText("最多只能添加9个联系人");
                toast.setDuration(Toast.LENGTH_SHORT);
            }
            toast.show();
            return;
        }
    }

    public void removeInputView(SendInputView inputView) {
        if(inputView.getBookVisibi() == 0 && getChildCount() > 1){
            SendInputView view = (SendInputView) getChildAt(1);
            if(view != null){
                view.setBookVisible();
            }
        }
        removeView(inputView);
        mCount--;
        if (mListener != null) {
            mListener.OnChange();
        }
    }

    public void removeAllInputView() {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            removeView(childView);
            mCount--;
        }
    }

    private SendInputView.OnAddClickListener onClickListener = new SendInputView.OnAddClickListener() {
        @Override
        public void onAddClick(SendInputView view) {
            removeInputView(view);
            if(mListener != null){
                if(way == SendWay.toInt(SendWay.SENDWAY_SMS)){
                    mListener.OnSmsDetectionPerson(view.getEditText());
                }else if(way == SendWay.toInt(SendWay.SENDWAY_PHONE)){
                    mListener.OnPhoneDetectionPerson(view.getEditText());
                }
            }
        }
    };

    public SendInputView getInputView(String text){
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            if (childView instanceof SendInputView) {
                SendInputView iv = (SendInputView) childView;
                if (text.equals(iv.getEditText())) {
                    return iv;
                }
            }
        }
        return null;
    }

    public ArrayList<String> getEditText() {
        ArrayList<String> list = new ArrayList<>();
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            if (childView instanceof SendInputView) {
                SendInputView iv = (SendInputView) childView;
                if (!TextUtils.isEmpty(iv.getEditText())) {
                    list.add(iv.getEditText());
                } else {
                    removeInputView(iv);
                }
            }
        }
        return list;
    }

    public int getInputViewCount() {
        return mCount;
    }

    public void setOnChooseOpenBookListener(OnChooseOpenBookListener Listener) {
        mOnChooseOpenBookListener = Listener;
    }

    public static interface OnChooseOpenBookListener {
        public void onOpenAdressBook();
    }

    public void setOnChangeListener(OnChangeListener listener) {
        this.mListener = listener;
    }

    public static interface OnChangeListener {
        void OnSmsDetectionPerson(String text);
        void OnPhoneDetectionPerson(String text);
        void OnChange();
    }
}
