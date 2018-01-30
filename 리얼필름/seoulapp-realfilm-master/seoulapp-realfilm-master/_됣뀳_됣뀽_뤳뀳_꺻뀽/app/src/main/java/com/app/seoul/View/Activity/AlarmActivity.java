package com.app.seoul.View.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.seoul.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlarmActivity extends AppCompatActivity {

    @OnClick(R.id.a_back)
    void click() {
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);
    }
}
