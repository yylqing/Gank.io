package cn.lyq2828.gankio.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.lyq2828.gankio.R;

import static cn.lyq2828.gankio.util.MyOkHttpUtil.getDayData;

public class Details extends AppCompatActivity {

    private WebView webView;
    private String details_html;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        webView = (WebView) findViewById(R.id.details_webView);
//        webView.setVerticalScrollBarEnabled(false);
//        webView.setHorizontalScrollBarEnabled(false);

        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        getDetails2Html(date);
    }


    public void getDetails2Html(String date) {
        final String[] html = new String[1];
        html[0] = "<html> <head> <meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\"> </hed> <body>";
        String url = getDayData(date);
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
                            Log.i("qweqwe", response);

                            JSONArray categorys = new JSONArray(new JSONObject(response).getString("category"));
                            JSONObject results = new JSONObject(new JSONObject(response).getString("results"));

                            for (int i = 0; i < categorys.length(); i++) {
                                if(categorys.getString(i) == "福利"){
                                    JSONArray android_data = results.getJSONArray(categorys.getString(i));
                                    html[0] += "<h3>" + categorys.getString(i) + "</h3> <ul>";
                                    for (int j = 0; j < android_data.length(); j++) {
                                        JSONObject jsonObject1 = android_data.getJSONObject(j);
                                        html[0] += "<li> <a href=\"" + jsonObject1.getString("url") + "\">" + jsonObject1.getString("desc") + "</a> (" + jsonObject1.getString("who") + ") </li>";
                                    }
                                    html[0] += "</ul>";
                                }else {
                                    JSONArray android_data = results.getJSONArray(categorys.getString(i));
                                    html[0] += "<h3>" + categorys.getString(i) + "</h3> <ul>";
                                    for (int j = 0; j < android_data.length(); j++) {
                                        JSONObject jsonObject1 = android_data.getJSONObject(j);
                                        html[0] += "<li> <a href=\"" + jsonObject1.getString("url") + "\">" + jsonObject1.getString("desc") + "</a> (" + jsonObject1.getString("who") + ") </li>";
                                    }
                                    html[0] += "</ul>";
                                }
                            }

                            webView.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8
                            webView.loadData(html[0], "text/html; charset=UTF-8", null);//这种写法可以正确解码
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


    }
}
