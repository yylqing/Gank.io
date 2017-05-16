package cn.lyq2828.gankio;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.yuqirong.cardswipelayout.CardItemTouchHelperCallback;
import me.yuqirong.cardswipelayout.CardLayoutManager;
import me.yuqirong.cardswipelayout.OnSwipeListener;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Integer> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        CardItemTouchHelperCallback cardCallback = new CardItemTouchHelperCallback(recyclerView.getAdapter(), dataList);
        ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback); CardLayoutManager cardLayoutManager = new CardLayoutManager(recyclerView, touchHelper);
        recyclerView.setLayoutManager(cardLayoutManager);
        touchHelper.attachToRecyclerView(recyclerView);
        cardCallback.setOnSwipedListener(new OnSwipeListener() {
            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {

            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, Object o, int direction) {

            }

            @Override
            public void onSwipedClear() {

            }
        });

    }

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
                Toast.makeText(this, "关于", Toast.LENGTH_SHORT).show();
                Intent more = new Intent(MainActivity.this, MoreAboutme.class);
                startActivity(more);
                break;
            default:
        }
        return true;
    }


}
