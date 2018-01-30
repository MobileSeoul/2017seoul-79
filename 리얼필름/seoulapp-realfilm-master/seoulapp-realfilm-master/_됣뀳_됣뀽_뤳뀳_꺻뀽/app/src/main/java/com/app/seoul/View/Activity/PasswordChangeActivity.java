package com.app.seoul.View.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.app.seoul.Api.RetrofitApi;
import com.app.seoul.Api.RetrofitClient;
import com.app.seoul.Model.Data;
import com.app.seoul.Model.Value;
import com.app.seoul.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordChangeActivity extends AppCompatActivity {

    private RetrofitApi api;
    private ProgressDialog progressDialog;

    @BindView(R.id.pw_now)
    EditText now;
    @BindView(R.id.pw_newa)
    EditText newa;
    @BindView(R.id.pw_newb)
    EditText newb;

    @OnClick({R.id.pw_back, R.id.pw_submit})
    void Click(View v) {

        switch (v.getId()) {

            case R.id.pw_back:
                this.finish();
                break;

            case R.id.pw_submit:
                validate();
                break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);
        ButterKnife.bind(this);

    }

    private void sendData(String np) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("잠시만 기다려주세요");
        progressDialog.show();

        api = RetrofitClient.getRetrofit().create(RetrofitApi.class);

        Call<Value> call = api.chpw(Data.userID, now.getText().toString(), np);

        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {

                if (response.isSuccessful()) {

                    String value = response.body().getValue();
                    String message = response.body().getMessage();

                    if (value.equals("1")) {

                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                        finish();

                    } else {

                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }

                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                t.printStackTrace();
                call.cancel();
                progressDialog.cancel();
            }
        });

    }

    private void validate() {

        String a = newa.getText().toString();
        String b = newb.getText().toString();

        if (a.length() < 4 || b.length() < 4) {
            Toast.makeText(getApplicationContext(), "4자리 이상 입력해주세요", Toast.LENGTH_SHORT).show();
        } else {

            if (a.equals(b)) {
                sendData(a);
            } else {
                Toast.makeText(getApplicationContext(), "입력한 비밀번호가 맞지 않습니다.", Toast.LENGTH_SHORT).show();
            }

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
