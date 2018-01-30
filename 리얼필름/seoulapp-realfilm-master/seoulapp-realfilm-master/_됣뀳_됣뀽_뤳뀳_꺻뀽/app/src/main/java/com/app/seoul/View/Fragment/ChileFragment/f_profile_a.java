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
import com.app.seoul.Model.pro_a;
import com.app.seoul.R;
import com.app.seoul.View.Adapter.profile_a_adapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class f_profile_a extends android.support.v4.app.Fragment {

    private RetrofitApi api;
    private RecyclerView.LayoutManager mLayoutManager;
    private profile_a_adapter adapter;
    private List<pro_a> item = new ArrayList<>();

    @Nullable
    @BindView(R.id.profile_myfilm)
    RecyclerView recyclerView;

    public f_profile_a() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.f_profile_a, container, false);
        ButterKnife.bind(this, v);

        loadData();

        return v;
    }

    private void loadData() {

        api = RetrofitClient.getRetrofit().create(RetrofitApi.class);

        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.scrollToPosition(0);


        Call<Value> call = api.pro_a(Data.userID, 0);

        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {

                item = response.body().getPro_a();
                adapter = new profile_a_adapter(item, getActivity());
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
