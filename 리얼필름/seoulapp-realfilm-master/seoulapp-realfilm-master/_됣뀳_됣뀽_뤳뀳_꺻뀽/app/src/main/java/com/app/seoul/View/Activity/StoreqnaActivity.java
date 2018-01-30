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

public class StoreqnaActivity extends AppCompatActivity {

    private RetrofitApi api;
    private int OVERLAP = 0;

    @BindView(R.id.qna_address)
    EditText add;
    @BindView(R.id.qna_address_a)
    EditText add_a;
    @BindView(R.id.qna_name)
    EditText name;
    @BindView(R.id.qna_phone)
    EditText phone;

    @OnClick({R.id.qna_back, R.id.qna_submit})
    void Click(View v) {
        switch (v.getId()) {
            case R.id.qna_back:
                this.finish();
                break;
            case R.id.qna_submit:
                validate();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storeqna);
        ButterKnife.bind(this);
    }

    private void sendData(String name, String add, String adda, String phone) { // 서버 업로드

        if (OVERLAP == 1) {
            api = RetrofitClient.getRetrofit().create(RetrofitApi.class);
            Call<Value> call = api.office_qna(Data.userID, name, add, adda, phone);
            call.enqueue(new Callback<Value>() {
                @Override
                public void onResponse(Call<Value> call, Response<Value> response) {
                    String value = response.body().getValue();
                    String message = response.body().getMessage();
                    if (value.equals("1")) {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        finish();
                        OVERLAP--;
                    } else {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        OVERLAP--;
                    }
                }

                @Override
                public void onFailure(Call<Value> call, Throwable t) {
                    call.cancel();
                    t.printStackTrace();
                    OVERLAP--;

                }
            });
        }

    }

    private void validate() {   // 빈칸 체크
        String name_text = name.getText().toString(), add_text = add.getText().toString(),
                adda_text = add_a.getText().toString(), phone_text = phone.getText().toString();

        if (name_text.equals("") || add_text.equals("") || adda_text.equals("") || phone_text.equals("")) {
            Toast.makeText(getApplicationContext(), "빈칸을 확인해 주세요.", Toast.LENGTH_SHORT).show();
        } else {
            OVERLAP++;
            sendData(name_text, add_text, adda_text, phone_text);
        }
    }
}
