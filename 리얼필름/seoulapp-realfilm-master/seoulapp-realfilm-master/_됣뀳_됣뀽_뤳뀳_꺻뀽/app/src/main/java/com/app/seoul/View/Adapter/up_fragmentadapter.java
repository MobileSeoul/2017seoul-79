package com.app.seoul.View.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.app.seoul.View.Fragment.ChileFragment.up_a;
import com.app.seoul.View.Fragment.ChileFragment.up_b;
import com.app.seoul.View.Fragment.ChileFragment.up_c;

/**
 * Created by ihoyong on 2017. 6. 18..
 */

public class up_fragmentadapter extends FragmentStatePagerAdapter {

    int TabCount;

    public up_fragmentadapter(FragmentManager main_frag_adapter, int CountTabs) {

        super(main_frag_adapter);

        this.TabCount = CountTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                up_a tab1 = new up_a();
                return tab1;

            case 1:
                up_b tab2 = new up_b();
                return tab2;
            case 2:

                up_c tab3 = new up_c();
                return tab3;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TabCount;
    }
}
