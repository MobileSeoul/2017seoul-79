package com.app.seoul.View.Fragment.ChileFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.app.seoul.Model.up_cc;
import com.app.seoul.R;
import com.app.seoul.View.Adapter.up_c_adapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ihoyong on 2017. 10. 27..
 */

public class up_c extends Fragment {

    private RetrofitApi api;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<up_cc> item;
    private up_c_adapter adapter;

    @BindView(R.id.up_c_recyclerview)
    RecyclerView recyclerView;

    public up_c(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.up_c, container, false);
        ButterKnife.bind(this, v);

        loadData();

        return v;
    }

    private void loadData(){
        api = RetrofitClient.getRetrofit().create(RetrofitApi.class);

        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.scrollToPosition(0);


        Call<Value> call = api.up_c(Data.upid);

        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {

                item = response.body().getUp_c();
                adapter = new up_c_adapter(item, getActivity());
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
