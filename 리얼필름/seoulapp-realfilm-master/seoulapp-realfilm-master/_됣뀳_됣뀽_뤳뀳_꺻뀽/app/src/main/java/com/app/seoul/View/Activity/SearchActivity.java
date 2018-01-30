package com.app.seoul.View.Activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.seoul.Api.RetrofitApi;
import com.app.seoul.Api.RetrofitClient;
import com.app.seoul.Model.Data;
import com.app.seoul.Model.Value;
import com.app.seoul.Model.search_a;
import com.app.seoul.Model.search_c;
import com.app.seoul.Model.up_bb;
import com.app.seoul.R;
import com.app.seoul.View.Adapter.se_a_adapter;
import com.app.seoul.View.Adapter.se_c_adapter;
import com.app.seoul.View.Adapter.up_b_adapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private se_a_adapter adapter;
    private up_b_adapter badapter;
    private se_c_adapter cadapter;

    private RecyclerView.LayoutManager layoutManager;
    private List<search_a> item = new ArrayList<>();
    private List<up_bb> bitem = new ArrayList<>();
    private List<search_c> citem = new ArrayList<>();

    private RetrofitApi api;
    private String data;
    private int ck = 0;

    @BindView(R.id.search_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.search_a)
    TextView a;
    @BindView(R.id.search_b)
    TextView b;
    @BindView(R.id.search_c)
    TextView c;
    @BindView(R.id.se_a_edit)
    EditText e;
    @BindView(R.id.search_vt)
    TextView mess;

    @OnTextChanged(R.id.se_a_edit)
    void ch(CharSequence cs) {
        if (cs.length() > 0) {

            mess.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            data = cs.toString();
            initData(data, ck);
        } else {

            mess.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

        }
    }

    @OnClick({R.id.search_back, R.id.search_a, R.id.search_b, R.id.search_c})
    void cl(View v) {
        switch (v.getId()) {
            case R.id.search_back:
                this.finish();
                break;

            case R.id.search_a:
                btnPress(a, 0);
                break;
            case R.id.search_b:
                btnPress(b, 1);
                break;
            case R.id.search_c:
                btnPress(c, 2);
                break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        api = RetrofitClient.getRetrofit().create(RetrofitApi.class);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.scrollToPosition(0);

    }

    private void btnPress(TextView tv, int p) {
        switch (p) {
            case 0:
                ck = p;
                e.setText("");
                tv.setTextColor(getResources().getColor(R.color.textColor));
                tv.setTypeface(null, Typeface.BOLD);
                b.setTextColor(getResources().getColor(R.color.regular));
                b.setTypeface(null, Typeface.NORMAL);
                c.setTextColor(getResources().getColor(R.color.regular));
                c.setTypeface(null, Typeface.NORMAL);
                break;

            case 1:
                ck = p;
                e.setText("");
                tv.setTextColor(getResources().getColor(R.color.textColor));
                tv.setTypeface(null, Typeface.BOLD);
                a.setTextColor(getResources().getColor(R.color.regular));
                a.setTypeface(null, Typeface.NORMAL);
                c.setTextColor(getResources().getColor(R.color.regular));
                c.setTypeface(null, Typeface.NORMAL);
                break;

            case 2:
                ck = p;
                e.setText("");
                tv.setTextColor(getResources().getColor(R.color.textColor));
                tv.setTypeface(null, Typeface.BOLD);
                b.setTextColor(getResources().getColor(R.color.regular));
                b.setTypeface(null, Typeface.NORMAL);
                a.setTextColor(getResources().getColor(R.color.regular));
                a.setTypeface(null, Typeface.NORMAL);
                break;
        }
    }

    private void initData(String data, int ck) {

        Call<Value> call = api.search_a(data, ck, Data.userID);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()) {
                    String value = response.body().getValue();

                    if (value.equals("0")) {
                        item = response.body().getSearch_a();
                        adapter = new se_a_adapter(item, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else if (value.equals("1")) {
                        bitem = response.body().getUp_b();
                        badapter = new up_b_adapter(bitem, getApplicationContext());
                        recyclerView.setAdapter(badapter);
                        badapter.notifyDataSetChanged();
                    }else{
                        citem = response.body().getSearch_c();
                        cadapter = new se_c_adapter(citem, getApplicationContext());
                        recyclerView.setAdapter(cadapter);
                        cadapter.notifyDataSetChanged();
                    }

                } else {


                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                t.printStackTrace();
                call.cancel();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
