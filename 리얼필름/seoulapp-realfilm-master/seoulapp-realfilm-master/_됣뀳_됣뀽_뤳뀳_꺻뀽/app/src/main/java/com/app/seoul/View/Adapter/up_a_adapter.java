package com.app.seoul.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.seoul.Model.up_aa;
import com.app.seoul.R;
import com.app.seoul.View.Activity.BoardInfoActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ihoyong on 2017. 10. 24..
 */

public class up_a_adapter extends RecyclerView.Adapter<up_a_adapter.ViewHolder> {

    private Context context;
    private List<up_aa> item;


    public up_a_adapter(List<up_aa> item, Context context) {

        super();
        this.item = item;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.up_a_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final up_aa item1 = item.get(position);

        Log.e("up_a", item1.getUrl() + " " + item1.getIcount());
        Glide.with(context)
                .load(item1.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.image);

        holder.icount.setText("+" + item1.getIcount());
        holder.lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BoardInfoActivity.class);
                intent.putExtra("bid", String.valueOf(item1.getBid()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.up_a_item_image)
        ImageView image;
        @BindView(R.id.up_a_item_icount)
        TextView icount;
        @BindView(R.id.up_a_lin)
        FrameLayout lin;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
