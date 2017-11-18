package com.lichao.orderfood.global;

import android.app.Application;

/**
 * Created by Administrator on 2017-11-17.
 */

public class MyApplication extends Application {
    public static int statusBarHeight;

    @Override
    public void onCreate() {
        super.onCreate();

        //MobSDK.init(this, "2271f16c7ece6", "a9494347e9c76baa217decee7c721796");
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
    }
}
