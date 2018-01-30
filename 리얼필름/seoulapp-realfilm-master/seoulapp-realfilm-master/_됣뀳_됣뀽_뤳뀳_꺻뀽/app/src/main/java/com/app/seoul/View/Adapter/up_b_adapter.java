package com.app.seoul.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.app.seoul.Model.up_bb;
import com.app.seoul.R;
import com.app.seoul.View.Activity.BoardInfoActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class up_b_adapter extends RecyclerView.Adapter<up_b_adapter.ViewHolder> {

    private Context context;
    private List<up_bb> item;
    private Integer[] tagImage = new Integer[12];

    public up_b_adapter(List<up_bb> item, Context context) {
        super();
        this.item = item;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f_home_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final up_bb item1 = item.get(position);
        getTag();
        holder.title.setText(item1.getTitle());

        if (item1.getImgcount() > 1) { // 사진이 2개이상일 경우
            holder.imageCount.setText("+" + String.valueOf(item1.getImgcount() - 1));
        }

        holder.commenttext.setText(String.valueOf(item1.getComment()));

        if (item1.getLike() > 0) {
            holder.likeimg.setBackgroundResource(R.drawable.main_heart2_icon_60x53);
        }
        holder.liketext.setText(String.valueOf(item1.getLike()));
        holder.datetext.setText(String.valueOf(item1.getDate()));

        Glide.with(context)
                .load(item1.getImage())
                .thumbnail(0.3f)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.imageView);


        Glide.with(context)
                .load(tagImage[item1.getBoardtag()])
                .thumbnail(0.3f)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.tag);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BoardInfoActivity.class);
                intent.putExtra("bid", String.valueOf(item.get(position).getId()));
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

        @Nullable
        @BindView(R.id.home_title)
        TextView title;
        @Nullable
        @BindView(R.id.home_imageView)
        ImageView imageView;
        @Nullable
        @BindView(R.id.home_imageCount)
        TextView imageCount;
        @BindView(R.id.f_home_like_img)
        ImageView likeimg;
        @BindView(R.id.f_home_like_text)
        TextSwitcher liketext;
        @BindView(R.id.f_home_comment_text)
        TextView commenttext;
        @BindView(R.id.f_home_date_text)
        TextView datetext;
        @BindView(R.id.f_home_like_lin)
        LinearLayout likeClick;
        @BindView(R.id.home_tag)
        ImageView tag;
        @BindView(R.id.home_cardview)
        CardView view;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void getTag() {
        tagImage[0] = R.drawable.category_alley_icon_40x56;
        tagImage[1] = R.drawable.category_show_icon_40x56;
        tagImage[2] = R.drawable.category_music_icon_40x56;
        tagImage[3] = R.drawable.category_drinking_icon_40x56;
        tagImage[4] = R.drawable.category_park_icon_40x56;
        tagImage[5] = R.drawable.category_walk_icon_40x56;
        tagImage[6] = R.drawable.category_traffic_icon_40x56;
        tagImage[7] = R.drawable.category_game_icon_40x56;
        tagImage[8] = R.drawable.category_cafe_icon_40x56;
        tagImage[9] = R.drawable.category_exercise_icon_40x56;
        tagImage[10] = R.drawable.category_busking_icon_40x56;
        tagImage[11] = R.drawable.category_photozone_icon_40x56;
    }


}
