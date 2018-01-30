package com.app.seoul.View.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.seoul.Api.RetrofitApi;
import com.app.seoul.Api.RetrofitClient;
import com.app.seoul.Model.Data;
import com.app.seoul.Model.Value;
import com.app.seoul.Model.p_setting;
import com.app.seoul.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gun0912.tedbottompicker.TedBottomPicker;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileSettingActivity extends AppCompatActivity {

    private String profile, sname, back, pwlength;
    private RetrofitApi api;
    private String pwmen = "";
    private MultipartBody.Part pimage;
    private HashMap<String, RequestBody> map = new HashMap<>();
    private final int REQUEST_CODE_NAME = 0;

    @BindView(R.id.p_setting_profile)
    de.hdodenhof.circleimageview.CircleImageView profile_img;
    @BindView(R.id.p_setting_name)
    TextView name;
    @BindView(R.id.p_setting_status)
    EditText status;
    @BindView(R.id.p_setting_id)
    TextView id;
    @BindView(R.id.p_setting_pw)
    TextView pw;
    @BindView(R.id.p_setting_pbackg)
    ImageView p_background;


    @OnClick({R.id.p_setting_back, R.id.p_setting_status_del, R.id.p_setting_changepw, R.id.p_setting_changepimg,
            R.id.p_setting_backimage, R.id.p_setting_cname})
    void Click(View v) {
        switch (v.getId()) {
            case R.id.p_setting_back:
                this.finish();
                break;

            case R.id.p_setting_status_del:
                chstatus();
                break;

            case R.id.p_setting_changepw:
                Intent in = new Intent(this, PasswordChangeActivity.class);
                startActivity(in);
                break;
            case R.id.p_setting_changepimg:
                setImage(0);
                break;

            case R.id.p_setting_backimage:
                setImage(1);
                break;
            case R.id.p_setting_cname:
                Intent intent = new Intent(getApplicationContext(), ChangeNameActivity.class);
                intent.putExtra("name", sname);
                startActivityForResult(intent, REQUEST_CODE_NAME);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);
        ButterKnife.bind(this);
        init();
    }

    private void chstatus() {

        Call<Value> call = api.chstatus(Data.userID, status.getText().toString());
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()) {
                    String value = response.body().getValue();
                    String message = response.body().getMessage();

                    if (value.equals("1")) {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {

            }
        });

    }

    private void init() {

        api = RetrofitClient.getRetrofit().create(RetrofitApi.class);

        Intent intent = getIntent();
        profile = intent.getStringExtra("profile");
        sname = intent.getStringExtra("name");
        back = intent.getStringExtra("back");

        Glide.with(getApplicationContext())
                .load(profile)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(profile_img);

        Glide.with(getApplicationContext())
                .load(back)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(p_background);

        name.setText(sname);

        getData();

    }

    private void getData() {

        Call<p_setting> call = api.pset(Data.userID);
        call.enqueue(new Callback<p_setting>() {
            @Override
            public void onResponse(Call<p_setting> call, Response<p_setting> response) {
                if (response.isSuccessful()) {

                    status.setText(response.body().getStatus());
                    id.setText(response.body().getUid());
                    pwlength = response.body().getPw();
                    getpwlen(pwlength);

                }
            }

            @Override
            public void onFailure(Call<p_setting> call, Throwable t) {
                t.printStackTrace();
                call.cancel();
            }
        });

    }

    private void getpwlen(String c) {

        for (int i = 0; i < c.length(); i++) {
            pwmen += "*";
        }
        pw.setText(pwmen);
    }

    private void setImage(final int ck) {

        TedBottomPicker bottomSheetDialogFragment = new TedBottomPicker.Builder(this)
                .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(Uri uri) {

                        if (ck == 0) {
                            pimage = prepareFilePart(String.valueOf(uri.getPath()));
                            uploadProfile(0);
                        } else {
                            pimage = prepareBackground(String.valueOf(uri.getPath()));
                            uploadProfile(1);
                        }
                    }
                })
                .create();

        bottomSheetDialogFragment.show(getSupportFragmentManager());

    }

    private void uploadProfile(int ck) {

        map.put("uid", toRequestBody(Data.userID));
        if (ck == 0) {

            Call<Value> call = api.pimage(pimage, map);
            call.enqueue(new Callback<Value>() {
                @Override
                public void onResponse(Call<Value> call, Response<Value> response) {
                    if (response.isSuccessful()) {

                        String value = response.body().getValue();
                        String message = response.body().getMessage();

                        if (value.equals("1")) {

                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            String pimage = response.body().getPimage();
                            Glide.with(getApplicationContext())
                                    .load(pimage)
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .into(profile_img);

                        } else {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Value> call, Throwable t) {
                    t.printStackTrace();
                    call.cancel();
                }
            });

        } else {

            Call<Value> call = api.pback(pimage, map);
            call.enqueue(new Callback<Value>() {
                @Override
                public void onResponse(Call<Value> call, Response<Value> response) {
                    if (response.isSuccessful()) {

                        String value = response.body().getValue();
                        String message = response.body().getMessage();

                        if (value.equals("1")) {

                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            String pback = response.body().getPback();
                            Glide.with(getApplicationContext())
                                    .load(pback)
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .into(p_background);

                        } else {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        }
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

    @NonNull
    private MultipartBody.Part prepareFilePart(String uri) {

        File file = new File(uri);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        return MultipartBody.Part.createFormData("pimage", file.getName(), requestBody);
    }

    @NonNull
    private MultipartBody.Part prepareBackground(String uri) {

        File file = new File(uri);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        return MultipartBody.Part.createFormData("pback", file.getName(), requestBody);
    }

    public RequestBody toRequestBody(String value) {

        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        return requestBody;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_NAME && resultCode == RESULT_OK) {   // 지도
            String nam = data.getStringExtra("name");
            name.setText(nam);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
