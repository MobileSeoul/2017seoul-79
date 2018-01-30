package com.app.seoul.View.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.seoul.Api.RetrofitApi;
import com.app.seoul.Api.RetrofitClient;
import com.app.seoul.Model.Data;
import com.app.seoul.Model.Value;
import com.app.seoul.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private RetrofitApi api;
    private InputMethodManager inputMethodManager;

    @BindView(R.id.login_id)
    EditText login_id;
    @BindView(R.id.login_pw)
    EditText login_pw;
    @BindView(R.id.login_progress)
    ProgressBar progressBar;
    @BindView(R.id.login_hide)
    RelativeLayout lin;
    @BindView(R.id.login_logo)
    ImageView logo;

    @OnClick({R.id.login_login, R.id.login_hide, R.id.login_reg})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_login:
                checkUser();
                break;
            case R.id.login_hide:
                inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(login_id.getWindowToken(), 0);
                inputMethodManager.hideSoftInputFromWindow(login_pw.getWindowToken(), 0);
                break;
            case R.id.login_reg:
                Intent reg = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(reg);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        setBackgroundImage();
        checkPermission();
    }

    private void setBackgroundImage() {

        Glide.with(this)
                .load(R.drawable.login_background_1920x1080)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new ViewTarget<RelativeLayout, GlideDrawable>(lin) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        RelativeLayout v = this.view;
                        v.setBackground(resource);
                    }
                });

        Glide.with(this)
                .load(R.drawable.logo2)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(logo);

    }

    private void checkUser() {   // 회원가입 체크

        final String id = login_id.getText().toString(), pw = login_pw.getText().toString();

        if (id.equals("") || pw.equals("")) {
            Toast.makeText(getApplicationContext(), R.string.toast_login_empty, Toast.LENGTH_SHORT).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            api = RetrofitClient.getRetrofit().create(RetrofitApi.class);

            Call<Value> call = api.login(id, pw);
            call.enqueue(new Callback<Value>() {
                @Override
                public void onResponse(Call<Value> call, Response<Value> response) {
                    String value = response.body().getValue();
                    String message = response.body().getMessage();
                    String responseid = response.body().getId();
                    progressBar.setVisibility(View.GONE);

                    if (value == "0") {   // 인증 실패
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    } else {  // 성공
                        Data.userID = responseid;
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }

                @Override
                public void onFailure(Call<Value> call, Throwable t) {
                    t.printStackTrace();
                    call.cancel();
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    private void checkPermission() {
        PermissionListener camerapermission = new PermissionListener() {
            @Override
            public void onPermissionGranted() {


            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(getApplicationContext(), "permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();

            }
        };

        new TedPermission(getApplicationContext())
                .setPermissionListener(camerapermission)
                .setDeniedMessage("권한 설정 동의를 안하신다면\\n\\이곳에서 설정해 주세요. [설정] > [권한]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();

    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
