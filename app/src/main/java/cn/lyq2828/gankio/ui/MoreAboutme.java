package cn.lyq2828.gankio.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import cn.lyq2828.gankio.R;
import cn.lyq2828.gankio.db.IndexPage;
import cn.lyq2828.gankio.util.DataCleanManager;

public class MoreAboutme extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_aboutme);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
                intent.putExtra(Intent.EXTRA_TEXT, "I have successfully share my message through my app");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent, getTitle()));
            }
        });
        TextView text_more = (TextView) findViewById(R.id.text_more);
        text_more.setTextIsSelectable(true);
        final Button button = (Button) findViewById(R.id.clear_button);
        try {
            button.setText("清除缓存(" + DataCleanManager.getTotalCacheSize(this) + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setBackgroundResource(R.drawable.shape_button_click);
                AlertDialog.Builder dialog = new AlertDialog.Builder(MoreAboutme.this);
//              dialog.setIcon(R.drawable.ic_launcher);//窗口头图标
                dialog.setTitle("提示");//窗口名
                dialog.setMessage("是否清除缓存 ");
                dialog.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        DataCleanManager.clearAllCache(MoreAboutme.this);
                        try {
                            button.setText("清除缓存(" + DataCleanManager.getTotalCacheSize(MoreAboutme.this) + ")");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        DataSupport.deleteAll(IndexPage.class);
                        button.setBackgroundResource(R.drawable.shape_button_test);
                        Intent intent = new Intent(MoreAboutme.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                dialog.setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        button.setBackgroundResource(R.drawable.shape_button_test);
                    }
                });
                dialog.show();
            }
        });
    }
}
