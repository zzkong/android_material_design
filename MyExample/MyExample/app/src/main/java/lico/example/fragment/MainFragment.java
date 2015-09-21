package lico.example.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import lico.example.R;
import lico.example.adapter.TabFragmentPagerAdapter;

/**
 * Created by zwl 首页 on 2015/8/31.
 */
public class MainFragment extends Fragment {

    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    private TabFragmentPagerAdapter mPagerAdapter;

    public static MainFragment newInstance() {
        MainFragment mainFragment = new MainFragment();
        return mainFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewPager(viewpager);
    }

    private void setupViewPager(ViewPager viewPager){
        mPagerAdapter = new TabFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        mPagerAdapter.addFragment(CommonListFragment.newFragment("Android"), "Android");
        mPagerAdapter.addFragment(CommonListFragment.newFragment("IOS"), "IOS");
        mPagerAdapter.addFragment(CommonListFragment.newFragment("Java"), "Java");


        tabs.setTabsFromPagerAdapter(mPagerAdapter);
        viewPager.setAdapter(mPagerAdapter);
        tabs.setupWithViewPager(viewpager);
    }

}
