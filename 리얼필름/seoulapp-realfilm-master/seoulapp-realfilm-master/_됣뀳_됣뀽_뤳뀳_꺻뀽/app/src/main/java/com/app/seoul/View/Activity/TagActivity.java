package com.app.seoul.View.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.seoul.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TagActivity extends AppCompatActivity {

    private final int MAXLENGTH = 5;
    private final int RESPONSE_CODE = 333;
    private int DELETE = 0;
    private List<Integer> resultImage = new ArrayList<>();
    private List<Integer> defImage = new ArrayList<>();
    private Integer[] tagImage = new Integer[12];

    @BindView(R.id.tag_l_a)
    ImageView a;
    @BindView(R.id.tag_l_b)
    ImageView b;
    @BindView(R.id.tag_l_c)
    ImageView c;
    @BindView(R.id.tag_l_d)
    ImageView d;
    @BindView(R.id.tag_l_e)
    ImageView e;
    @BindView(R.id.tag_l_f)
    ImageView f;
    @BindView(R.id.tag_l_g)
    ImageView g;
    @BindView(R.id.tag_l_h)
    ImageView h;
    @BindView(R.id.tag_l_i)
    ImageView i;
    @BindView(R.id.tag_l_j)
    ImageView j;
    @BindView(R.id.tag_l_k)
    ImageView k;
    @BindView(R.id.tag_l_l)
    ImageView l;
    @BindView(R.id.tag_selectedtag)
    LinearLayout lin;

    @OnClick({R.id.tag_back, R.id.tag_submit, R.id.tag_l_a, R.id.tag_l_b, R.id.tag_l_c, R.id.tag_l_d, R.id.tag_l_e,
            R.id.tag_l_f, R.id.tag_l_g, R.id.tag_l_h, R.id.tag_l_i, R.id.tag_l_j, R.id.tag_l_k, R.id.tag_l_l})
    void Click(View v) {
        switch (v.getId()) {
            case R.id.tag_back:
                this.finish();
                break;

            case R.id.tag_submit:
                ResponseResult();
                break;

            case R.id.tag_l_a:
                setImage(tagImage[0]);
                break;

            case R.id.tag_l_b:
                setImage(tagImage[1]);
                break;

            case R.id.tag_l_c:
                setImage(tagImage[2]);
                break;

            case R.id.tag_l_d:
                setImage(tagImage[3]);
                break;

            case R.id.tag_l_e:
                setImage(tagImage[4]);
                break;

            case R.id.tag_l_f:
                setImage(tagImage[5]);
                break;

            case R.id.tag_l_g:
                setImage(tagImage[6]);
                break;

            case R.id.tag_l_h:
                setImage(tagImage[7]);
                break;

            case R.id.tag_l_i:
                setImage(tagImage[8]);
                break;

            case R.id.tag_l_j:
                setImage(tagImage[9]);
                break;

            case R.id.tag_l_k:
                setImage(tagImage[10]);
                break;

            case R.id.tag_l_l:
                setImage(tagImage[11]);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);
        ButterKnife.bind(this);
        setGlobalValue();

    }

    private void setGlobalValue() { // 기본 이미지 설정

        tagImage[0] = R.drawable.category_alley_icon_40x56;
        tagImage[1] = R.drawable.category_show_icon_40x56;
        tagImage[2] = R.drawable.category_music_icon_40x56;
        tagImage[3] = R.drawable.category_drinking_icon_40x56;
        tagImage[4] = R.drawable.category_park_icon_40x56;
        tagImage[5] = R.drawable.category_walk_icon_40x56;
        tagImage[6] = R.drawable.category_traffic_icon_40x56;
        tagImage[7] = R.drawable.category_game_icon_40x56;
        tagImage[8] = R.drawable.category_cafe_icon_40x56;
        tagImage[9] = R.drawable.category_exercise_icon_40x56;
        tagImage[10] = R.drawable.category_busking_icon_40x56;
        tagImage[11] = R.drawable.category_photozone_icon_40x56;

        defImage.add(R.drawable.kaokaotalk);
        defImage.add(R.drawable.category_blank_icon_40x56);
        defImage.add(R.drawable.category_blank_icon_40x56);
        defImage.add(R.drawable.category_blank_icon_40x56);
        defImage.add(R.drawable.category_blank_icon_40x56);

        showUriList(defImage);
    }

    private void showUriList(List<Integer> image) {     // 선택된 이미지 추가

        lin.removeAllViews();
        int wdpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
        int htpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 56, getResources().getDisplayMetrics());

        for (final Integer position : image) {

            final View imageHolder = LayoutInflater.from(this).inflate(R.layout.tag_item, null);
            ImageView thumbnail = (ImageView) imageHolder.findViewById(R.id.tag_image);

            Glide.with(this)
                    .load(position)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(thumbnail);

            lin.addView(imageHolder);
            FrameLayout.LayoutParams param = new FrameLayout.LayoutParams(wdpx, htpx);
            param.setMargins(63, 0, 0, 0);
            thumbnail.setLayoutParams(param);

            thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resultImage.remove(position);
                    lin.removeView(imageHolder);
                }
            });
        }
    }

    private void setImage(Integer image) {  // 이미지 추가


        removeImage(image);

        for (int i = 0; i < MAXLENGTH; i++) {

            if (resultImage.size() == 0) {

                resultImage.add(image);
                showUriList(resultImage);
                break;

            } else {

                if (DELETE == 1) {

                    DELETE--;
                    showUriList(resultImage);
                    break;
                } else {
                    if (resultImage.size() == 5) {

                        Toast.makeText(getApplicationContext(), "최대 5개까지 선택가능합니다", Toast.LENGTH_SHORT).show();
                        break;

                    } else {

                        resultImage.add(image);
                        showUriList(resultImage);
                        break;

                    }

                }
            }
        }

    }

    private void removeImage(Integer image) {   // 이미지 삭제

        if (resultImage.size() > 0) {   // 추가된 이미지가 있을때

            for (int i = 0; i < resultImage.size(); i++) {

                if (resultImage.get(i).equals(image)) {
                    Log.e("0", "1");
                    resultImage.remove(i);
                    DELETE++;
                    break;
                }
            }
        }
    }

    private void ResponseResult() { // 값 전달


        Intent intent = new Intent();
        ArrayList<Integer> result = new ArrayList<Integer>();

        for (int i = 0; i < resultImage.size(); i++) {

            for (int j = 0; j < tagImage.length; j++) {

                if (resultImage.get(i).equals(tagImage[j])) {

                    result.add(j);
                    break;
                }
            }
        }

        intent.putIntegerArrayListExtra("tag", result);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
