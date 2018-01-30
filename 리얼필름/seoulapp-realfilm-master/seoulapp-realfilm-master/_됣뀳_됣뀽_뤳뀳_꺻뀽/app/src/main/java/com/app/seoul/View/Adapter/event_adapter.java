package com.app.seoul.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.seoul.Model.Event.e_item;
import com.app.seoul.R;
import com.app.seoul.View.Activity.EventInfoActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ihoyong on 2017. 10. 22..
 */

public class event_adapter extends RecyclerView.Adapter<event_adapter.ViewHolder> {

    Context context;
    List<e_item> item;
    RequestManager requestManager;


    public event_adapter(List<e_item> item, Context context, RequestManager requestManager) {
        this.item = item;
        this.context = context;
        this.requestManager = requestManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final e_item item1 = item.get(position);

        holder.title.setText(item1.getTitle());
        holder.date.setText(item1.getEventstartdate() + " ~ " + item1.getEventenddate());

        final String image;
        if (item1.getFirstiamge() != null) {
            image = item1.getFirstiamge();
            Glide.with(context)
                    .load(item1.getFirstiamge())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(new ViewTarget<LinearLayout, GlideDrawable>(holder.background) {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            LinearLayout v = this.view;
                            v.setBackground(resource);
                        }
                    });
        } else {
            image = item1.getFirstimage2();
            Glide.with(context)
                    .load(item1.getFirstimage2())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(new ViewTarget<LinearLayout, GlideDrawable>(holder.background) {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            LinearLayout v = this.view;
                            v.setBackground(resource);
                        }
                    });
        }

        holder.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventInfoActivity.class);
                intent.putExtra("conid", item.get(position).getContentid());
                intent.putExtra("image", image);
                intent.putExtra("title", item.get(position).getTitle());
                intent.putExtra("mapx", item1.getMapx());
                intent.putExtra("mapy", item1.getMapy());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public void add(e_item i) {
        item.add(i);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        @BindView(R.id.event_title)
        TextView title;
        @Nullable
        @BindView(R.id.event_date)
        TextView date;
        @Nullable
        @BindView(R.id.event_background)
        LinearLayout background;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
