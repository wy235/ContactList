package com.example.user.contactlist.bean;

import java.io.Serializable;

/**
 * Created by 王宇 on 2015/10/21.
 */
public class PersonBook implements Serializable {
    private String name;
    private String phoneNumber;
    private int tag;
    private String sortLetters;  //显示数据拼音的首字母

    public boolean isDetete() {
        return isDetete;
    }

    public void setDetete(boolean detete) {
        isDetete = detete;
    }

    private boolean isDetete;

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSortLetters() {
        return sortLetters;
    }
    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }
}
