package com.app.seoul.View.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.seoul.Api.RetrofitApi;
import com.app.seoul.Api.RetrofitClient;
import com.app.seoul.Model.Data;
import com.app.seoul.Model.Value;
import com.app.seoul.Model.userprofile;
import com.app.seoul.R;
import com.app.seoul.View.Adapter.up_fragmentadapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity {

    private RetrofitApi api;
    private String uid;
    private int flcheck;
    private up_fragmentadapter adapter;

    @BindView(R.id.uprofile_background)
    ImageView background_i;
    @BindView(R.id.uprofile_profile)
    ImageView profile_i;
    @BindView(R.id.uprofile_status)
    TextView status_t;
    @BindView(R.id.uprofile_name)
    TextView name_t;
    @BindView(R.id.uprofile_filmc)
    TextView filmc_t;
    @BindView(R.id.uprofile_fallowing)
    TextView fallowing_t;
    @BindView(R.id.uprofile_falla)
    TextView falla_t;
    @BindView(R.id.uprofile_fallowbtn)
    ImageView fbtn;

    @BindView(R.id.uprofile_viewpager)
    ViewPager viewPager;
    @BindView(R.id.uprofile_tablayout)
    TabLayout tabLayout;

    @OnClick({R.id.uprofile_fallowbtn, R.id.uprofile_back})
    void click(View v) {

        switch (v.getId()) {

            case R.id.uprofile_fallowbtn:
                if (flcheck == 0) {
                    fallow(flcheck);
                    flcheck++;
                } else {
                    fallow(flcheck);
                    flcheck--;
                }
                break;
            case R.id.uprofile_back:
                returndata();
                break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);

        getData();

    }

    private void getData() {

        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");

        if (intent.getStringExtra("vali") != null) {
            String c = intent.getStringExtra("vali");
            flcheck = Integer.parseInt(c);
        }
        api = RetrofitClient.getRetrofit().create(RetrofitApi.class);

        Call<userprofile> call = api.uprofile(uid, Data.userID);
        call.enqueue(new Callback<userprofile>() {
            @Override
            public void onResponse(Call<userprofile> call, Response<userprofile> response) {
                if (response.isSuccessful()) {

                    Glide.with(getApplicationContext())
                            .load(response.body().getBackground())
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .error(R.drawable.mypage_cover_default_1080x600)
                            .into(background_i);

                    Glide.with(getApplicationContext())
                            .load(response.body().getProfile())
                            .error(R.drawable.mypage_profile_default_250x250)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(profile_i);

                    status_t.setText(response.body().getStatus());
                    name_t.setText(response.body().getName());
                    filmc_t.setText(response.body().getFilmc());
                    fallowing_t.setText(response.body().getFallowing());
                    falla_t.setText(response.body().getFalla());

                    if (response.body().getFallowbtn().equals("1")) {
                        fbtn.setBackgroundResource(R.drawable.userpage_fallowing_btn_70x27);
                    } else {
                        fbtn.setBackgroundResource(R.drawable.ccc);
                    }
                    tab();

                }
            }

            @Override
            public void onFailure(Call<userprofile> call, Throwable t) {
                t.printStackTrace();
                call.cancel();

            }
        });

    }

    private void tab() {
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.up_a));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.up_b));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.up_c));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        adapter = new up_fragmentadapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab LayoutTab) {

                viewPager.setCurrentItem(LayoutTab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab LayoutTab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab LayoutTab) {

            }
        });

    }

    private void fallow(int vali) {
        Call<Value> call = api.ufal(Data.userID, uid, vali);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()) {
                    String value = response.body().getValue();

                    if (value.equals("0")) {
                        fbtn.setBackgroundResource(R.drawable.userpage_fallowing_btn_70x27);
                    } else {
                        fbtn.setBackgroundResource(R.drawable.ccc);
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

    private void returndata() {
        Intent intent = new Intent();
        intent.putExtra("flcheck", String.valueOf(flcheck));
        setResult(Activity.RESULT_OK, intent);
        this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        returndata();
    }
}
