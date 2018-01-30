package com.app.seoul.View.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.seoul.Api.RetrofitApi;
import com.app.seoul.Api.RetrofitClient;
import com.app.seoul.Model.Value;
import com.app.seoul.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ihoyong on 2017. 10. 21..
 */

public class Register_c_Activity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private String id, pw, date;
    private RetrofitApi api;
    private int sexual = 0;

    @BindView(R.id.reg_c_lin)
    LinearLayout lin;
    @BindView(R.id.reg_c_step)
    ImageView step;
    @BindView(R.id.reg_c_logo)
    ImageView logo;
    @BindView(R.id.reg_c_name)
    EditText name;
    @BindView(R.id.reg_c_man)
    ImageView man;
    @BindView(R.id.reg_c_woman)
    ImageView woman;
    @BindView(R.id.reg_c_date_text)
    TextView date_text;

    @OnClick({R.id.reg_c_woman, R.id.reg_c_man, R.id.reg_c_date, R.id.register_c_submit})
    void click(View v) {
        switch (v.getId()) {
            case R.id.reg_c_woman:
                pressedButton(1);
                break;
            case R.id.reg_c_man:
                pressedButton(0);
                break;
            case R.id.reg_c_date:
                date();
                break;
            case R.id.register_c_submit:
                sendData();
                break;

        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_c);
        ButterKnife.bind(this);

        setBackgroundImage();
        getIntentData();
    }

    private void getIntentData() {   // 전 액티비티로부터 데이터 받기
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        pw = intent.getStringExtra("pw");

    }

    private void setBackgroundImage() { // 배경 이미지

        Glide.with(this)    // 배경 이미지
                .load(R.drawable.login_backgroundopacity50_640x360)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new ViewTarget<LinearLayout, GlideDrawable>(lin) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        LinearLayout v = this.view;
                        v.setBackground(resource);
                    }
                });

        Glide.with(this)    // 로고
                .load(R.drawable.logo2)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(logo);

        Glide.with(this)
                .load(R.drawable.login_step3_icon_266x30)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(step);

        Glide.with(this)
                .load(R.drawable.login_manoff_btn_103x43)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(man);
        Glide.with(this)
                .load(R.drawable.login_womanoff_btn_103x43)
                .into(woman);
    }

    private void pressedButton(int position) {

        if (position == 0) {

            Glide.with(this)
                    .load(R.drawable.login_manon_btn_103x43)
                    .into(man);
            Glide.with(this)
                    .load(R.drawable.login_womanoff_btn_103x43)
                    .into(woman);
            sexual = 0;

        } else if (position == 1) {

            Glide.with(this)
                    .load(R.drawable.login_manoff_btn_103x43)
                    .into(man);
            Glide.with(this)
                    .load(R.drawable.login_womanon_btn_103x43)
                    .into(woman);

            sexual = 1;
        }
    }

    private void date() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(Register_c_Activity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        date_text.setText(year + "  "
                                + (monthOfYear + 1) + "  " + dayOfMonth);
                        date = String.valueOf(year + (monthOfYear + 1) + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void sendData() {
        api = RetrofitClient.getRetrofit().create(RetrofitApi.class);
        Call<Value> call = api.register(id, pw, name.getText().toString(), date, sexual);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                if (value.equals("1")) {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                t.printStackTrace();
                call.cancel();
            }
        });

    }
}
