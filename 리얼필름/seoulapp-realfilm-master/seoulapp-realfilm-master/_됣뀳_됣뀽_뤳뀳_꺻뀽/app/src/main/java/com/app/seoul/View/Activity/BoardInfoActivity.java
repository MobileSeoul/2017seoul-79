package com.app.seoul.View.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.seoul.Api.RetrofitApi;
import com.app.seoul.Api.RetrofitClient;
import com.app.seoul.Api.RetrofitFoodApi;
import com.app.seoul.Model.Data;
import com.app.seoul.Model.Value;
import com.app.seoul.Model.comment;
import com.app.seoul.Model.info;
import com.app.seoul.R;
import com.app.seoul.View.Adapter.comment_adapter;
import com.app.seoul.View.Adapter.food_adapater;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
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
import butterknife.OnTextChanged;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.seoul.R.id.info_maps;

public class BoardInfoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private RetrofitApi api;
    private List<String> ImageArray = new ArrayList<>();
    private List<Integer> tagArray = new ArrayList<>();
    private List<comment> commentList;
    private List<com.app.seoul.Model.Food.item> FoodArray = new ArrayList<>();
    private double lati, longi;
    private String lname, bid, popupc;
    private BitmapPool bitmapPool;
    private Integer[] tagImage = new Integer[12];
    private int userCheck, bmCheck, flCheck;
    private int opena = 0, openb = 0, mapclear = 0;

    private String rtitle, rcontent;


    private ProgressDialog progressDialog;
    private int DOUBLE_CHECK = 0;
    private RecyclerView.LayoutManager mLayoutManager;
    private comment_adapter adapter;
    private food_adapater f_adapter;
    private final int REQUEST_CODE_REVISE = 0;
    private final int REQUEST_CODE_USERPROFILE = 1;
    private final String FOOD_KOREAN = "A05020100";
    private final String FOOD_AMERICAN = "A05020200";
    private final String FOOD_JAPAN = "A05020300";
    private final String FOOD_CHAINA = "A05020400";
    private final String FOOD_CAFE = "A05020900";

    @BindView(R.id.info_top_background)
    ImageView top_background;
    @BindView(R.id.info_top_title)
    TextView top_title;
    @BindView(R.id.info_top_profile)
    de.hdodenhof.circleimageview.CircleImageView top_profile;
    @BindView(R.id.info_top_fallow)
    ImageButton top_fallow;
    @BindView(R.id.info_top_like)
    ImageButton top_like;
    @BindView(R.id.info_top_date)
    TextView top_date;
    @BindView(R.id.info_top_name)
    TextView top_name;
    @BindView(R.id.info_view_text)
    TextView view_text;
    @BindView(R.id.info_like_count)
    TextView like_count;
    @BindView(R.id.info_comment_count)
    TextView comment_count;
    @BindView(R.id.info_slide_close)
    LinearLayout slide_close;
    @BindView(R.id.info_slide_open)
    LinearLayout slide_open;
    @BindView(R.id.info_image_row)
    LinearLayout image_row;
    @BindView(R.id.info_title)
    TextView title;
    @BindView(R.id.info_content)
    TextView content;
    @BindView(R.id.info_tag_row)
    LinearLayout tag_row;
    @BindView(R.id.info_locationName)
    TextView locationName;
    @BindView(R.id.info_comment_edit)
    EditText comment_editText;
    @BindView(R.id.info_comment_popup)
    LinearLayout comment_popup;
    @BindView(R.id.info_comment_popup_id)
    TextView popup_id;
    @BindView(R.id.info_comment_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.info_chiledscroll)
    LinearLayout chiledscroll;
    @BindView(R.id.info_scrap)
    ImageButton bookmark;
    @BindView(R.id.info_more)
    ImageButton more;
    @BindView(R.id.info_sl)
    ImageView open_a;
    @BindView(R.id.info_sli)
    ImageView open_b;
    @BindView(R.id.info_open_close)
    TextView open_close;
    @BindView(R.id.info_open_open)
    TextView open_open;
    @BindView(R.id.info_food_recyclerview)
    RecyclerView food_recyclerview;
    @BindView(R.id.info_scrollview)
    NestedScrollView info_scrollv;
    @BindView(R.id.info_food_k)
    TextView food_k;
    @BindView(R.id.info_food_a)
    TextView food_a;
    @BindView(R.id.info_food_j)
    TextView food_j;
    @BindView(R.id.info_food_c)
    TextView food_c;
    @BindView(R.id.info_food_cafe)
    TextView food_cafe;
    @BindView(R.id.info_not_food)
    TextView not_food;
    @BindView(R.id.info_horizontalview)
    HorizontalScrollView horizontalview;
    @BindView(R.id.info_scroll_open)
    LinearLayout scrollopen;
    @BindView(R.id.tool_bar)
    zhan.transparent.widget.TransparentFrameLayout mTransparentFrameLayout;

    @OnTextChanged(R.id.info_comment_edit)
    void commentedit(CharSequence charSequence) {

        if (charSequence.toString().length() > 0) {
            comment_popup.setVisibility(View.VISIBLE);
        } else {
            comment_popup.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.info_slide_close, R.id.info_slide_open, R.id.info_back, R.id.info_comment_submit,
            R.id.info_top_fallow, R.id.info_top_like, R.id.info_scrap, R.id.info_more, R.id.info_top_profile, R.id.info_food_k,
            R.id.info_food_a, R.id.info_food_j, R.id.info_food_c, R.id.info_food_cafe})
    void click(View v) {

        switch (v.getId()) {
            case R.id.info_slide_close:

                open_close.setTextColor(getResources().getColor(R.color.textColor));
                open_open.setTextColor(getResources().getColor(R.color.film_gray));
                open_b.setBackgroundResource(R.drawable.film_opneoff_btn_24x24);
                open_a.setBackgroundResource(R.drawable.film_closeon_btn_24x24);
                horizontalview.setVisibility(View.VISIBLE);
                scrollopen.setVisibility(View.GONE);

                break;
            case R.id.info_slide_open:


                open_close.setTextColor(getResources().getColor(R.color.film_gray));
                open_open.setTextColor(getResources().getColor(R.color.textColor));
                open_b.setBackgroundResource(R.drawable.film_opneon_btn_24x24);
                open_a.setBackgroundResource(R.drawable.film_closeoff_btn_24x24);
                horizontalview.setVisibility(View.GONE);
                scrollopen.setVisibility(View.VISIBLE);

                break;
            case R.id.info_back:
                this.finish();
                break;
            case R.id.info_comment_submit:
                comment(bid, comment_editText.getText().toString());
                break;
            case R.id.info_top_like:

                if (userCheck == 0) {
                    sendlikeData(userCheck);
                    userCheck++;
                } else {
                    sendlikeData(userCheck);
                    userCheck--;
                }
                break;
            case R.id.info_scrap:
                if (bmCheck == 0) {
                    bookMark(bmCheck);
                    bmCheck++;
                } else {
                    bookMark(bmCheck);
                    bmCheck--;
                }
                break;

            case R.id.info_top_fallow:
                if (flCheck == 0) {
                    fallow(flCheck);
                    flCheck++;
                } else {
                    fallow(flCheck);
                    flCheck--;
                }
                break;

            case R.id.info_more:
                popup();
                break;

            case R.id.info_top_profile:
                Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                intent.putExtra("uid", popupc);
                intent.putExtra("vali", String.valueOf(flCheck));
                startActivityForResult(intent, REQUEST_CODE_USERPROFILE);
                break;

            case R.id.info_food_k:
                chooseFood(food_k, 0);
                break;
            case R.id.info_food_a:
                chooseFood(food_a, 1);
                break;
            case R.id.info_food_j:
                chooseFood(food_j, 2);
                break;
            case R.id.info_food_c:
                chooseFood(food_c, 3);
                break;
            case R.id.info_food_cafe:
                chooseFood(food_cafe, 4);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_info);
        ButterKnife.bind(this);

        api = RetrofitClient.getRetrofit().create(RetrofitApi.class);
        Intent intent = getIntent();
        bid = intent.getStringExtra("bid");

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.scrollToPosition(0);


        loadData(bid);
        commentScrollPosition();
        scrolled();


    }


    private void popup() {


        if (Data.userID.equals(popupc)) {

            PopupMenu mpopup = new PopupMenu(BoardInfoActivity.this, more);
            MenuInflater inflater = mpopup.getMenuInflater();
            inflater.inflate(R.menu.info_my_popup, mpopup.getMenu());


            mpopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.mpop_a:

                            break;
                        case R.id.mpop_b:
                            revise();
                            break;
                        case R.id.mpop_c:
                            delete();
                            break;
                    }

                    return true;
                }
            });
            mpopup.show();

        } else {
            PopupMenu popup = new PopupMenu(BoardInfoActivity.this, more);
            popup.getMenuInflater().inflate(R.menu.info_popup, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Log.e("popup", "1");
                    return true;
                }
            });
            popup.show();
        }
    }

    private void revise() {

        Intent revise = new Intent(getApplicationContext(), UploadActivity.class);
        revise.putExtra("title", rtitle);
        revise.putExtra("content", rcontent);
        revise.putExtra("location", lname);
        revise.putExtra("revise", "0");
        revise.putExtra("bid", bid);
        revise.putStringArrayListExtra("image", (ArrayList<String>) ImageArray);
        startActivityForResult(revise, REQUEST_CODE_REVISE);


    }

    private void delete() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("글 삭제");
        builder.setMessage("정말 지우시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("bid", bid);
                        Call<Value> delete = api.deleteinfo(bid, Data.userID);
                        delete.enqueue(new Callback<Value>() {
                            @Override
                            public void onResponse(Call<Value> call, Response<Value> response) {
                                if (response.isSuccessful()) {

                                    String value = response.body().getValue();
                                    String message = response.body().getMessage();

                                    if (value.equals("1")) {
                                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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

                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.show();
    }

    private void scrolled() {
        mTransparentFrameLayout.setColorToBackGround(getResources().getColor(R.color.textColor));
        mTransparentFrameLayout.setMaxOffset(getResources().getDimension(R.dimen.offset));
        info_scrollv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                mTransparentFrameLayout.updateTop(oldScrollY);
            }
        });

    }

    private void chooseFood(TextView tv, int a) {

        switch (a) {
            case 0:
                tv.setBackgroundResource(R.drawable.more_round);
                food_a.setBackgroundResource(R.drawable.more_round_a);
                food_c.setBackgroundResource(R.drawable.more_round_a);
                food_j.setBackgroundResource(R.drawable.more_round_a);
                food_cafe.setBackgroundResource(R.drawable.more_round_a);
                getFoodData(FOOD_KOREAN);
                break;
            case 1:
                tv.setBackgroundResource(R.drawable.more_round);
                food_k.setBackgroundResource(R.drawable.more_round_a);
                food_j.setBackgroundResource(R.drawable.more_round_a);
                food_c.setBackgroundResource(R.drawable.more_round_a);
                food_cafe.setBackgroundResource(R.drawable.more_round_a);
                getFoodData(FOOD_AMERICAN);
                break;
            case 2:
                tv.setBackgroundResource(R.drawable.more_round);
                food_k.setBackgroundResource(R.drawable.more_round_a);
                food_a.setBackgroundResource(R.drawable.more_round_a);
                food_c.setBackgroundResource(R.drawable.more_round_a);
                food_cafe.setBackgroundResource(R.drawable.more_round_a);
                getFoodData(FOOD_JAPAN);
                break;
            case 3:
                tv.setBackgroundResource(R.drawable.more_round);
                food_k.setBackgroundResource(R.drawable.more_round_a);
                food_a.setBackgroundResource(R.drawable.more_round_a);
                food_j.setBackgroundResource(R.drawable.more_round_a);
                food_cafe.setBackgroundResource(R.drawable.more_round_a);
                getFoodData(FOOD_CHAINA);
                break;
            case 4:
                tv.setBackgroundResource(R.drawable.more_round);
                food_k.setBackgroundResource(R.drawable.more_round_a);
                food_a.setBackgroundResource(R.drawable.more_round_a);
                food_j.setBackgroundResource(R.drawable.more_round_a);
                food_c.setBackgroundResource(R.drawable.more_round_a);
                getFoodData(FOOD_CAFE);
        }

    }


    private void loadData(String bid) {

        Call<info> call = api.info(bid, Data.userID);
        call.enqueue(new Callback<info>() {
            @Override
            public void onResponse(Call<info> call, Response<info> response) {
                if (response.isSuccessful()) {

                    ImageArray = response.body().getImagearray();
                    tagArray = response.body().getTagarray();
                    lati = response.body().getLati();
                    longi = response.body().getLongi();
                    lname = response.body().getLocationName();


                    rtitle = response.body().getTitle();
                    rcontent = response.body().getContent();

                    setGlobalValue();
                    showUriList(ImageArray, image_row);
                    showTagList(tagArray, tag_row);
                    showRowList(ImageArray, scrollopen);

                    Glide.with(getApplicationContext())
                            .load(ImageArray.get(0))
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(top_background);

                    top_title.setText(rtitle);

                    String profile = response.body().getProfile();

                    Glide.with(getApplicationContext())
                            .load(profile)
                            .error(R.drawable.mypage_profile_default_250x250)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(top_profile);

                    top_date.setText(response.body().getDate());
                    top_name.setText(response.body().getName() + "님의 필름");
                    popup_id.setText(response.body().getName());
                    view_text.setText(response.body().getVcount());
                    like_count.setText(String.valueOf(response.body().getLikecount()));
                    comment_count.setText(response.body().getCommentcount());
                    title.setText(response.body().getTitle());
                    content.setText(rcontent);
                    locationName.setText(lname);

                    popupc = response.body().getPopupc();
                    Data.upid = popupc;
                    userCheck = response.body().getUserclick();
                    bmCheck = response.body().getBookm();
                    flCheck = response.body().getFallo();

                    if (userCheck == 1) {
                        top_like.setBackgroundResource(R.drawable.film_likeon_btn_70x27);
                    }

                    if (bmCheck == 1) {
                        bookmark.setBackgroundResource(R.drawable.film_scrapon_btn_24x24);
                    }
                    if (flCheck == 1) {
                        top_fallow.setBackgroundResource(R.drawable.film_fallowing_btn_70x27);
                    }

                    commentList();

                    getFoodData(FOOD_KOREAN);

                }
            }

            @Override
            public void onFailure(Call<info> call, Throwable t) {
                t.printStackTrace();
                call.cancel();
            }
        });
    }

    private void showUriList(List<String> uriList, LinearLayout lin) {

        lin.removeAllViews();
        lin.setVisibility(View.VISIBLE);

        int wdpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 130, getResources().getDisplayMetrics());
        int htpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 130, getResources().getDisplayMetrics());

        for (final String uri : uriList) {
            View imageHolder = LayoutInflater.from(this).inflate(R.layout.film_image_item, null);
            ImageView thumbnail = (ImageView) imageHolder.findViewById(R.id.info_image_item);

            Glide.with(this)
                    .load(uri)
                    .thumbnail(0.3f)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(thumbnail);

            lin.addView(imageHolder);

            thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent go = new Intent(getApplicationContext(), ImageInfoActivity.class);
                    go.putExtra("image", uri);
                    startActivity(go);
                }
            });

            FrameLayout.LayoutParams param = new FrameLayout.LayoutParams(wdpx, htpx);
            param.setMargins(3, 0, 0, 0);
            thumbnail.setLayoutParams(param);
        }
    }

    private void showTagList(List<Integer> tagList, LinearLayout lin) {

        lin.removeAllViews();
        lin.setVisibility(View.VISIBLE);

        int wdpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
        int htpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 56, getResources().getDisplayMetrics());

        for (Integer uri : tagList) {
            View imageHolder = LayoutInflater.from(this).inflate(R.layout.film_image_item, null);
            ImageView thumbnail = (ImageView) imageHolder.findViewById(R.id.info_image_item);

            Glide.with(this)
                    .load(tagImage[uri])
                    .thumbnail(0.3f)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(thumbnail);

            lin.addView(imageHolder);

            FrameLayout.LayoutParams param = new FrameLayout.LayoutParams(wdpx, htpx);
            if (uri != tagList.get(0)) {
                param.setMargins(60, 0, 0, 0);
            }

            thumbnail.setLayoutParams(param);
        }
    }

    private void showRowList(List<String> uriList, LinearLayout lin) {

        lin.removeAllViews();

        for (final String uri : uriList) {


            View imageHolder = LayoutInflater.from(this).inflate(R.layout.image_row_list, null);
            ImageView thumbnail = (ImageView) imageHolder.findViewById(R.id.image_row_view);

            Glide.with(this)
                    .load(uri)
                    .thumbnail(0.3f)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(thumbnail);

            lin.addView(imageHolder);

            thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent go = new Intent(getApplicationContext(), ImageInfoActivity.class);
                    go.putExtra("image", uri);
                    startActivity(go);
                }
            });

            FrameLayout.LayoutParams param = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            param.setMargins(0, 2, 0, 0);
            thumbnail.setLayoutParams(param);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {   // 지도에 좌표랑 마커 전달

        googleMap.clear();

        if (mapclear == 1) {
            googleMap.clear();
            mapclear--;
        }

        LatLng latLng = new LatLng(lati, longi);

        int height = 120;
        int width = 90;
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.marker_b);
        Bitmap bb = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(bb, width, height, false);

        googleMap.addMarker(new MarkerOptions().position(latLng).title(lname).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
        googleMap.getUiSettings().setScrollGesturesEnabled(false);

        if (openb == 1) {

            for (int i = 0; i < FoodArray.size(); i++) {
                double a = Double.parseDouble(FoodArray.get(i).getMapy());
                double b = Double.parseDouble(FoodArray.get(i).getMapx());

                BitmapDrawable bitmapdrawa = (BitmapDrawable) getResources().getDrawable(R.drawable.marker_a);
                Bitmap ab = bitmapdrawa.getBitmap();
                Bitmap smallMarkera = Bitmap.createScaledBitmap(ab, width, height, false);
                LatLng la = new LatLng(a, b);

                googleMap.addMarker(new MarkerOptions().position(la).title(FoodArray.get(i).getTitle()).alpha(0.8f)
                        .icon(BitmapDescriptorFactory.fromBitmap(smallMarkera)));
            }
            openb = 0;
        }

    }

    private void comment(String bid, String comment) {

        if (comment.length() > 0) {

            if (DOUBLE_CHECK == 0) {

                DOUBLE_CHECK++;
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("답글 작성중");
                progressDialog.show();

                Call<Value> call = api.comment_upload(bid, Data.userID, comment);
                call.enqueue(new Callback<Value>() {
                    @Override
                    public void onResponse(Call<Value> call, Response<Value> response) {

                        if (response.isSuccessful()) {

                            String value = response.body().getValue();
                            String message = response.body().getMessage();

                            if (value.equals("1")) {

                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                comment_editText.setText("");

                                commentList();

                                progressDialog.cancel();

                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(comment_editText.getWindowToken(), 0);

                                DOUBLE_CHECK--;
                            } else {
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                                DOUBLE_CHECK--;
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<Value> call, Throwable t) {
                        t.printStackTrace();
                        call.cancel();
                        progressDialog.cancel();
                        DOUBLE_CHECK--;
                    }
                });
            }
        } else {
            Toast.makeText(getApplicationContext(), "답글을 작성해주세요", Toast.LENGTH_SHORT).show();
        }
    }

    private void commentList() {

        commentList = new ArrayList<>();

        Call<Value> call = api.comment(bid);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()) {

                    commentList = response.body().getComment();
                    adapter = new comment_adapter(commentList, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                t.printStackTrace();
                call.cancel();
            }
        });

    }

    private void commentScrollPosition() {

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                info_scrollv.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        food_recyclerview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                info_scrollv.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    private void sendlikeData(int vali) {

        Call<Value> call = api.b_like(bid, Data.userID, vali);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()) {
                    String value = response.body().getValue();

                    if (value.equals("0")) {
                        top_like.setBackgroundResource(R.drawable.film_likeon_btn_70x27);
                    } else {
                        top_like.setBackgroundResource(R.drawable.film_likeoff_btn_70x27);
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

    private void bookMark(int vali) {

        Call<Value> call = api.bm_btn(bid, Data.userID, vali);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()) {
                    String value = response.body().getValue();

                    if (value.equals("0")) {
                        bookmark.setBackgroundResource(R.drawable.film_scrapon_btn_24x24);
                    } else {
                        bookmark.setBackgroundResource(R.drawable.film_scrapoff_btn_24x24);
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

    private void fallow(int vali) {

        Call<Value> call = api.fl_btn(bid, Data.userID, vali);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()) {
                    String value = response.body().getValue();

                    if (value.equals("0")) {
                        top_fallow.setBackgroundResource(R.drawable.film_fallowing_btn_70x27);
                    } else {
                        top_fallow.setBackgroundResource(R.drawable.film_fallow_btn_70x27);
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


    private void getFoodData(String value) {

        RetrofitApi foodapi = RetrofitFoodApi.getRetrofit().create(RetrofitApi.class);
        RecyclerView.LayoutManager flayoutmanager = new LinearLayoutManager(getApplicationContext());

        food_recyclerview.setLayoutManager(flayoutmanager);
        food_recyclerview.setHasFixedSize(true);
        food_recyclerview.setItemAnimator(new DefaultItemAnimator());
        food_recyclerview.scrollToPosition(0);

        if (FoodArray.size() != 0) {
            FoodArray.clear();
        }

        Call<Value> call = foodapi.response(String.valueOf(longi), String.valueOf(lati), value);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()) {

                    FoodArray = response.body().getResponse().getBody().getItems().getItem();

                    if (FoodArray.size() > 0) {

                        food_recyclerview.setVisibility(View.VISIBLE);
                        not_food.setVisibility(View.GONE);

                        f_adapter = new food_adapater(FoodArray, getApplicationContext());
                        food_recyclerview.setAdapter(f_adapter);
                        f_adapter.notifyDataSetChanged();
                        openb++;

                        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(info_maps);
                        supportMapFragment.getMapAsync(BoardInfoActivity.this);

                    } else {
                        MarkerClear();
                        food_recyclerview.setVisibility(View.GONE);
                        not_food.setVisibility(View.VISIBLE);
                    }
                } else {
                    MarkerClear();
                    food_recyclerview.setVisibility(View.VISIBLE);
                    not_food.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                t.printStackTrace();
                call.cancel();
                MarkerClear();
                food_recyclerview.setVisibility(View.GONE);
                not_food.setVisibility(View.VISIBLE);
            }
        });
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

    }

    private void MarkerClear() {
        mapclear++;
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(info_maps);
        supportMapFragment.getMapAsync(BoardInfoActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_REVISE && resultCode == RESULT_OK) {   // 지도
            String title1 = data.getStringExtra("title");
            String content1 = data.getStringExtra("content");
            title.setText(title1);
            top_title.setText(title1);
            content.setText(content1);
        } else if (requestCode == REQUEST_CODE_USERPROFILE && resultCode == RESULT_OK) {

            String a = data.getStringExtra("flcheck");

            if (a.equals("0")) {
                top_fallow.setBackgroundResource(R.drawable.film_fallowing_btn_70x27);
            } else {
                top_fallow.setBackgroundResource(R.drawable.film_fallow_btn_70x27);
            }
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }


}
