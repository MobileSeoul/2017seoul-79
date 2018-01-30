package com.app.seoul.View.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.seoul.Api.RetrofitApi;
import com.app.seoul.Api.RetrofitClient;
import com.app.seoul.Model.Data;
import com.app.seoul.Model.Value;
import com.app.seoul.Model.comment;
import com.app.seoul.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ihoyong on 2017. 10. 24..
 */

public class comment_adapter extends RecyclerView.Adapter<comment_adapter.ViewHolder> {

    private Context context;
    private List<comment> item;

    public comment_adapter(List<comment> item, Context context) {
        this.item = item;
        this.context = context;
    }

    @Override
    public comment_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(comment_adapter.ViewHolder holder, final int position) {

        comment item1 = item.get(position);
        final String id = item1.getId();
        Glide.with(context)
                .load(item1.getProfile())
                .error(R.drawable.mypage_profile_default_250x250)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.profile);

        holder.name.setText(item1.getName());
        holder.date.setText(item1.getDate());
        holder.likecount.setText(item1.getLikecount());
        holder.content.setText(item1.getContent());

        if (item1.getCuid().equals(Data.userID)) {
            holder.delbtn.setVisibility(View.VISIBLE);

            holder.delbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("삭제하기");
                    builder.setMessage("정말 삭제하시겠습니까");
                    builder.setPositiveButton("예",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    RetrofitApi api = RetrofitClient.getRetrofit().create(RetrofitApi.class);
                                    Call<Value> call = api.commentdel(id);
                                    call.enqueue(new Callback<Value>() {
                                        @Override
                                        public void onResponse(Call<Value> call, Response<Value> response) {
                                            if (response.isSuccessful()) {
                                                String value = response.body().getValue();
                                                if (value.equals("1")) {
                                                    item.remove(position);
                                                    notifyDataSetChanged();
                                                } else {

                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Value> call, Throwable t) {
                                            t.printStackTrace();
                                            call.cancel();
                                        }
                                    });

                                }
                            });
                    builder.setNegativeButton("아니오",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    builder.show();


                }
            });

        } else {
            holder.delbtn.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.comment_profile)
        de.hdodenhof.circleimageview.CircleImageView profile;
        @BindView(R.id.comment_name)
        TextView name;
        @BindView(R.id.comment_date)
        TextView date;
        @BindView(R.id.comment_likebtn)
        TextView likebtn;
        @BindView(R.id.comment_likecount)
        TextView likecount;
        @BindView(R.id.comment_content)
        TextView content;
        @BindView(R.id.comment_del)
        TextView delbtn;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
