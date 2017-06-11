package cn.lyq2828.gankio.util;

import android.util.Log;

import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.List;

import cn.lyq2828.gankio.db.IndexPage;

/**
 * Created by singinger on 17-6-2.
 */

public class getIndexData {

    private static int page;

    public getIndexData(){
        this.page = 1;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public static void getData() {
        String url = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/" + page;
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            setData2DB(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public static void upGetMore(){
        page = page + 1;
        getData();
    }

    private static void setData2DB(String response) throws JSONException {
        JSONArray jsonArray = new JSONArray(new JSONObject(response).getString("results"));
//        Log.i("sas",response);
        for (int i = 0;i<jsonArray.length();i++){
            JSONObject oneResults = jsonArray.getJSONObject(i);
            String tag_id = oneResults.getString("_id");
            List<IndexPage> datas = DataSupport.where("tag_id = ?",tag_id).find(IndexPage.class);
//            Log.i("datas", datas.get(0).getDate());
//            Log.i("datas.size()", String.valueOf(datas.size()));
            if(datas.size() != 0) {
                Log.i("数据之前是否有：", "原有");
//                Log.i("qwe", String.valueOf(datas.isEmpty()));
                continue;
            }else{
                String publish_time = oneResults.getString("publishedAt");
                String image_url = oneResults.getString("url");
                String who = oneResults.getString("who");
                String date = publish_time.substring(0,10);
                IndexPage indexPage = new IndexPage();
                indexPage.setTag_id(tag_id);
                indexPage.setPublish_time(publish_time);
                indexPage.setDate(date);
                indexPage.setImage_url(image_url);
                indexPage.setWho(who);
                indexPage.setPage(page);
                indexPage.save();
            }
        }
    }

}
