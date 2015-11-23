package lico.example.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

import butterknife.Bind;
import lico.example.InterfaceView.MainFragmentView;
import lico.example.R;
import lico.example.adapter.TabFragmentPagerAdapter;
import lico.example.presenter.Presenter;
import lico.example.presenter.impl.MainFragmentPresenterImpl;

/**
 * Created by zwl 首页 on 2015/8/31.
 */
public class MainFragment extends BaseFragment implements MainFragmentView {

    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    private TabFragmentPagerAdapter mPagerAdapter;
    private Presenter mMainPresenterImpl;

    @Override
    protected int getTitle() {
        return R.string.string_tab;
    }

    @Override
    public boolean hasCustomToolbar() {
        return true;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initViewsAndEvents() {
        mMainPresenterImpl = new MainFragmentPresenterImpl(getActivity(), this);
        mMainPresenterImpl.initialized();
    }

    @Override
    public void initializePagerViews(final List<String> titles) {
        viewpager.setOffscreenPageLimit(1);
        mPagerAdapter = new TabFragmentPagerAdapter(getActivity().getSupportFragmentManager(), titles);
        tabs.setTabsFromPagerAdapter(mPagerAdapter);
        viewpager.setAdapter(mPagerAdapter);
        tabs.setupWithViewPager(viewpager);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                CommonListFragment fragment = (CommonListFragment) viewpager.getAdapter().instantiateItem(viewpager, position);
                fragment.onPageSelected(position, titles.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onFirstUserVisible() {
    }

    @Override
    protected void onUserVisible() {
    }

    @Override
    protected void onUserInvisible() {
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }
}
