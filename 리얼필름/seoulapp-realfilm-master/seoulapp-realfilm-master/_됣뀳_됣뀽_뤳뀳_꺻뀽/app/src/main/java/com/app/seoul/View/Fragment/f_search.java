package com.app.seoul.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.seoul.R;
import com.app.seoul.View.Activity.SearchActivity;
import com.app.seoul.View.Activity.Top5Activity;
import com.app.seoul.View.Activity.TopTenActivity;
import com.app.seoul.View.Activity.TopjongActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ihoyong on 2017. 9. 15..
 */

public class f_search extends android.support.v4.app.Fragment {

    @BindView(R.id.search_a)
    ImageView a;
    @BindView(R.id.search_b)
    ImageView b;
    @BindView(R.id.search_c)
    ImageView c;

    @OnClick({R.id.search_lin, R.id.search_top10, R.id.search_viewlin, R.id.top5}) void click(View v){
        switch (v.getId()){
            case R.id.search_lin:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;

            case R.id.search_top10:
                Intent top10 = new Intent(getActivity(), TopTenActivity.class);
                startActivity(top10);
                break;
            case R.id.search_viewlin:
                Intent top3 = new Intent(getActivity(), TopjongActivity.class);
                startActivity(top3);

                break;

            case R.id.top5:
                Intent top5 = new Intent(getActivity(), Top5Activity.class);
                startActivity(top5);
                break;
        }
    }

    public f_search() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, v);

        initData();

        return v;
    }

    private void initData() {

        Glide.with(getActivity())
                .load(R.drawable.aa)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(a);

        Glide.with(getActivity())
                .load(R.drawable.bb)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(b);

        Glide.with(getActivity())
                .load(R.drawable.cc)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(c);
    }


}
