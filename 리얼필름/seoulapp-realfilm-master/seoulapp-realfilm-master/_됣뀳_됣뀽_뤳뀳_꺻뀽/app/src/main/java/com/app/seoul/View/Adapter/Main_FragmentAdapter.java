package com.app.seoul.View.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.app.seoul.View.Fragment.f_setting;
import com.app.seoul.View.Fragment.f_home;
import com.app.seoul.View.Fragment.f_profile;
import com.app.seoul.View.Fragment.f_search;

/**
 * Created by ihoyong on 2017. 6. 18..
 */

public class Main_FragmentAdapter extends FragmentStatePagerAdapter {

    int TabCount;

    public Main_FragmentAdapter(FragmentManager main_frag_adapter, int CountTabs) {

        super(main_frag_adapter);

        this.TabCount = CountTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                f_home tab1 = new f_home();
                return tab1;

            case 1:
                f_search tab2 = new f_search();
                return tab2;
            case 2:

                f_profile tab3 = new f_profile();
                return tab3;

            case 3:
                f_setting tab4 = new f_setting();
                return tab4;


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TabCount;
    }
}
