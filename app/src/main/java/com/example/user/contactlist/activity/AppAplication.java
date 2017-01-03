package com.example.user.contactlist.activity;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.List;

/**
 * Created by user on 2017/1/3.
 */
public class AppAplication extends Application {

    private static AppAplication mThis = null;
    private static Context instance;

    @Override
    public void onCreate() {
        super.onCreate();
        mThis = this;
        instance = getApplicationContext();
    }

    public static AppAplication getApplication() {
        return mThis;
    }


    /**
     * 输入框得到焦点弹出软键盘
     * @param view
     * @param context
     */
    public void showSoftInput(final View view, Context context){
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(500);
                InputMethodManager inputManager = (InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(view, 0);
            }
        }).start();
    }

    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }
}
