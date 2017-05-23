package cn.lyq2828.gankio.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cn.lyq2828.gankio.R;
import cn.lyq2828.gankio.db.IndexData;


/**
 * Created by singinger on 17-5-23.
 */

public class IndexDataAdapter extends RecyclerView.Adapter<IndexDataAdapter.ViewHolder> {

    private ArrayList<IndexData> mdata;

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView activiteImage;
        TextView activiteText;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public IndexDataAdapter(ArrayList<IndexData> indexData) {
        mdata = indexData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_item,
                parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.activiteImage =(ImageView) view.findViewById(R.id.activite_image);
        viewHolder.activiteText = (TextView) view.findViewById(R.id.activite_text);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        IndexData indexData = mdata.get(position);
        Picasso.with(holder.activiteImage.getContext()).load(indexData.getImage_url()).resize(120,120).into(holder.activiteImage);
        holder.activiteText.setText(indexData.getDesc());
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

}
