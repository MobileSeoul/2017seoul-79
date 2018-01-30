package com.app.seoul.View.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.seoul.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserprivitActivity extends AppCompatActivity {

    @OnClick(R.id.up_back)void Click(){
        this.finish();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprivit);
        ButterKnife.bind(this);
    }
}
