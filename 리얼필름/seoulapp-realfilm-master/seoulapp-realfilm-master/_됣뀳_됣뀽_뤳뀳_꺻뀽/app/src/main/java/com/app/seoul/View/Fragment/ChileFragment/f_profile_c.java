package com.app.seoul.View.Fragment.ChileFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.seoul.R;

import butterknife.ButterKnife;

/**
 * Created by ihoyong on 2017. 9. 15..
 */

public class f_profile_c extends android.support.v4.app.Fragment {

    public f_profile_c() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.f_profile_c, container, false);
        ButterKnife.bind(this, v);

        return v;
    }
}
