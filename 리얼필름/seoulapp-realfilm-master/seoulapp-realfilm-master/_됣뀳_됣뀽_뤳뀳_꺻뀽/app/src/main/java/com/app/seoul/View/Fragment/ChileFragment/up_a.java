package com.app.seoul.View.Fragment.ChileFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.seoul.Api.RetrofitApi;
import com.app.seoul.Api.RetrofitClient;
import com.app.seoul.Model.Data;
import com.app.seoul.Model.Value;
import com.app.seoul.Model.up_aa;
import com.app.seoul.R;
import com.app.seoul.View.Adapter.up_a_adapter;

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

public class up_a extends android.support.v4.app.Fragment {

    private RetrofitApi api;
    private List<up_aa> item;
    private up_a_adapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @BindView(R.id.up_a_recyclerview)
    RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.up_a, container, false);
        ButterKnife.bind(this, v);

        loadData();

        return v;
    }

    private void loadData() {

        api = RetrofitClient.getRetrofit().create(RetrofitApi.class);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(mLayoutManager);

        item = new ArrayList<>();

        Call<Value> call = api.up_a(Data.upid);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()) {

                    item = response.body().getUp_a();
                    adapter = new up_a_adapter(item, getActivity());
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
