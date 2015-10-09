package lico.example.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
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
        Log.e("", "------------------------------------我了个去");
        setupViewPager(viewpager);
    }

    private void setupViewPager(ViewPager viewPager){
        viewpager.setOffscreenPageLimit(1);
        mPagerAdapter = new TabFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        mPagerAdapter.addFragment(CommonListFragment.newFragment("美女"), "美女");
        mPagerAdapter.addFragment(CommonListFragment.newFragment("动漫"), "动漫");
        mPagerAdapter.addFragment(CommonListFragment.newFragment("明星"), "明星");
        mPagerAdapter.addFragment(CommonListFragment.newFragment("汽车"), "汽车");
        mPagerAdapter.addFragment(CommonListFragment.newFragment("摄影"), "摄影");
        mPagerAdapter.addFragment(CommonListFragment.newFragment("美食"), "美食");


        tabs.setTabsFromPagerAdapter(mPagerAdapter);
        viewPager.setAdapter(mPagerAdapter);
        tabs.setupWithViewPager(viewpager);
    }

}
