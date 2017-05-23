package cn.lyq2828.gankio;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.lyq2828.gankio.db.Data;
import cn.lyq2828.gankio.db.Date;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {


    private ArrayList<Date> dateList = new ArrayList<>();
    private ArrayList<Data> dataList = new ArrayList<Data>();
    private TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        System.out.print(dataList);


    }

//    public void initView() {
//        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        CardItemTouchHelperCallback cardCallback = new CardItemTouchHelperCallback<>(recyclerView.getAdapter(), dateList);
//        ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
//        CardLayoutManager cardLayoutManager = new CardLayoutManager(recyclerView, touchHelper);
//        recyclerView.setLayoutManager(cardLayoutManager);
//        touchHelper.attachToRecyclerView(recyclerView);
//        cardCallback.setOnSwipedListener(new OnSwipeListener<String>() {
//            @Override
//            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
//                Log.i("onSwiping", "onSwiping");
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, String o, int direction) {
//                Log.i("onSwiped", "onSwiped");
//            }
//
//            @Override
//            public void onSwipedClear() {
//                Log.i("onSwipedClear", "onSwipedClear");
//            }
//        });
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.where_item:
                Intent where = new Intent(Intent.ACTION_VIEW);
                where.setData(Uri.parse("https://github.com/yylqing/Gank.io"));
                startActivity(where);
                break;
            case R.id.more_item:
                Toast.makeText(this, "关于", LENGTH_SHORT).show();
                Intent more = new Intent(MainActivity.this, MoreAboutme.class);
                startActivity(more);
                break;
            default:
        }
        return true;
    }

    public void initDate() {
        OkHttpUtils.get()
                .url("http://gank.io/api/day/history")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String results = jsonObject.getString("results");
                            JSONArray jsonArray = new JSONArray(results);
                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject haha = jsonArray.getJSONObject(i);
//                                Log.i(String.valueOf(i),haha.getString("desc"));
                                String date = jsonArray.getString(i);
                                Log.i(String.valueOf(i), date);
                                Date dates = new Date(date);
                                dateList.add(dates);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            System.out.print("!!!!!!!!!!!!!!hahahhahahahaha!!!!!!!!!!!!!!");
                            System.out.print(e);
                        }
                    }
                });
    }

    public void initData() {
        OkHttpUtils.get()
                .url("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String results = jsonObject.getString("results");
                            JSONArray jsonArray = new JSONArray(results);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject oneResults = jsonArray.getJSONObject(i);
//                                String data = jsonArray.getString(i);
//                                Log.i(String.valueOf(i), data);
                                String url = oneResults.getString("url");
                                String desc = oneResults.getString("desc");
                                Data data = new Data(url,desc);
                                dataList.add(data);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
