package com.app.seoul.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.seoul.R;
import com.app.seoul.View.Activity.ContactusActivity;
import com.app.seoul.View.Activity.EventActivity;
import com.app.seoul.View.Activity.LoginActivity;
import com.app.seoul.View.Activity.NoticeActivity;
import com.app.seoul.View.Activity.StoreqnaActivity;
import com.app.seoul.View.Activity.TermsActivity;
import com.app.seoul.View.Activity.UserprivitActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ihoyong on 2017. 9. 15..
 */

public class f_setting extends android.support.v4.app.Fragment {

    private Intent intent;

    @OnClick({R.id.setting_contactus, R.id.more_event, R.id.more_notice, R.id.more_twoyong, R.id.more_private,
            R.id.more_office, R.id.more_logout})
    void click(View v) {
        switch (v.getId()) {
            case R.id.setting_contactus:    // 문의하기
                intent = new Intent(getActivity(), ContactusActivity.class);
                startActivity(intent);
                break;
            case R.id.more_event:   // 이벤트
                intent = new Intent(getActivity(), EventActivity.class);
                startActivity(intent);
                break;

            case R.id.more_notice:  // 공지사항
                intent = new Intent(getActivity(), NoticeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.more_twoyong: // 이용약관
                intent = new Intent(getActivity(), TermsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.more_private: // 개인정보 약관
                intent = new Intent(getActivity(), UserprivitActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.more_office:  // 상가문의
                intent = new Intent(getActivity(), StoreqnaActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.more_logout:  // 로그아웃

                intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().finish();
                startActivity(intent);
                break;


        }
    }

    public f_setting() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, v);

        return v;
    }
}
