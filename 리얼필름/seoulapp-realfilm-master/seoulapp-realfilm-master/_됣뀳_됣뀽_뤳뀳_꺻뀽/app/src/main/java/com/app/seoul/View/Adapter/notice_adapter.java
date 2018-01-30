package com.app.seoul.View.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.seoul.Model.Notice;
import com.app.seoul.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class notice_adapter extends RecyclerView.Adapter<notice_adapter.ViewHolder> {

    private Context context;
    private List<Notice> item;
    private int[] icon = {R.drawable.settings_more_btn_20x13, R.drawable.settings_less_btn_20x13};
    private boolean[] open;

    public notice_adapter(List<Notice> item, Context context) {

        super();
        this.item = item;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Notice item1 = item.get(position);

        open = new boolean[item.size()];

        holder.title.setText(item1.getTitle());
        holder.content.setText(item1.getContent());
        holder.date.setText("업데이트   |   " + item1.getDate());

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noticeSlideAnimation(open[position], position, holder.lin, holder.btn);
            }
        });


    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.notice_title)
        TextView title;
        @BindView(R.id.notice_content)
        TextView content;
        @BindView(R.id.notice_date)
        TextView date;
        @BindView(R.id.notice_lin)
        LinearLayout lin;
        @BindView(R.id.notice_btn)
        ImageButton btn;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void noticeSlideAnimation(boolean check, int position, final LinearLayout lin, ImageView btn) {

        if (check == false) {
            Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);

            slideDown.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            lin.setVisibility(View.VISIBLE);
            lin.startAnimation(slideDown);
            slideDown.setFillAfter(true);
            btn.setBackgroundResource(icon[1]);

            open[position] = true;

        } else {
            final Animation slideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up);

            slideUp.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    lin.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            lin.startAnimation(slideUp);
            btn.setBackgroundResource(icon[0]);

            open[position] = false;
        }
    }


}
