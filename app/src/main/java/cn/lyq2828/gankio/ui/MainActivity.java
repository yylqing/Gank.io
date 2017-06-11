package cn.lyq2828.gankio.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

import cn.lyq2828.gankio.R;
import cn.lyq2828.gankio.db.IndexPage;

import static android.widget.Toast.LENGTH_SHORT;
import static cn.lyq2828.gankio.util.getIndexData.getData;
import static cn.lyq2828.gankio.util.getIndexData.upGetMore;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshView swipeRefresh;
    private IndexDataAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        swipeRefresh = (SwipeRefreshView) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);

        initView();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                down_Refresh();
            }
        });
        swipeRefresh.setOnLoadListener(new SwipeRefreshView.OnLoadListener(){
            @Override
            public void onLoad() {
                up_Refresh();
            }
        });


        swipeRefresh.setRefreshing(false);

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

    /**
     * 填数据到RecycleView
     */
    public void initView() {
        getData();
        List<IndexPage> datas = DataSupport.order("publish_time desc").find(IndexPage.class);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new IndexDataAdapter(datas);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 下拉刷新
     */
    public void down_Refresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
//                    Log.i("qweqwe","qweqwe");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getData();
//                        Log.i("asdasd","adsaasd");
                        Toast.makeText(MainActivity.this, "数据刷新了", Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    /**
     * 上拉加载更多
     */
    public void up_Refresh(){new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
//                    Log.i("qweqwe","qweqwe");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    upGetMore();
//                        Log.i("asdasd","adsaasd");
                    adapter.notifyDataSetChanged();
                    swipeRefresh.setRefreshing(false);
                }
            });
        }
    }).start();

    }

}
