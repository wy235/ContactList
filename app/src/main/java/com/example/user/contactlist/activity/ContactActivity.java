package com.example.user.contactlist.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.contactlist.R;
import com.example.user.contactlist.adapter.SortAdapter;
import com.example.user.contactlist.bean.PersonBook;
import com.example.user.contactlist.view.CharacterParser;
import com.example.user.contactlist.view.ClearEditText;
import com.example.user.contactlist.view.PinyinComparator;
import com.example.user.contactlist.view.SideBar;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;
import com.zhy.m.permission.ShowRequestPermissionRationale;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<PersonBook> list = new ArrayList<>();
    private ArrayList<PersonBook> selectBook = new ArrayList<>();
    public final static int RESULT_CODE = 1;
    private ListView sortListView;
    private SortAdapter adapter;
    private CharacterParser characterParser;
    private List<PersonBook> SourceDateList = new ArrayList<>();
    private PinyinComparator pinyinComparator;
    private ArrayList<PersonBook> phoneBook = new ArrayList<>();
    private ArrayList<PersonBook> smsBook = new ArrayList<>();
    private ArrayList<PersonBook> datas = new ArrayList<>();
    private int type;
    private static final int REQUECT_CODE_BOOKS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_book);
        initTooBar();
        initView();
        if (!MPermissions.shouldShowRequestPermissionRationale(ContactActivity.this, Manifest.permission.READ_CONTACTS, REQUECT_CODE_BOOKS)) {
            MPermissions.requestPermissions(ContactActivity.this, REQUECT_CODE_BOOKS, Manifest.permission.READ_CONTACTS);
        }else{
            initData();
        }
    }

    private void initTooBar() {
        Toolbar topBar = (Toolbar) findViewById(R.id.toolbar);
        topBar.setTitle("");
        setSupportActionBar(topBar);
        topBar.setNavigationIcon(R.drawable.back_select);
        topBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        phoneBook = (ArrayList<PersonBook>) getIntent().getSerializableExtra("phone");
        smsBook = (ArrayList<PersonBook>) getIntent().getSerializableExtra("sms");
        type = getIntent().getIntExtra("addressType",0);
        switch (type){
            case 1:
                datas = smsBook;
                break;
            case 2:
                datas = phoneBook;
                break;
            default:
                break;
        }
    }

    @ShowRequestPermissionRationale(REQUECT_CODE_BOOKS)
    public void whyNeedCramer() {
        MPermissions.requestPermissions(ContactActivity.this, REQUECT_CODE_BOOKS, Manifest.permission.READ_CONTACTS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionGrant(REQUECT_CODE_BOOKS)
    public void requestCramerSuccess() {
        initData();
    }

    @PermissionDenied(REQUECT_CODE_BOOKS)
    public void requestCramerFailed() {
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.books_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            selectBook.clear();
            adapter.setOnGetPersonBookListener(new SortAdapter.getPersonBookListener() {
                @Override
                public void getPersonBooks(ArrayList<PersonBook> arrayList) {
                    if (arrayList.size() == 0) {
                        Toast.makeText(ContactActivity.this, getResources().getString(R.string.chooese_sendto), Toast.LENGTH_SHORT).show();
                    } else {
                        for (PersonBook book : arrayList) {
                            if (book.getTag() == 1) {
                                selectBook.add(book);
                            }
                        }
                        Intent intent = new Intent();
                        intent.putExtra("selectBook", selectBook);
                        setResult(RESULT_CODE, intent);
                        ContactActivity.this.finish();
                    }
                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void initView() {
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        SideBar sideBar = (SideBar) findViewById(R.id.sidrbar);
        TextView dialog = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(dialog);
        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        adapter = new SortAdapter(this, SourceDateList,datas);
        sortListView.setAdapter(adapter);
        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }

            }
        });

        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
                Toast.makeText(getApplication(), ((PersonBook) adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        //adapter.notifyDataSetChanged();

        ClearEditText mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

        //根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    /**
     * 为ListView填充数据
     *
     * @return
     */
    private List<PersonBook> filledData(ArrayList<PersonBook> arr) {
        List<PersonBook> mSortList = new ArrayList<>();

        for (int i = 0; i < arr.size(); i++) {
            PersonBook book = new PersonBook();
            book.setName(arr.get(i).getName());
            book.setPhoneNumber(arr.get(i).getPhoneNumber());
            //book.setSortLetters(pinyin.substring(0, 1).toUpperCase());
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(arr.get(i).getName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                book.setSortLetters(sortString.toUpperCase());
            } else {
                book.setSortLetters("#");
            }

            mSortList.add(book);
        }
        //adapter.notifyDataSetChanged();
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<PersonBook> filterDateList = new ArrayList<>();
        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (PersonBook person : SourceDateList) {
                String name = person.getName();
                if (name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(person);
                }
            }
        }
        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }


    private void initData() {
        new MyAsyncTask().execute();
    }

    @Override
    public void onClick(View view) {

    }

    private class MyAsyncTask extends AsyncTask<Void,Void,ArrayList<PersonBook>> {

        @Override
        protected ArrayList<PersonBook> doInBackground(Void... voids) {
            ContentResolver resolver = getContentResolver();
            Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
            Uri datauri = Uri.parse("content://com.android.contacts/data");
            Cursor cursor = resolver.query(uri, new String[]{"contact_id"}, null, null, null);
            // 获取contact_id 获取联系人id
            while (cursor.moveToNext()) {
                String contact_id = cursor.getString(0);
                if (contact_id != null) {
                    // 具体的某个联系人
                    PersonBook book = new PersonBook();
                    // 如果不为空 查询对应data表的联系人信息
                    Cursor dataCursor = resolver.query(datauri, new String[]{"data1", "mimetype"}, "contact_id=?", new String[]{contact_id}, null);
                    while (dataCursor.moveToNext()) {
                        String data1 = dataCursor.getString(0);
                        String mimetype = dataCursor.getString(1);
                        book.setTag(0);
                        if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
                            //Log.e("AddressBookActivity", "电话:" + data1);
                            if (data1.length() > 11) {
                                String str = data1.substring(data1.length() - 11, data1.length());
                                book.setPhoneNumber(str);
                            } else {
                                book.setPhoneNumber(data1);
                            }
                        } else if ("vnd.android.cursor.item/name".equals(mimetype)) {
                            //Log.e("AddressBookActivity", "姓名:" + data1);
                            book.setName(data1);
                        }
                    }
                    list.add(book);
                    // 释放游标
                    dataCursor.close();
                }

            }
            cursor.close();
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<PersonBook> books) {
            super.onPostExecute(books);
            Iterator<PersonBook> it = books.iterator();
            while (it.hasNext()) {
                PersonBook value = it.next();
                Pattern p = Pattern.compile("1([\\d]{10})");
                Matcher m = p.matcher(value.getPhoneNumber() == null ? "" : value.getPhoneNumber());
                Boolean boo = m.matches();
                if (value.getPhoneNumber() == null || value.getPhoneNumber().length() < 11 || value.getPhoneNumber().equals("") || !boo) {
                    it.remove();
                }
            }
            SourceDateList = filledData(books);
            // 根据a-z进行排序源数据
            Collections.sort(SourceDateList, pinyinComparator);
            if(SourceDateList.size() > 0 && datas.size() >0){
                for (PersonBook book: datas) {
                    for (int i = 0; i < SourceDateList.size(); i++) {
                        if(book.equals(SourceDateList.get(i))){
                            SourceDateList.get(i).setTag(1);
                        }
                    }
                }

            }
            adapter.updateListView(SourceDateList);
        }
    }

    private final String mPageName = "AddressBookActivity";




    @Override
    protected void onPause() {
        super.onPause();
    }
}