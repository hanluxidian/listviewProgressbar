package com.hanlu.listviewprogressbar.utils;

import android.os.Environment;

import androidx.appcompat.app.AppCompatActivity;

import com.hanlu.listviewprogressbar.bean.AppContent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DownloadUtils {

    public static String getDownloadPath() {
        String downloadPath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            downloadPath = Environment.getExternalStorageDirectory() +
                    File.separator + "download";
        }
        return downloadPath;
    }


    public static List<AppContent> getData() {

        List<AppContent> list = new ArrayList<AppContent>();
        AppContent app1 = new AppContent("zhi fu bao", "http://d2.eoemarket.com/app0/5/5666/apk/2102191.apk?channel_id=426&tid=l4qizsg0f8uodiy5mp3w1amn");
        list.add(app1);

        AppContent app2 = new AppContent("jingdong", "http://shouji.360tpcdn.com/190820/38e224f337d393263e0a775624a4f7a5/com.jingdong.app.mall_69021.apk");
        list.add(app2);

        AppContent app3 = new AppContent("qq music", "http://shouji.360tpcdn.com/190830/ba4a72787d863071583a0d54db9a6701/com.tencent.qqmusic_1108.apk");
        list.add(app3);

        AppContent app4 = new AppContent("tencent", "http://shouji.360tpcdn.com/190728/99b0e4180be4e160ff78b4e22dada91b/com.tencent.mm_1460.apk");
        list.add(app4);

        AppContent app5 = new AppContent("BaiduMap", "http://shouji.360tpcdn.com/190827/5222deec8849d2534d9c35a5da882881/com.baidu.BaiduMap_932.apk");
        list.add(app5);

        AppContent app6 = new AppContent("baidu hi", "http://shouji.360tpcdn.com/190826/4182ec955aaa9ffe0d7b73a70cc00521/com.baidu.hi_249.apk");
        list.add(app6);

        AppContent app7 = new AppContent("baidu know", "http://shouji.360tpcdn.com/190828/cbe2a30c24967f4b2f7f8c9de3f3413b/com.baidu.iknow_646.apk");
        list.add(app7);

        AppContent app8 = new AppContent("nuo mi", "http://shouji.360tpcdn.com/190829/0cc44056dc2c93d4d9c7e621e78b2c68/com.nuomi_432.apk");
        list.add(app8);

        AppContent app9 = new AppContent("baidu video", "http://shouji.360tpcdn.com/190829/c6e99e8ca0c512226ad272e9cef34882/com.baidu.video_1081008662.apk");
        list.add(app9);

        AppContent app10 = new AppContent("baidu translate", "http://shouji.360tpcdn.com/190827/153ea195494fb3676eeadc3cbb5aa9d0/com.baidu.baidutranslate_108.apk");
        list.add(app10);

        AppContent app11 = new AppContent("baidu channel", "http://d2.eoemarket.com/app0/142/142867/apk/1491116.apk?channel_id=426");
        list.add(app11);

        AppContent app12 = new AppContent("baidu wenku", "http://shouji.360tpcdn.com/190812/9d3c2ce8519d3e80236d19d4f3525fe5/com.baidu.wenku_6380.apk");
        list.add(app12);
        return list;
    }
}
