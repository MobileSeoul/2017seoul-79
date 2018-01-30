package com.app.seoul.View.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.seoul.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TermsActivity extends AppCompatActivity {

    @OnClick(R.id.terms_back) void Click(){
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        ButterKnife.bind(this);

    }
}
