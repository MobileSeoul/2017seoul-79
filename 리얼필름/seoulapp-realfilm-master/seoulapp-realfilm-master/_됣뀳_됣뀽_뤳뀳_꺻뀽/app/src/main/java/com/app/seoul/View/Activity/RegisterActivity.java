package com.app.seoul.View.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.seoul.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.register_a_check1)
    CheckBox box1;
    @BindView(R.id.register_a_check2)
    CheckBox box2;
    @BindView(R.id.reg_lin)
    LinearLayout lin;

    @OnClick({R.id.register_a_submit})
    void Click(View v) {
        switch (v.getId()) {
            case R.id.register_a_submit:
                validate();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setBackgroundImage();
    }


    private void validate() {

        if (box1.isChecked() == true && box2.isChecked() == true) {
            Intent intent = new Intent(this, Register_b_Activity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "약관을 동의해 주세요.", Toast.LENGTH_SHORT).show();
        }

    }

    private void setBackgroundImage() {
        Glide.with(this)
                .load(R.drawable.login_backgroundopacity50_640x360)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new ViewTarget<LinearLayout, GlideDrawable>(lin) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        LinearLayout v = this.view;
                        v.setBackground(resource);
                    }
                });
    }

}
