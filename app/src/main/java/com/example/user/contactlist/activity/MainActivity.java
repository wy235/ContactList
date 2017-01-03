package com.example.user.contactlist.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Toast;
import com.example.user.contactlist.R;
import com.example.user.contactlist.bean.PersonBook;
import com.example.user.contactlist.bean.SendWay;
import com.example.user.contactlist.view.ChooseWayEditView;
import com.example.user.contactlist.view.SendInputView;
import java.util.ArrayList;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements ChooseWayEditView.OnChangeListener{

    @Bind(R.id.sendWay_sms)
    ChooseWayEditView mSms;
    @Bind(R.id.sendWay_phone)
    ChooseWayEditView mPhone;
    @Bind(R.id.sendWay_email)
    ChooseWayEditView mEmail;
    @Bind(R.id.sendWay_weixin)
    SendInputView toWX;
    @Bind(R.id.sendWay_weixin_frends)
    SendInputView friendsWX;
    private int width;
    private final static int REQUEST_CODE = 0;
    private final static int PHONEREQUEST_CODE = 1;
    private ArrayList<PersonBook> phoneBook = new ArrayList<>();
    private ArrayList<PersonBook> smsBook = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        width = getResources().getDimensionPixelSize(R.dimen.input_chooseway_hint_width);
    }

    @OnClick({R.id.item1, R.id.item2, R.id.item3, R.id.item4, R.id.item5})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item1:
                if (AppAplication.isWeixinAvilible(MainActivity.this)) {
                    addSendWayWeixin(1);
                } else {
                    Toast.makeText(MainActivity.this,getResources().getString(R.string.check_weixin),Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.item2:
                if (AppAplication.isWeixinAvilible(MainActivity.this)) {
                    addSendWayWeixin(2);
                } else {
                    Toast.makeText(MainActivity.this,getResources().getString(R.string.check_weixin),Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.item3:
                addSendWayPhone("");
                break;
            case R.id.item4:
                addSendWaySms("");
                break;
            case R.id.item5:
                addSendWayEmail("");
                break;
            default:
                break;
        }
    }

    private void addSendWaySms(String string) {
        mSms.setMaxLence(11);
        mSms.setMyHint(getResources().getString(R.string.fill_in_the_number));
        mSms.setMyLines(true);
        mSms.setType(InputType.TYPE_CLASS_PHONE);
        mSms.setHintInfo(getResources().getString(R.string.sms), width);
        mSms.setWayType(SendWay.toInt(SendWay.SENDWAY_SMS));
        mSms.setOnChooseOpenBookListener(new ChooseWayEditView.OnChooseOpenBookListener() {
            @Override
            public void onOpenAdressBook() {
                Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                intent.putExtra("sms", smsBook);
                intent.putExtra("addressType", 1);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        mSms.setOnChangeListener(this);
        mSms.setAddressBook(true);
        mSms.addInputView(string);
    }

    private void addSendWayPhone(String string) {
        mPhone.setMaxLence(11);
        mPhone.setMyHint(getResources().getString(R.string.fill_in_the_voice_number));
        mPhone.setMyLines(true);
        mPhone.setWayType(SendWay.toInt(SendWay.SENDWAY_PHONE));
        mPhone.setType(InputType.TYPE_CLASS_PHONE);
        mPhone.setHintInfo(getResources().getString(R.string.phone), width);
        mPhone.setAddressBook(true);
        mPhone.setOnChooseOpenBookListener(new ChooseWayEditView.OnChooseOpenBookListener() {
            @Override
            public void onOpenAdressBook() {
                Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                intent.putExtra("phone", phoneBook);
                intent.putExtra("addressType", 2);
                startActivityForResult(intent, PHONEREQUEST_CODE);
            }
        });
        mPhone.setOnChangeListener(this);
        mPhone.addInputView(string);
    }

    private void addSendWayEmail(String str) {
        mEmail.setMaxLence(40);
        mEmail.setMyHint(getResources().getString(R.string.check_email));
        mEmail.setMyLines(true);
        mEmail.setWayType(SendWay.toInt(SendWay.SENDWAY_EMAIL));
        mEmail.setType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        mEmail.setHintInfo(getResources().getString(R.string.email), width);
        mEmail.setOnChangeListener(this);
        mEmail.addInputView(str);
    }

    private void addSendWayWeixin(int type) {
        switch (type){
            case 1:
                toWX.setEditText(getResources().getString(R.string.wechat_friends));
                break;
            case 2:
                toWX.setEditText(getResources().getString(R.string.weixi_frends));
                break;
            default:
                break;
        }
        toWX.setNoEnabled();
        toWX.setVisibility(View.VISIBLE);
    }

    @Override
    public void OnSmsDetectionPerson(String text) {

    }

    @Override
    public void OnPhoneDetectionPerson(String text) {

    }

    @Override
    public void OnChange() {

    }
}
