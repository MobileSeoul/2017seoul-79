package com.app.seoul.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.seoul.Model.Data;
import com.app.seoul.Model.search_c;
import com.app.seoul.R;
import com.app.seoul.View.Activity.UserProfileActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ihoyong on 2017. 10. 29..
 */

public class se_c_adapter extends RecyclerView.Adapter<se_c_adapter.ViewHolder> {

    private Context context;
    private List<search_c> item;

    public se_c_adapter(List<search_c> item, Context context) {
        this.item = item;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.se_c, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final search_c item1 = item.get(position);

        holder.name.setText(item1.getName());

        Glide.with(context)
                .load(item1.getProfile())
                .error(R.drawable.mypage_profile_default_250x250)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.image);

        holder.lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Data.upid = item.get(position).getId();
                Intent intent = new Intent(context, UserProfileActivity.class);
                intent.putExtra("uid", item.get(position).getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.sc_c_name)
        TextView name;
        @BindView(R.id.se_c_image)
        de.hdodenhof.circleimageview.CircleImageView image;
        @BindView(R.id.se_c_lin)
        LinearLayout lin;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
