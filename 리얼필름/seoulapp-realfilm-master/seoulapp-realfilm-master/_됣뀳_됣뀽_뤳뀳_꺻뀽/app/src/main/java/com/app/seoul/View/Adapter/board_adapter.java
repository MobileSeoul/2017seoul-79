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

import com.app.seoul.Model.Event.e_item;
import com.app.seoul.Model.board;
import com.app.seoul.R;
import com.app.seoul.View.Activity.BoardInfoActivity;
import com.app.seoul.View.Activity.EventInfoActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class board_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Object> items;

    private Integer[] tagImage = new Integer[12];
    private static final int CONTENT_VIEW_TYPE = 1;
    private static final int ADS_VIEW_TYPE = 0;
    private ContentHolder contentHolder;
    private EventHolder eventHolder;

    public board_adapter(List<Object> items, Context context) {

        super();
        this.items = items;
        this.context = context;

    }

    @Override
    public int getItemViewType(int position) {
        return (position % 5 == 2) ? ADS_VIEW_TYPE : CONTENT_VIEW_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ADS_VIEW_TYPE:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.b_event_item, parent, false);
                return new EventHolder(view);

            case CONTENT_VIEW_TYPE:
            default:
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f_home_item, parent, false);
                return new ContentHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);

        switch (viewType) {
            case ADS_VIEW_TYPE:
                final e_item item2 = (e_item) items.get(position);

                eventHolder = (EventHolder) holder;
                eventHolder.title.setText(item2.getTitle());
                eventHolder.date.setText(item2.getEventstartdate() + " ~ " + item2.getEventenddate());

                final String image;
                if (item2.getFirstiamge() != null) {
                    image = item2.getFirstiamge();
                    Glide.with(context)
                            .load(item2.getFirstiamge())
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(new ViewTarget<LinearLayout, GlideDrawable>(eventHolder.background) {
                                @Override
                                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                    LinearLayout v = this.view;
                                    v.setBackground(resource);
                                }
                            });
                } else {
                    image = item2.getFirstimage2();
                    Glide.with(context)
                            .load(item2.getFirstimage2())
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(new ViewTarget<LinearLayout, GlideDrawable>(eventHolder.background) {
                                @Override
                                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                    LinearLayout v = this.view;
                                    v.setBackground(resource);
                                }
                            });
                }

                eventHolder.background.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, EventInfoActivity.class);
                        intent.putExtra("conid", item2.getContentid());
                        intent.putExtra("image", image);
                        intent.putExtra("title", item2.getTitle());
                        intent.putExtra("mapx", item2.getMapx());
                        intent.putExtra("mapy", item2.getMapy());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });
                break;
            case CONTENT_VIEW_TYPE:
            default:

                contentHolder = (ContentHolder) holder;
                final board item1 = (board) items.get(position);

                getTag();
                ((ContentHolder) holder).title.setText(item1.getTitle());

                if (item1.getImgcount() > 1) { // 사진이 2개이상일 경우
                    contentHolder.imageCount.setText("+" + String.valueOf(item1.getImgcount() - 1));
                }

                contentHolder.commenttext.setText(String.valueOf(item1.getComment()));


                if (item1.getVallike() > 0) {
                    contentHolder.likeimg.setBackgroundResource(R.drawable.main_heart2_icon_60x53);
                }
                contentHolder.liketext.setText(String.valueOf(item1.getLike()));
                contentHolder.datetext.setText(String.valueOf(item1.getDate()));

                Glide.with(context)
                        .load(item1.getImage())
                        .thumbnail(0.3f)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(contentHolder.imageView);


                Glide.with(context)
                        .load(tagImage[item1.getBoardtag()])
                        .thumbnail(0.3f)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(contentHolder.tag);

                contentHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, BoardInfoActivity.class);
                        intent.putExtra("bid", String.valueOf(item1.getId()));
                        context.startActivity(intent);
                    }
                });
                break;

        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ContentHolder extends RecyclerView.ViewHolder {

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
        @BindView(R.id.home_tag)
        ImageView tag;
        @BindView(R.id.home_cardview)
        CardView view;

        public ContentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class EventHolder extends RecyclerView.ViewHolder {
        @Nullable
        @BindView(R.id.b_event_title)
        TextView title;
        @Nullable
        @BindView(R.id.b_event_date)
        TextView date;
        @Nullable
        @BindView(R.id.b_event_background)
        LinearLayout background;

        public EventHolder(View itemView) {
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
