package com.app.seoul.View.Activity;

import android.app.Activity;
import android.content.Intent;
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

public class ChangeNameActivity extends AppCompatActivity {

    private RetrofitApi api;

    @BindView(R.id.cname_t)
    EditText tv;

    @OnClick({R.id.cname_back, R.id.cname_submit, R.id.cname_del})
    void click(View v) {
        switch (v.getId()) {
            case R.id.cname_back:
                this.finish();
                break;
            case R.id.cname_submit:
                sendData();
                break;

            case R.id.cname_del:
                tv.setText("");
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String sname = intent.getStringExtra("name");
        tv.setText(sname);
    }

    private void sendData() {
        api = RetrofitClient.getRetrofit().create(RetrofitApi.class);

        if (tv.getText().toString().length() > 0) {

            Call<Value> call = api.chname(Data.userID, tv.getText().toString());
            call.enqueue(new Callback<Value>() {
                @Override
                public void onResponse(Call<Value> call, Response<Value> response) {
                    if (response.isSuccessful()) {
                        String value = response.body().getValue();
                        String message = response.body().getMessage();
                        if (value.equals("1")) {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.putExtra("name", tv.getText().toString());
                            setResult(Activity.RESULT_OK, intent);
                            finish();
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
            Toast.makeText(getApplicationContext(), "이름을 입력해 주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
