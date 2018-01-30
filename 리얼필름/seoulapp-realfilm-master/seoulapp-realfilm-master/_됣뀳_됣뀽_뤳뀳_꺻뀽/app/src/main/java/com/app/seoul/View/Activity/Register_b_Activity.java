package com.app.seoul.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.seoul.R.id.asdf;

public class Register_b_Activity extends AppCompatActivity {

    private int check = 0;
    private RetrofitApi api;

    @BindView(R.id.register_id)
    EditText id;
    @BindView(R.id.register_pw)
    EditText pw;
    @BindView(R.id.register_pw_check)
    EditText pw_check;
    @BindView(asdf)
    TextView checkText;
    @BindView(R.id.reg_b_lin)
    LinearLayout lin;
    @BindView(R.id.reg_b_logo)
    ImageView logo;
    @BindView(R.id.reg_b_step)
    ImageView step;

    @OnClick({R.id.register_b_submit})
    void Click(View v) {
        switch (v.getId()) {
            case R.id.register_b_submit:
                checkpw();
                break;
        }
    }

    @OnTextChanged(R.id.register_id)
    void change(CharSequence charSequence) {

        if (charSequence.toString().length() < 6) {
            checkText.setText("6자리 이상 입력해주세요.");
            checkText.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else {
            search(charSequence.toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_b_);
        ButterKnife.bind(this);

        setBackgroundImage();

    }

    private void checkpw() {

        String userID = id.getText().toString();
        String ch1 = pw.getText().toString(), ch2 = pw_check.getText().toString();

        if(userID.equals("")){  //아이디 체크
            Toast.makeText(getApplicationContext(), "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
        }else {

            if (ch1.equals(ch2)) {

                if (ch1.length() >= 4 && ch1.length() <= 12) {

                    Intent intent = new Intent(getApplicationContext(), Register_c_Activity.class);
                    intent.putExtra("id", userID);
                    intent.putExtra("pw", ch1);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();


                } else if (ch1.length() < 4) {
                    Toast.makeText(getApplicationContext(), "4자리 이상 입력해주세요", Toast.LENGTH_SHORT).show();

                } else if (ch1.length() > 12) {
                    Toast.makeText(getApplicationContext(), "12자리를 초과할 수 없습니다", Toast.LENGTH_SHORT).show();
                }


            } else {
                Toast.makeText(getApplicationContext(), "비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void search(String id) {    // 아이디 중복확인

        api = RetrofitClient.getRetrofit().create(RetrofitApi.class);
        Call<Value> call = api.registercheck(id);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                if (value.equals("0")) {
                    checkText.setText("사용 가능");
                    checkText.setTextColor(getResources().getColor(R.color.colorAccent));
                } else {
                    checkText.setText("이미 사용중인 아이디입니다");
                    checkText.setTextColor(getResources().getColor(R.color.colorPrimary));

                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                t.printStackTrace();
                call.cancel();

            }
        });
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

        Glide.with(this)    // 로고고
                .load(R.drawable.logo2)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(logo);

        Glide.with(this)
                .load(R.drawable.login_step2_icon_266x30)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(step);
    }
}
