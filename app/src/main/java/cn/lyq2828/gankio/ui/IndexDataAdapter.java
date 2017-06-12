package cn.lyq2828.gankio.ui;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.lyq2828.gankio.R;
import cn.lyq2828.gankio.db.IndexData;
import cn.lyq2828.gankio.db.IndexPage;
import cn.lyq2828.gankio.util.CircleTransform;


/**
 * Created by singinger on 17-5-23.
 */

public class IndexDataAdapter extends RecyclerView.Adapter<IndexDataAdapter.ViewHolder> {

    //    private ArrayList<IndexData> mdata;
    private List<IndexPage> mdata;
    static class ViewHolder extends RecyclerView.ViewHolder {

        View indexDataView;
        ImageView activiteImage;
        TextView activiteText;

        public ViewHolder(View itemView) {
            super(itemView);
            indexDataView = itemView;
            activiteImage = (ImageView) itemView.findViewById(R.id.activite_image);
            activiteText = (TextView) itemView.findViewById(R.id.activite_text);
        }
    }

    //    public IndexDataAdapter(ArrayList<IndexData> indexData) {
//        mdata = indexData;
//    }
    public IndexDataAdapter(List<IndexPage> indexData) {
        mdata = indexData;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.indexDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
//                IndexData data = mdata.get(position);
                IndexPage data = mdata.get(position);
                String date = data.getDate();
                Intent intent = new Intent(parent.getContext(),Details.class);
                intent.putExtra("date",date);
                parent.getContext().startActivity(intent);
//                Toast.makeText(v.getContext(), "哈哈哈" + data.getDesc(), Toast.LENGTH_SHORT).show();


            }
        });
        viewHolder.activiteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
//                IndexData data = mdata.get(position);
                IndexPage data = mdata.get(position);
                String image_Url = data.getImage_url();
//                Toast.makeText(v.getContext(), "哈哈哈" + data.getImage_url(), Toast.LENGTH_SHORT).show();

                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View imgEntryView = inflater.inflate(R.layout.dialog_photo, null); // 加载自定义的布局文件
                final AlertDialog dialog = new AlertDialog.Builder(parent.getContext()).create();
                ImageView img = (ImageView) imgEntryView.findViewById(R.id.large_image);
                Picasso.with(img.getContext())
                        .load(image_Url)
                        .into(img);
                dialog.setView(imgEntryView); // 自定义dialog
                dialog.show();
                // 点击布局文件（也可以理解为点击大图）后关闭dialog，这里的dialog不需要按钮
                imgEntryView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View paramView) {
                        dialog.cancel();
                    }
                });

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
//        IndexData indexData = mdata.get(position);
        IndexPage indexData = mdata.get(position);
        Picasso.with(holder
                .activiteImage.getContext())
                .load(indexData.getImage_url())
                .transform(new CircleTransform(50))
                .into(holder.activiteImage);
        String text = indexData.getDate().substring(5,10) + indexData.getDec();
        holder.activiteText.setText(text);
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

}