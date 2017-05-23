package cn.lyq2828.gankio.util;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.util.ArrayList;
import java.util.List;

import cn.lyq2828.gankio.R;
import cn.lyq2828.gankio.db.Data;

/**
 * Created by singinger on 17-5-23.
 */

public class ActiviteAdapter extends RecyclerView.Adapter<ActiviteAdapter.ViewHolder> {

    private ArrayList<Data> mdata;

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView activiteImage;
        TextView activiteText;

        public ViewHolder(View itemView) {
            super(itemView);
            activiteImage = (ImageView) itemView.findViewById(R.id.activite_image);
            activiteText = (TextView) itemView.findViewById(R.id.activite_text);
        }
    }

    public ActiviteAdapter(ArrayList<Data> data) {
        mdata = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ViewHolder mholder = holder;
        Data data = mdata.get(position);
        OkHttpUtils.get()
                .url(data.getImage_url())
                .build()
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(Bitmap response) {
                        mholder.activiteImage.setImageBitmap(response);
                    }
                });
        holder.activiteText.setText(data.getDec());
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

}
