package com.app.seoul.View.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.app.seoul.Api.RetrofitApi;
import com.app.seoul.Api.RetrofitClient;
import com.app.seoul.Model.Notice;
import com.app.seoul.Model.Value;
import com.app.seoul.R;
import com.app.seoul.View.Adapter.notice_adapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeActivity extends AppCompatActivity {

    private RetrofitApi api;
    private RecyclerView.LayoutManager mLayoutManager;
    private notice_adapter adapter;
    private List<Notice> item;

    @BindView(R.id.notice_recyclerview)
    RecyclerView recyclerView;

    @OnClick({R.id.notice_back})
    void Click(View v) {
        switch (v.getId()) {
            case R.id.notice_back:
                this.finish();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        ButterKnife.bind(this);
        loadData();
    }

    private void loadData() {

        api = RetrofitClient.getRetrofit().create(RetrofitApi.class);
        item = new ArrayList<>();

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.scrollToPosition(0);

        Call<Value> call = api.notice();
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {

                item = response.body().getNotice();

                adapter = new notice_adapter(item, getApplicationContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                t.printStackTrace();
                call.cancel();
            }
        });

    }
}
