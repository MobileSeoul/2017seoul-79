package com.app.seoul.View.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.seoul.Api.RetrofitApi;
import com.app.seoul.Api.RetrofitClient;
import com.app.seoul.Model.Data;
import com.app.seoul.Model.Value;
import com.app.seoul.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

public class UploadActivity extends AppCompatActivity {

    private ArrayList<Uri> selectedUriList = new ArrayList<>();
    private ArrayList<String> stringArry;
    private ArrayList<Integer> tag = new ArrayList<Integer>();
    private List<MultipartBody.Part> parts;
    private List<MultipartBody.Part> tagparts;
    private HashMap<String, RequestBody> map = new HashMap<>();
    private Uri aa;

    private String[] imagess;
    private boolean showCamera = false;
    private final int REQUEST_CODE_PLACESPICKER = 2;
    private final int REQUEST_CODE_TAG = 333;
    private final int REQUEST_CODE_CAMERA = 123;
    private int DOUBLE_TOUCH = 0;

    private String lati, longi, locationName, address, code, bid;
    private RetrofitApi api;
    private ProgressDialog progressDialog;


    @BindView(R.id.selected_photos_container)
    LinearLayout mSelectedImagesContainer;
    @BindView(R.id.upload_map_text)
    TextView locationText;
    @BindView(R.id.upload_title)
    EditText e_title;
    @BindView(R.id.upload_content)
    EditText e_content;
    @BindView(R.id.upload_lin)
    LinearLayout lin;

    @OnClick({R.id.upload_back, R.id.upload_photo, R.id.upload_camera, R.id.upload_tag, R.id.upload_place, R.id.upload_send})
    void click(View v) {

        switch (v.getId()) {
            case R.id.upload_back:  // 뒤로가기
                this.finish();
                break;
            case R.id.upload_photo: // 이미지 선택
                setMultiShow();
                break;
            case R.id.upload_place: // 지도
                placepicker();
                break;
            case R.id.upload_tag:   // 태그선택
                Intent intent = new Intent(getApplicationContext(), TagActivity.class);
                startActivityForResult(intent, REQUEST_CODE_TAG);
                break;
            case R.id.upload_camera:    // 카메라
                setShowCamera();
                break;
            case R.id.upload_send:
                if (code.equals("0")) {

                    reSend();
                } else {
                    if (DOUBLE_TOUCH == 0) {
                        sendData(code);
                    }
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        ButterKnife.bind(this);
        api = RetrofitClient.getRetrofit().create(RetrofitApi.class);
        getreviseData();
    }

    private void getreviseData() {

        code = "1";

        Intent intent = getIntent();
        if (intent.getStringExtra("revise") != null) {

            code = intent.getStringExtra("revise");

            if (code.equals("0")) {


                lin.setVisibility(View.GONE);

                String title = intent.getStringExtra("title");
                String content = intent.getStringExtra("content");
                String location = intent.getStringExtra("location");
                bid = intent.getStringExtra("bid");
                stringArry = new ArrayList<>();
                stringArry = intent.getStringArrayListExtra("image");

                e_title.setText(title);
                e_content.setText(content);
                locationText.setText(location);
                reviseUriList(stringArry);

            }

        }
    }

    // 사진 선택
    private void setMultiShow() {

        TedBottomPicker bottomSheetDialogFragment = new TedBottomPicker.Builder(getApplicationContext())
                .setOnMultiImageSelectedListener(new TedBottomPicker.OnMultiImageSelectedListener() {

                    @Override
                    public void onImagesSelected(ArrayList<Uri> uriList) {

                        selectedUriList = uriList;
                        showUriList(selectedUriList);
                        selectedUriList.size();

                        stringArry = new ArrayList<String>();

                        for (int i = 0; i < selectedUriList.size(); i++) {
                            stringArry.add(String.valueOf(selectedUriList.get(i).getPath()));
                        }

                        imagess = stringArry.toArray(new String[stringArry.size()]);

                        parts = new ArrayList<>();

                        for (int i = 0; i < imagess.length; i++) {
                            parts.add(prepareFilePart(imagess[i]));
                        }
                    }

                })
                .showCameraTile(showCamera)         //카메라 기능 빼기
                .setCompleteButtonText("확인")      //완료 버튼 이름
                .setEmptySelectionText("필름")
                .setSelectMaxCount(10)               //최대 선택 가능한 사진 수
                .setSelectMaxCountErrorText("10개까지 선택 가능합니다.")
                .setSelectMinCount(1)
                .setTitle("사진선택")
                .setSelectMinCountErrorText("1개 이상 선택하셔야합니다.")
                .setSelectedUriList(selectedUriList)
                .create();

        bottomSheetDialogFragment.show(getSupportFragmentManager());

    }

    //LinearLayout에 선택한 사진 넣어줌.
    private void showUriList(final ArrayList<Uri> uriList) {
        mSelectedImagesContainer.removeAllViews();

        mSelectedImagesContainer.setVisibility(View.VISIBLE);

        int wdpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        int htpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());

        for (final Uri uri : uriList) {
            final View imageHolder = LayoutInflater.from(this).inflate(R.layout.image_item, null);
            ImageView thumbnail = (ImageView) imageHolder.findViewById(R.id.tag_image);
            ImageView clear = (ImageView) imageHolder.findViewById(R.id.image_clear);


            Glide.with(this)
                    .load(uri.toString())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(thumbnail);

            Glide.with(this)
                    .load(R.drawable.upload_clear_btn_24x24)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(clear);

            mSelectedImagesContainer.addView(imageHolder);

            final FrameLayout.LayoutParams param = new FrameLayout.LayoutParams(wdpx, htpx);
            param.setMargins(10, 0, 0, 8);
            thumbnail.setLayoutParams(param);

            clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uriList.remove(uri);
                    mSelectedImagesContainer.removeView(imageHolder);
                }
            });

        }
    }

    private void reSend() {  // 게시글 수정
        Call<Value> re = api.revise(e_title.getText().toString(), e_content.getText().toString(), bid);
        re.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                if (value.equals("1")) {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("title", e_title.getText().toString());
                    intent.putExtra("content", e_content.getText().toString());
                    setResult(Activity.RESULT_OK, intent);
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

    private void reviseUriList(final ArrayList<String> uriList) {
        mSelectedImagesContainer.removeAllViews();

        mSelectedImagesContainer.setVisibility(View.VISIBLE);

        int wdpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        int htpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());

        for (final String uri : uriList) {
            final View imageHolder = LayoutInflater.from(this).inflate(R.layout.image_item, null);
            ImageView thumbnail = (ImageView) imageHolder.findViewById(R.id.tag_image);
            ImageView clear = (ImageView) imageHolder.findViewById(R.id.image_clear);
            clear.setVisibility(View.GONE);

            Glide.with(this)
                    .load(uri)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(thumbnail);

            mSelectedImagesContainer.addView(imageHolder);

            final FrameLayout.LayoutParams param = new FrameLayout.LayoutParams(wdpx, htpx);
            param.setMargins(10, 0, 0, 8);
            thumbnail.setLayoutParams(param);


        }
    }

    // 구글 플레이스
    private void placepicker() {
        PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();

        try {
            Intent intent = intentBuilder.build(this);
            startActivityForResult(intent, REQUEST_CODE_PLACESPICKER);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 좌표와 이름
    private void displaySelect(Intent data) {
        Place place = PlacePicker.getPlace(data, this);
        address = place.getAddress().toString();
        locationName = place.getName().toString();
        cutLatLng(place.getLatLng().toString());
        locationText.setText(locationName);
        if (locationName.length() > 0) {
            locationText.setVisibility(View.VISIBLE);
        }
    }

    // 좌표 자르기
    private void cutLatLng(String data) {

        String substring = data.substring(10);
        String resultSum[] = substring.split("[)]");
        String result[] = resultSum[0].split(",");
        lati = result[0];
        longi = result[1];
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_PLACESPICKER && resultCode == RESULT_OK) {   // 지도
            displaySelect(data);
        } else if (requestCode == REQUEST_CODE_TAG && resultCode == RESULT_OK) {
            tag = data.getIntegerArrayListExtra("tag");
        } else if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {
            selectedUriList.add(data.getData());
            showUriList(selectedUriList);
        }
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String uri) {
        File file = new File(uri);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        return MultipartBody.Part.createFormData("flim_image[]", file.getName(), requestBody);
    }

    private void setShowCamera() {  // 카메라 선택

        if (selectedUriList.size() < 10) {  // 10장 이하일경우 실행
            Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(camera, REQUEST_CODE_CAMERA);
        } else {
            Toast.makeText(getApplicationContext(), "업로드 개수를 초과했습니다.", Toast.LENGTH_SHORT).show();
        }

    }

    private void sendData(String code) {
        String title = e_title.getText().toString();
        String content = e_content.getText().toString();
        DOUBLE_TOUCH++;

        if (title.length() == 0 || content.length() == 0 || tag.size() == 0 || locationName.length() == 0) {

            if (title.length() == 0) {
                Toast.makeText(getApplicationContext(), "제목을 입력해 주세요", Toast.LENGTH_SHORT).show();
            } else if (content.length() == 0) {
                Toast.makeText(getApplicationContext(), "내용을 입력해 주세요", Toast.LENGTH_SHORT).show();
            } else if (tag.size() == 0) {
                Toast.makeText(getApplicationContext(), "태그를 선택해 주세요", Toast.LENGTH_SHORT).show();
            } else if (locationName.length() == 0) {
                Toast.makeText(getApplicationContext(), "장소를 선택해 주세요", Toast.LENGTH_SHORT).show();
            }
            DOUBLE_TOUCH--;
        } else {

            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("업로드 중 입니다");
            progressDialog.show();
            getTagrow(tag);
            map.put("address", toRequestBody(address));
            map.put("title", toRequestBody(title));
            map.put("content", toRequestBody(content));
            map.put("uid", toRequestBody(Data.userID));
            map.put("lati", toRequestBody(lati));
            map.put("longi", toRequestBody(longi));
            map.put("locationName", toRequestBody(locationName));

            Call<Value> call = api.upload(parts, map);
            call.enqueue(new Callback<Value>() {
                @Override
                public void onResponse(Call<Value> call, Response<Value> response) {

                    String value = response.body().getValue();
                    String message = response.body().getMessage();
                    if (value.equals("1")) {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                        DOUBLE_TOUCH--;
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                        DOUBLE_TOUCH--;
                    }

                }

                @Override
                public void onFailure(Call<Value> call, Throwable t) {
                    call.cancel();
                    t.printStackTrace();
                    progressDialog.cancel();
                    DOUBLE_TOUCH--;

                }
            });

        }
    }

    public RequestBody toRequestBody(String value) {

        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        return requestBody;
    }

    private void getTagrow(ArrayList<Integer> row) {

        tagparts = new ArrayList<>();

        for (int i = 0; i < row.size(); i++) {

            map.put("tag[" + i + "]", toRequestBody(String.valueOf(row.get(i))));

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }


}
