package com.app.seoul.View.Activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.seoul.Api.RetrofitApi;
import com.app.seoul.Api.RetrofitEventClient;
import com.app.seoul.Model.Event.e_item;
import com.app.seoul.Model.Event.e_val;
import com.app.seoul.R;
import com.app.seoul.View.Adapter.event_adapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventActivity extends AppCompatActivity {

    private RetrofitApi api;
    private List<e_item> item;
    private RecyclerView.LayoutManager manager;
    private event_adapter adapter;
    private RequestManager requestManager;

    @BindView(R.id.event_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.event_progress)
    ProgressBar progressBar;
    @BindView(R.id.ev_b)
    TextView b;
    @BindView(R.id.ev_c)
    TextView c;
    @BindView(R.id.ev_d)
    TextView d;

    @OnClick({R.id.event_back, R.id.ev_b, R.id.ev_c, R.id.ev_d})
    void click(View v) {
        switch (v.getId()) {
            case R.id.event_back:
                this.finish();
                break;
            case R.id.ev_b:
                click(b, 1);
                break;
            case R.id.ev_c:
                click(c, 2);
                break;
            case R.id.ev_d:
                click(d, 3);
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        requestManager = Glide.with(getApplicationContext());

        manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.scrollToPosition(0);

        api = RetrofitEventClient.getRetrofit().create(RetrofitApi.class);
        loadData("A0207", "A02070200");


    }

    private void loadData(final String cat2, final String cat3) {

        Call<e_val> call = api.event(cat2, cat3);
        call.enqueue(new Callback<e_val>() {
            @Override
            public void onResponse(Call<e_val> call, Response<e_val> response) {
                if (response.isSuccessful()) {

                    item = new ArrayList<>();
                    item = response.body().getResponse().getBody().getItems().getItem();
                    adapter = new event_adapter(item, getApplicationContext(), requestManager);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
            }


            @Override
            public void onFailure(Call<e_val> call, Throwable t) {
                t.printStackTrace();
                call.cancel();
            }
        });

    }

    private void click(TextView tv, int position) {

        switch (position) {
            case 1:
                tv.setTextColor(getResources().getColor(R.color.textColor));
                tv.setTypeface(null, Typeface.BOLD);
                c.setTextColor(getResources().getColor(R.color.regular));
                c.setTypeface(null, Typeface.NORMAL);
                d.setTextColor(getResources().getColor(R.color.regular));
                d.setTypeface(null, Typeface.NORMAL);
                loadData("A0207", "A02070200");
                break;
            case 2:
                tv.setTextColor(getResources().getColor(R.color.textColor));
                tv.setTypeface(null, Typeface.BOLD);
                b.setTextColor(getResources().getColor(R.color.regular));
                b.setTypeface(null, Typeface.NORMAL);
                d.setTextColor(getResources().getColor(R.color.regular));
                d.setTypeface(null, Typeface.NORMAL);
                loadData("A0208", "A02080500");
                break;
            case 3:
                tv.setTextColor(getResources().getColor(R.color.textColor));
                tv.setTypeface(null, Typeface.BOLD);
                b.setTextColor(getResources().getColor(R.color.regular));
                b.setTypeface(null, Typeface.NORMAL);
                c.setTextColor(getResources().getColor(R.color.regular));
                c.setTypeface(null, Typeface.NORMAL);
                loadData("A0208", "A02080600");
                break;
        }

    }


}
