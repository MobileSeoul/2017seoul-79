package com.app.seoul.View.Activity;

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

public class ContactusActivity extends AppCompatActivity {

    private int OVERLAPCOUNT = 0;
    private RetrofitApi api;

    @BindView(R.id.contact_content)
    EditText content;
    @BindView(R.id.contact_email)
    EditText email;

    @OnClick({R.id.contact_back, R.id.contact_submit})
    void Click(View v) {
        switch (v.getId()) {
            case R.id.contact_back:
                this.finish();
                break;
            case R.id.contact_submit:
                validate();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);
        ButterKnife.bind(this);
    }

    private void validate() {

        String con = content.getText().toString();
        String e_mail = email.getText().toString();

        if (con.equals("") || e_mail.equals("")) {
            Toast.makeText(getApplicationContext(), "빈칸을 확인해주세요.", Toast.LENGTH_SHORT).show();
        } else {
            OVERLAPCOUNT++;
            sendData(con, e_mail);
        }

    }

    private void sendData(String content, String email) {

        api = RetrofitClient.getRetrofit().create(RetrofitApi.class);

        Call<Value> call = api.qna(Data.userID, content, email);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")) {
                    OVERLAPCOUNT--;
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    OVERLAPCOUNT--;
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                t.printStackTrace();
                call.cancel();
                OVERLAPCOUNT--;
            }
        });

    }


}
