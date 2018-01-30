package com.app.seoul.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.app.seoul.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageInfoActivity extends AppCompatActivity {

    private int count = 0;

    @BindView(R.id.image_info_image)
    ImageView image;
    @BindView(R.id.image_info_back)
    ImageButton back;

    @OnClick({R.id.image_info_back, R.id.image_info_lin})
    void c(View v) {

        switch (v.getId()) {
            case R.id.image_info_back:
                if (count == 1) {
                    this.finish();
                }
                break;

            case R.id.image_info_lin:
              visible();
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_info);
        ButterKnife.bind(this);

        setImage();
    }

    private void visible(){

        if (count == 0) {
            back.setVisibility(View.VISIBLE);
            count++;
        } else {
            back.setVisibility(View.GONE);
            count--;
        }
    }

    private void setImage() {

        Intent intent = getIntent();
        String ima = intent.getStringExtra("image");

        Glide.with(this)
                .load(ima)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(image);
    }
}
