package com.app.seoul.View.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.app.seoul.Api.RetrofitApi;
import com.app.seoul.Api.RetrofitClient;
import com.app.seoul.Model.Data;
import com.app.seoul.Model.Value;
import com.app.seoul.Model.up_bb;
import com.app.seoul.R;
import com.app.seoul.View.Adapter.up_b_adapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopjongActivity extends AppCompatActivity {

    private RetrofitApi api;
    private RecyclerView.LayoutManager layoutManager;
    private List<up_bb> item = new ArrayList<>();
    private up_b_adapter adapter;


    @BindView(R.id.topjong)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topjong);
        ButterKnife.bind(this);
        data();
    }

    private void data() {

        api = RetrofitClient.getRetrofit().create(RetrofitApi.class);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.scrollToPosition(0);

        getData();
    }

    private void getData() {
        Call<Value> call = api.top3(Data.userID);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()) {

                    item = response.body().getUp_b();
                    adapter = new up_b_adapter(item, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                t.printStackTrace();
                call.cancel();
            }
        });
    }
}
