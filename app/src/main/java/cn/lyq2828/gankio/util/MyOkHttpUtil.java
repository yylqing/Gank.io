package cn.lyq2828.gankio.util;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.lyq2828.gankio.db.DateDB;


/**
 * Created by singinger on 17-5-3.
 */

public class MyOkHttpUtil {

    /**
     * 简单的请求封装，
     *
     * @param address        地址
     * @param stringCallback 回调函数
     */
    public static void sendOkHttpUtilRequest(String address, StringCallback stringCallback) {
        OkHttpUtils.get()
                .url(address)
                .build()
                .execute(stringCallback);
    }

    /**
     * 获取图片
     *
     * @param address
     * @param bitmapCallback
     */

    public static void getImageByOkHttpUtil(String address, BitmapCallback bitmapCallback) {
        OkHttpUtils.get()
                .url(address)
                .build()
                .execute(bitmapCallback);
    }

    /**
     * 直接设置图片
     *
     * @param address
     * @param imageView
     */
    public static void setImageByOkHttpUtil(String address, final ImageView imageView) {
        OkHttpUtils.get()
                .url(address)
                .build()
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(Bitmap response) {
                        imageView.setImageBitmap(response);
                    }
                });
    }

    /**
     * 处理数据编码
     *
     * @param asciicode
     * @return
     */
    private static String ascii2native(String asciicode) {
        String[] asciis = asciicode.split("\\\\u");
        String nativeValue = asciis[0];
        try {
            for (int i = 1; i < asciis.length; i++) {
                String code = asciis[i];
                nativeValue += (char) Integer.parseInt(code.substring(0, 4), 16);
                if (code.length() > 4) {
                    nativeValue += code.substring(4, code.length());
                }
            }
        } catch (NumberFormatException e) {
            return asciicode;
        }
        return nativeValue;
    }

    public static String getDayData(String date) {
        String[] strs = date.split("-");
        String address = "http://gank.io/api/day/" + strs[0] + "/" + strs[1] + "/" + strs[2];
        return address;
    }

    public static void getNewestData(StringCallback stringCallback){
        OkHttpUtils.get()
                .url("http://gank.io/api/day/history")
                .build()
                .execute(stringCallback);
    }



}
