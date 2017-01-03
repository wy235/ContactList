package com.example.user.contactlist.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.user.contactlist.R;
import com.example.user.contactlist.bean.PersonBook;

public class SortAdapter extends BaseAdapter implements SectionIndexer {
    private List<PersonBook> list = null;
    private Context mContext;
    private ArrayList<PersonBook> books = new ArrayList<>();
    private List<PersonBook> datas = new ArrayList<>();
    private LayoutInflater inflater;

    public SortAdapter(Context mContext, List<PersonBook> list,List<PersonBook> datas) {
        this.mContext = mContext;
        this.list = list;
        this.datas = datas;
        inflater = LayoutInflater.from(mContext);
    }


    public void updateListView(List<PersonBook> mlist) {
        this.list = mlist;
        if(list.size() > 0 && datas.size() >0){
            for (PersonBook book: datas) {
                for (int i = 0; i < list.size(); i++) {
                    if(book.getPhoneNumber().equals(list.get(i).getPhoneNumber())){
                        list.get(i).setTag(1);
                    }
                }
            }

        }
        notifyDataSetChanged();
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.item_address_book, arg2, false);
            ViewHolder holder = new ViewHolder(view);
            view.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.name.setText(list.get(position).getName());
        holder.phone.setText(list.get(position).getPhoneNumber());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    list.get(position).setTag(1);
                    books.add(list.get(position));
                } else {
                    try {
                        list.get(position).setTag(0);
                        if(books.get(position) != null){
                            books.get(position).setDetete(true);
                        }
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                    //books.remove(list.get(position));
                }
            }
        });

        if (list.get(position).getTag() == 1 && !list.get(position).isDetete()) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }

        int section = getSectionForPosition(position);
        if (position == getPositionForSection(section)) {
            holder.catalog.setVisibility(View.VISIBLE);
            holder.catalog.setText(list.get(position).getSortLetters());
        } else {
            holder.catalog.setVisibility(View.GONE);
        }
        return view;

    }


    private static class ViewHolder {
        private CheckBox checkBox;
        private TextView name;
        private TextView phone;
        private TextView title;
        private TextView catalog;

        public ViewHolder(View view) {
            checkBox = (CheckBox) view.findViewById(R.id.item_book_cb);
            name = (TextView) view.findViewById(R.id.item_book_name);
            phone = (TextView) view.findViewById(R.id.item_book_phone);
            title = (TextView) view.findViewById(R.id.filter_edit);
            catalog = (TextView) view.findViewById(R.id.catalog);
        }
    }


    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }


    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }


    private String getAlpha(String str) {
        String sortStr = str.trim().substring(0, 1).toUpperCase();

        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    public void setOnGetPersonBookListener(getPersonBookListener listener) {
        if (books != null && listener != null) {
            listener.getPersonBooks(books);
        }
    }

    public interface getPersonBookListener {
        void getPersonBooks(ArrayList<PersonBook> arrayList);
    }

}

