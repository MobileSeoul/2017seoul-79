package com.app.seoul.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.seoul.Model.search_a;
import com.app.seoul.R;
import com.app.seoul.View.Activity.BoardInfoActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ihoyong on 2017. 10. 29..
 */

public class se_a_adapter extends RecyclerView.Adapter<se_a_adapter.ViewHolder> {

    private Context context;
    private List<search_a> item;

    public se_a_adapter(List<search_a> item, Context context) {
        this.item = item;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.se_a, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final search_a item1 = item.get(position);
        holder.title.setText(item1.getTitle());

        holder.lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BoardInfoActivity.class);
                intent.putExtra("bid", String.valueOf(item.get(position).getId()));
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.se_a_text)
        TextView title;
        @BindView(R.id.se_a_lin)
        LinearLayout lin;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
