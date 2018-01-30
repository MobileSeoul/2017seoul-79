package com.app.seoul.View.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.seoul.Model.search;
import com.app.seoul.R;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class search_adapter extends RecyclerView.Adapter<search_adapter.ViewHolder> {

    Context context;
    List<search> item;

    public search_adapter(List<search> item, Context context) {
        super();
        this.item = item;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f_search_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        search item1 = item.get(position);

        holder.title.setText( item1.getTitle());

        Glide.with(context)
                .load(item1.getImage())
                .thumbnail(0.3f)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        @BindView(R.id.search_item_title)
        TextView title;
        @Nullable
        @BindView(R.id.search_item_image)
        ImageView imageView;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView );
        }
    }
}
