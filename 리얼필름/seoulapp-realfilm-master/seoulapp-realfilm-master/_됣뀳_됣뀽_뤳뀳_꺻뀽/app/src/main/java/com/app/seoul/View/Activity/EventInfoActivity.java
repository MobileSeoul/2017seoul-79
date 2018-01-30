package com.app.seoul.View.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.app.seoul.Api.RetrofitApi;
import com.app.seoul.Api.RetrofitClient;
import com.app.seoul.Api.RetrofitOpenApi;
import com.app.seoul.Model.Data;
import com.app.seoul.Model.EventInfo.einfo_val;
import com.app.seoul.Model.Value;
import com.app.seoul.Model.up_bb;
import com.app.seoul.R;
import com.app.seoul.View.Adapter.up_b_adapter;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.seoul.R.id.einfo_maps;

public class EventInfoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private RetrofitApi api;
    private String cid, simage, stitle;
    private int ccc;
    private double mapx, mapy;
    private List<up_bb> item = new ArrayList<>();
    private up_b_adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @BindView(R.id.event_info_image)
    ImageView image;
    @BindView(R.id.event_info_title)
    TextView title;
    @BindView(R.id.event_info_location)
    TextView location;
    @BindView(R.id.event_info_date)
    TextView date;
    @BindView(R.id.event_info_time)
    TextView time;
    @BindView(R.id.event_info_age)
    TextView age;
    @BindView(R.id.event_info_price)
    TextView price;
    @BindView(R.id.event_info_tel)
    TextView tel;
    @BindView(R.id.event_info_intro)
    TextView intro;
    @BindView(R.id.event_info_subintro)
    TextView subintro;
    @BindView(R.id.event_info_btna)
    TextView btna;
    @BindView(R.id.event_info_btnb)
    TextView btnb;
    @BindView(R.id.event_info_layera)
    ScrollView layera;
    @BindView(R.id.event_info_layerb)
    RecyclerView recyclerView;

    @OnClick({R.id.event_info_back, R.id.event_info_btna, R.id.event_info_btnb})
    void Click(View v) {
        switch (v.getId()) {
            case R.id.event_info_back:
                this.finish();
                break;

            case R.id.event_info_btna:
                click(0);
                break;

            case R.id.event_info_btnb:
                click(1);
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);
        ButterKnife.bind(this);
        init();
    }

    private void click(int p) {
        switch (p) {
            case 0:
                layera.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                btna.setTextColor(getResources().getColor(R.color.white));
                btna.setBackgroundResource(R.color.textColor);
                btnb.setTextColor(getResources().getColor(R.color.textColor));
                btnb.setBackgroundResource(R.color.white);
                break;

            case 1:
                layera.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                btna.setTextColor(getResources().getColor(R.color.textColor));
                btna.setBackgroundResource(R.color.white);
                btnb.setTextColor(getResources().getColor(R.color.white));
                btnb.setBackgroundResource(R.color.textColor);
                break;
        }

    }

    private void init() {

        Intent intent = getIntent();
        cid = intent.getStringExtra("conid");
        ccc = Integer.parseInt(cid);
        simage = intent.getStringExtra("image");
        stitle = intent.getStringExtra("title");
        mapx = intent.getDoubleExtra("mapy", 0);
        mapy = intent.getDoubleExtra("mapx", 0);

        Glide.with(getApplicationContext())
                .load(simage)
                .into(image);

        api = RetrofitOpenApi.getRetrofit().create(RetrofitApi.class);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.scrollToPosition(0);

        initData();
        getfilm();

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(einfo_maps);
        supportMapFragment.getMapAsync(EventInfoActivity.this);
    }

    private void initData() {
        Call<einfo_val> call = api.einfo(ccc);
        call.enqueue(new Callback<einfo_val>() {
            @Override
            public void onResponse(Call<einfo_val> call, Response<einfo_val> response) {
                if (response.isSuccessful()) {

                    String stime = response.body().getResponse().getBody().getItems().getItem().getEventstartdate();
                    String endtime = response.body().getResponse().getBody().getItems().getItem().getEventenddate();
                    String limit = response.body().getResponse().getBody().getItems().getItem().getAgelimit();
                    String pay = response.body().getResponse().getBody().getItems().getItem().getUsetimefestival();
                    String sub = response.body().getResponse().getBody().getItems().getItem().getSubevent();

                    title.setText(stitle);
                    location.setText(response.body().getResponse().getBody().getItems().getItem().getEventplace());

                    if (stime != null && endtime != null) {
                        date.setText(stime + " ~ " + endtime);
                    } else {
                        date.setText(response.body().getResponse().getBody().getItems().getItem().getPlaytime());
                    }

                    if (limit != null) {
                        age.setText(limit);
                    }

                    if (pay != null) {
                        price.setText(pay);
                    }

                    tel.setText(response.body().getResponse().getBody().getItems().getItem().getSponsor1tel());
                    intro.setText(response.body().getResponse().getBody().getItems().getItem().getProgram());

                    if (sub != null) {
                        subintro.setText(sub);
                    }

                }
            }

            @Override
            public void onFailure(Call<einfo_val> call, Throwable t) {
                t.printStackTrace();
                call.cancel();
            }
        });
    }

    private void getfilm() {
        final RetrofitApi ap = RetrofitClient.getRetrofit().create(RetrofitApi.class);
        Call<Value> info = ap.eventinfo(Data.userID, mapx, mapy);
        info.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()) {

                    String value = response.body().getValue();
                    if(value.equals("1")){

                        item = response.body().getUp_b();
                        adapter = new up_b_adapter(item, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(mapx, mapy);

        int height = 120;
        int width = 90;
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.marker_b);
        Bitmap bb = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(bb, width, height, false);

        googleMap.addMarker(new MarkerOptions().position(latLng).title(stitle).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        googleMap.getUiSettings().setScrollGesturesEnabled(false);


    }
}
