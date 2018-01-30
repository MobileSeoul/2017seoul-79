package com.app.seoul.View.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.seoul.Api.RetrofitApi;
import com.app.seoul.Api.RetrofitClient;
import com.app.seoul.Api.RetrofitEventClient;
import com.app.seoul.Model.Data;
import com.app.seoul.Model.Event.e_item;
import com.app.seoul.Model.Event.e_val;
import com.app.seoul.Model.Value;
import com.app.seoul.Model.board;
import com.app.seoul.R;
import com.app.seoul.View.Adapter.board_adapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class f_home extends android.support.v4.app.Fragment {

    private RetrofitApi api;
    private RetrofitApi ap;
    private RecyclerView.LayoutManager mLayoutManager;
    private board_adapter adapter;
    private List<board> item = new ArrayList<>();
    private List<e_item> e_item = new ArrayList<>();
    private List<Object> items = new ArrayList<>();
    private boolean check = false;
    private int VALCOUNT = 0;
    private final String cat2 = "A0207";
    private final String cat3 = "A02070200";

    public f_home() {
    }

    @BindView(R.id.home_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.home_swipe)
    SwipeRefreshLayout swipe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, v);

        loadData();
        swipe();

        return v;
    }

    private void loadData() {

        api = RetrofitClient.getRetrofit().create(RetrofitApi.class);
        ap = RetrofitEventClient.getRetrofit().create(RetrofitApi.class);

        if (items != null && items.size() > 0) {

            item.clear();
            items.clear();
            e_item.clear();
        }

        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.scrollToPosition(0);

        Call<Value> call = api.board(Data.userID);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {

                item = response.body().getBoard();
                for (int i = 0; i < item.size(); i++) {
                    items.add(item.get(i));

                }

                check = true;
                if (check == true) {
                    initEvent();
                    check = false;
                }

            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                t.printStackTrace();
                call.cancel();
                swipe.setRefreshing(false);
            }
        });

    }

    private void initEvent() {

        Call<e_val> call = ap.event(cat2, cat3);
        call.enqueue(new Callback<e_val>() {
            @Override
            public void onResponse(Call<e_val> call, Response<e_val> response) {
                if (response.isSuccessful()) {


                    e_item = response.body().getResponse().getBody().getItems().getItem();

                    int j = 0;
                    for (int i = 2; i <= items.size(); i += 5) {

                        if (j >= items.size()) {
                            j = 0;
                            items.add(i, e_item.get(j));

                        } else {
                            items.add(i, e_item.get(j));
                            j++;
                        }


                    }

                    VALCOUNT++;
                    if (VALCOUNT == 1) {

                        adapter = new board_adapter(items, getActivity());
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        swipe.setRefreshing(false);
                        VALCOUNT--;
                    }

                } else

                {

                    swipe.setRefreshing(false);
                }
            }


            @Override
            public void onFailure(Call<e_val> call, Throwable t) {
                t.printStackTrace();
                call.cancel();
                swipe.setRefreshing(false);

            }
        });


    }

    private void swipe() {
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        swipe();
    }
}
