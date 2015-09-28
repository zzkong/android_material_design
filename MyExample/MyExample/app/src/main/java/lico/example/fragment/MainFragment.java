package lico.example.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import lico.example.R;
import lico.example.adapter.TabFragmentPagerAdapter;

/**
 * Created by zwl 首页 on 2015/8/31.
 */
public class MainFragment extends BaseFragment {

    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    private TabFragmentPagerAdapter mPagerAdapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    protected int getTitle() {
        return R.string.string_tab;
    }

    @Override
    public boolean hasCustomToolbar() {
        return true;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_main;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewpager.setOffscreenPageLimit(0);
        setupViewPager(viewpager);
    }

    private void setupViewPager(ViewPager viewPager){
        viewpager.setOffscreenPageLimit(1);
        mPagerAdapter = new TabFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        mPagerAdapter.addFragment(CommonListFragment.newFragment("美女"), "Android");
        mPagerAdapter.addFragment(CommonListFragment.newFragment("动漫"), "IOS");
        mPagerAdapter.addFragment(CommonListFragment.newFragment("明星"), "Java");
        mPagerAdapter.addFragment(CommonListFragment.newFragment("汽车"), "Android");
        mPagerAdapter.addFragment(CommonListFragment.newFragment("摄影"), "IOS");
        mPagerAdapter.addFragment(CommonListFragment.newFragment("美食"), "Java");


        tabs.setTabsFromPagerAdapter(mPagerAdapter);
        viewPager.setAdapter(mPagerAdapter);
        tabs.setupWithViewPager(viewpager);
    }

}
