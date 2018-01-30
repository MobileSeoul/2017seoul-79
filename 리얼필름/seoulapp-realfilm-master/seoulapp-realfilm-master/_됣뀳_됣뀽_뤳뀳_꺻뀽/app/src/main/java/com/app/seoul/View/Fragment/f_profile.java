package com.app.seoul.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.seoul.Api.RetrofitApi;
import com.app.seoul.Api.RetrofitClient;
import com.app.seoul.Model.Data;
import com.app.seoul.Model.profile;
import com.app.seoul.R;
import com.app.seoul.View.Activity.ProfileSettingActivity;
import com.app.seoul.View.Adapter.Profile_FragmentAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ihoyong on 2017. 9. 15..
 */

public class f_profile extends android.support.v4.app.Fragment {

    private Profile_FragmentAdapter adapter;
    private BitmapPool bitmapPool;
    private RetrofitApi api;
    private String profile, back, sname;

    @BindView(R.id.profile_tablayout)
    TabLayout tabLayout;
    @BindView(R.id.profile_viewPager)
    ViewPager viewPager;
    @BindView(R.id.profile_profileimage)
    de.hdodenhof.circleimageview.CircleImageView profileimage;
    @BindView(R.id.profile_backgroundimage)
    ImageView backgroundimage;
    @BindView(R.id.profile_name)
    TextView name;
    @BindView(R.id.profile_mycount)
    TextView myflimCount;
    @BindView(R.id.profile_fallowing_count)
    TextView fallowing_count;
    @BindView(R.id.profile_fallow_count)
    TextView fallow_count;
    @BindView(R.id.profile_setting)
    ImageButton settingbtn;

    @OnClick(R.id.profile_setting)
    void Click() {
        Intent intent = new Intent(getActivity(), ProfileSettingActivity.class);
        intent.putExtra("profile", profile);
        intent.putExtra("name", sname);
        intent.putExtra("back", back);
        getActivity().startActivity(intent);
    }

    public f_profile() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, v);

        bitmapPool = Glide.get(getActivity()).getBitmapPool();
        TabView();
        getData();
        return v;
    }

    /* 뷰페이져와 텝뷰 */
    private void TabView() {


        tabLayout.addTab(tabLayout.newTab().setText("내 필름"));
        tabLayout.addTab(tabLayout.newTab().setText("스크랩"));
        tabLayout.addTab(tabLayout.newTab().setText("이벤트"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        adapter = new Profile_FragmentAdapter(getChildFragmentManager(), tabLayout.getTabCount());
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

    private void getData() {

        api = RetrofitClient.getRetrofit().create(RetrofitApi.class);

        Call<profile> call = api.profile(Data.userID);
        call.enqueue(new Callback<profile>() {
            @Override
            public void onResponse(Call<profile> call, Response<profile> response) {
                if (response.isSuccessful()) {

                    sname = response.body().getName();
                    profile = response.body().getProfile();
                    back = response.body().getBackg();

                    name.setText(sname);
                    myflimCount.setText(response.body().getMycount());
                    fallow_count.setText(response.body().getFalla());
                    fallowing_count.setText(response.body().getFallowing());

                    Glide.with(getActivity())
                            .load(response.body().getProfile())
                            .error(R.drawable.mypage_profile_default_250x250)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(profileimage);

                    Glide.with(getActivity())
                            .load(back)
                            .error(R.drawable.mypage_cover_default_1080x600)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(backgroundimage);

                }
            }

            @Override
            public void onFailure(Call<profile> call, Throwable t) {
                t.printStackTrace();
                call.cancel();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        getData();
    }
}
