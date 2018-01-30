package com.app.seoul.View.Activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.app.seoul.R;
import com.app.seoul.View.Adapter.Main_FragmentAdapter;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private Main_FragmentAdapter adapter;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.main_upload)
    ImageButton imageButton;

    @OnClick({R.id.main_upload, R.id.main_alarm})
    void click(View v) {
        switch (v.getId()) {
            case R.id.main_upload:
                checkPermission();
                break;
            case R.id.main_alarm:
                Intent al = new Intent(getApplicationContext(), AlarmActivity.class);
                startActivity(al);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        TabView();
    }

    /* 뷰페이져와 텝뷰 */
    private void TabView() {


        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.tab_home));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.tab_search));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.tab_mypage));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.tab_more));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        adapter = new Main_FragmentAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab LayoutTab) {

                viewPager.setCurrentItem(LayoutTab.getPosition());

                if (LayoutTab.getPosition() == 0 || LayoutTab.getPosition() == 1 || LayoutTab.getPosition() == 2) {
                    imageButton.setVisibility(View.VISIBLE);
                } else {
                    imageButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab LayoutTab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab LayoutTab) {

            }
        });
    }

    private void checkPermission() {
        PermissionListener camerapermission = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Intent intent = new Intent(getApplicationContext(), UploadActivity.class);
                startActivity(intent);

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(getApplicationContext(), "권한을 허용해주세요", Toast.LENGTH_SHORT).show();

            }
        };

        new TedPermission(getApplicationContext())
                .setPermissionListener(camerapermission)
                .setDeniedMessage("권한 설정 동의를 안하신다면\\n\\이곳에서 설정해 주세요. [설정] > [권한]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();

    }
}
