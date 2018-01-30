package com.app.seoul.View.Fragment.ChileFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

/**
 * Created by ihoyong on 2017. 10. 27..
 */

public class up_b extends android.support.v4.app.Fragment {

    private RetrofitApi api;
    private List<up_bb> item;
    private up_b_adapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @BindView(R.id.up_b_recyclerview)
    RecyclerView recyclerView;

    public up_b() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.up_b, container, false);
        ButterKnife.bind(this, v);

        loadData();

        return v;
    }

    private void loadData() {

        api = RetrofitClient.getRetrofit().create(RetrofitApi.class);
        item = new ArrayList<>();

        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.scrollToPosition(0);

        Call<Value> call = api.up_b(Data.upid);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {

                item = response.body().getUp_b();

                adapter = new up_b_adapter(item, getActivity());
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
