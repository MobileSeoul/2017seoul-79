package com.app.seoul.View.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.seoul.Model.Food.item;
import com.app.seoul.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ihoyong on 2017. 10. 27..
 */

public class food_adapater extends RecyclerView.Adapter<food_adapater.ViewHolder> {

    private Context context;
    private List<item> item;

    public food_adapater(List<item> item, Context context) {
        this.item = item;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        com.app.seoul.Model.Food.item item1 = item.get(position);

        if (item1.getFirstimage() != null) {

            Glide.with(context)
                    .load(item1.getFirstimage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.image);

        } else {
            Glide.with(context)
                    .load(item1.getFirstimage2())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.image);
        }

        holder.title.setText(item1.getTitle());
        holder.addr.setText(item1.getAddr1());
        holder.tel.setText(item1.getTel());

    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.food_image)
        ImageView image;
        @BindView(R.id.food_title)
        TextView title;
        @BindView(R.id.food_addr)
        TextView addr;
        @BindView(R.id.food_tel)
        TextView tel;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
