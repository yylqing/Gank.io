package cn.lyq2828.gankio.util;

import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                            JSONObject jsonObject = new JSONObject(response);
                            String result = jsonObject.getString("results");
                            JSONArray jsonArray = new JSONArray(result);
                            for(int i = 0;i < jsonArray.length(); i++){
                                JSONObject oneResults = jsonArray.getJSONObject(i);
                                String publish_time = oneResults.getString("publishedAt");
                                String image_url = oneResults.getString("url");
                                String who = oneResults.getString("who");
                                String date = publish_time.substring(0,10);
                                IndexPage indexPage = new IndexPage();
                                indexPage.setPublish_time(publish_time);
                                indexPage.setDate(date);
                                indexPage.setImage_url(image_url);
                                indexPage.setWho(who);
                                indexPage.setPage(page);
                                indexPage.save();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void downFresh(){
        this.page = this.page + 1;
        getData();
    }


}
