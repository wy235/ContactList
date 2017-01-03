package com.example.user.contactlist.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liuliya on 2015/11/9.
 */
public class TabCheckUtils {

    public static boolean isPhone(String phoneNumber) {
        Pattern p = Pattern.compile("^[1][2-9]\\d{9}");
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    // 检测手机号码
    public static boolean isValidPhoneNumber(String number) {
        //$pattern="^(13|15|18)/d{9}$/";//手机号码
//		String test = "^1[3|4|5|8][0-9]/d{8}$/";
//		String test = "^(13|15|18)/d{9}$/";
//		String test = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        String test = "^((\\+?86)?|\\(\\+?86\\)?|(86))0?((13[0-9])|(140)|(145)|(147)|(15[^4,\\D])|(18[0-9])|(170)|(173)|(177))\\d{8}$";
        Pattern pattern = Pattern.compile(test);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    public static boolean isQQ(String qqNumber){
        Pattern p = Pattern.compile("^[1-9][0-9]{4,}$");
        Matcher m = p.matcher(qqNumber);
        return m.matches();
    }

    public static boolean isEmail(String email){
        Pattern p = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isSend(String send){
        Pattern p = Pattern.compile("^[a-zA-Z0-9_\\s\\u4e00-\\u9fa5]*$");
        Matcher m = p.matcher(send);
        return m.matches();
    }

    public static boolean isPassword(String password){
        Pattern p = Pattern.compile("^[0-9]{6,16}");
        Matcher m = p.matcher(password);
        return m.matches();
    }
}
