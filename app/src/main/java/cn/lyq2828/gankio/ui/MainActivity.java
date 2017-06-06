package cn.lyq2828.gankio.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import cn.lyq2828.gankio.R;
import cn.lyq2828.gankio.db.IndexPage;
import cn.lyq2828.gankio.db.IndexData;
import cn.lyq2828.gankio.db.Date;
import cn.lyq2828.gankio.util.getIndexData;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    private ArrayList<IndexData> indexDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        initData();
        getIndexData.getData();
        Log.i("sasas", "sasasas");
        List<IndexPage> datas = DataSupport.findAll(IndexPage.class);
        for (IndexPage data : datas) {
            Log.i("hahaha", data.getDate());
       }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setLayoutManager(layoutManager);
        IndexDataAdapter adapter = new IndexDataAdapter(datas);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share_item:
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
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            System.out.print("!!!!!!!!!!!!!!hahahhahahahaha!!!!!!!!!!!!!!");
                            System.out.print(e);
                        }
                    }
                });
    }


}
