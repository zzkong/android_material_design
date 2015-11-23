package lico.example.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import lico.example.fragment.CommonListFragment;

/**
 * Created by zwl on 2015/8/31.
 */
public class TabFragmentPagerAdapter extends FragmentPagerAdapter{

    private List<String> mFragmentTitles = new ArrayList<>();

    public TabFragmentPagerAdapter(FragmentManager fm, List<String> strings) {
        super(fm);
        this.mFragmentTitles = strings;
    }

    @Override
    public Fragment getItem(int position) {
        return new CommonListFragment();
    }

    @Override
    public int getCount() {
        return mFragmentTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }
}
