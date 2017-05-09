package cn.lyq2828.gankio;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import cn.lyq2828.gankio.util.MyOkHttpUtil;

public class MainActivity extends AppCompatActivity {

    private TextView textView1;
    private TextView textView2;
    private ImageView imageView1;
    private Button button1;
    private WebView webView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        imageView1 = (ImageView) findViewById(R.id.imageView);
//        MyOkHttpUtil.setImageByOkHttpUtil("http://ww1.sinaimg.cn/large/61e74233ly1feuogwvg27j20p00zkqe7.jpg", imageView1);
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
//        textView1.setText("hahaha");
//        textView2.setText(MyOkHttpUtil.getDayData("2017-05-04"));
        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击", Toast.LENGTH_SHORT).show();
                String data = "Hello SelectDay";
                Intent intent = new Intent(MainActivity.this, MoreAboutme.class);
                intent.putExtra("extra_data", data);
                startActivity(intent);
            }
        });
//        展示本地html文件
//        webView1 = (WebView) findViewById(R.id.webView1);
//        webView1.loadUrl("file:///android_asset/More_about_me.html");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share_item:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
                intent.putExtra(Intent.EXTRA_TEXT, "I have successfully share my message through my app");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent, getTitle()));
                break;
            case R.id.more_item:
                Toast.makeText(this, "More", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

    /**
     * 测试一下 MyOkHttpUtil
     */
    public void setText() {
        MyOkHttpUtil.sendOkHttpUtilRequest("http://gank.io/api/data/Android/10/5", new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_SHORT).show();
                textView1.setText(e.toString());
            }

            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "成功", Toast.LENGTH_SHORT).show();
                textView1 = (TextView) findViewById(R.id.textView1);
                textView1.setText(response);
            }
        });

    }

    public void setImage() {
        imageView1 = (ImageView) findViewById(R.id.imageView);
        MyOkHttpUtil.getImageByOkHttpUtil("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-02-926821_1453024764952889_775781470_n.jpg", new BitmapCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(Bitmap response) {
                imageView1.setImageBitmap(response);
            }
        });
    }
}
