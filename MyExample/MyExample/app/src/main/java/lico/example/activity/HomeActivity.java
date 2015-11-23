package lico.example.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import butterknife.ButterKnife;
import lico.example.InterfaceView.HomeView;
import lico.example.R;
import lico.example.app.BaseActivity;
import lico.example.bean.UserInfo;
import lico.example.fragment.BaseFragment;
import lico.example.fragment.EventFragment;
import lico.example.fragment.MainFragment;
import lico.example.presenter.HomePresenter;
import lico.example.presenter.impl.HomePresenterImpl;
import lico.example.utils.BitmapUtils;
import lico.example.utils.NetUtils;

/**
 * Created by zwl on 2015/8/29.
 */
public class HomeActivity extends BaseActivity implements HomeView, NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.navigation_view)
    NavigationView navigationView;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    ImageView mHeadBg;

    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private int mCurrentMenuItem;
    private FragmentManager mFragmentManager;
    private MainFragment mMainFragment;
    private EventFragment mEventFragment;
    private HomePresenter mHomePresenter = null;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_home;
    }

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    protected void initViewsAndEvents() {
        mHomePresenter = new HomePresenterImpl(this, this);
        mHomePresenter.initialized();
    }

    @Override
    public void initalizeViews(UserInfo userInfo) {
        setupToolbar();
        initDrawerView(userInfo);
        mFragmentManager = getSupportFragmentManager();
        mCurrentMenuItem = R.id.nav_home;
        setNewRootFragment(new MainFragment(), 0);
    }

    @Override
    public void setHeadBg(Bitmap bitmap) {
        mHeadBg.setImageBitmap(bitmap);
    }

    private void setNewRootFragment(BaseFragment fragment, int index) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        showOrhideActionBar(!fragment.hasCustomToolbar());
        hideFragment(transaction);
        switch (index) {
            case 0:
                mMainFragment = (MainFragment) mFragmentManager.findFragmentByTag("main");
                if (mMainFragment == null) {
                    mMainFragment = new MainFragment();
                    transaction.add(R.id.container, mMainFragment, "main");
                } else {
                    transaction.show(mMainFragment);
                }
                break;
            case 1:
                mEventFragment = (EventFragment) mFragmentManager.findFragmentByTag("event");
                if (mEventFragment == null) {
                    mEventFragment = new EventFragment();
                    transaction.add(R.id.container, mEventFragment, "event");
                } else {
                    transaction.show(mEventFragment);
                }
                break;
        }
        transaction.commit();
        drawerLayout.closeDrawers();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (mMainFragment != null) {
            transaction.hide(mMainFragment);
        }
        if (mEventFragment != null) {
            transaction.hide(mEventFragment);
        }
    }

    private void showOrhideActionBar(boolean isShow) {
        ActionBar actionBar = getSupportActionBar();
        if (null == actionBar) return;
        if (isShow) {
            actionBar.show();
        } else {
            actionBar.hide();
        }
    }

    private void setupToolbar() {
        mToolbar = ButterKnife.findById(this, R.id.toolbar);
        if (mToolbar == null) {
            return;
        }
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    private void initDrawerView(UserInfo userInfo) {
//        View views = getLayoutInflater().inflate(R.layout.navigation_header, null);
//        navigationView.addHeaderView(views);
        View view = navigationView.inflateHeaderView(R.layout.navigation_header);
        TextView name = (TextView) view.findViewById(R.id.user_name);
        TextView email = (TextView) view.findViewById(R.id.user_email);
        mHeadBg = (ImageView) view.findViewById(R.id.head_bg);
        SimpleDraweeView avator = (SimpleDraweeView) view.findViewById(R.id.user_avator);

        name.setText(userInfo.name);
        email.setText(userInfo.email);
        avator.setBackgroundResource(R.drawable.avator);
        mHomePresenter.blurBitmap(avator, BitmapUtils.matrixBitmap(this, userInfo.avator));

        avator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PersonalActivity.class);
                startActivity(intent);
            }
        });
        navigationView.setNavigationItemSelectedListener(this);

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, 0, 0) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(mDrawerToggle);
    }

    public void openDrawer() {
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == mCurrentMenuItem) {
            drawerLayout.closeDrawers();
            return false;
        }
        switch (id) {
            case R.id.nav_home:
                setNewRootFragment(new MainFragment(), 0);
                break;
            case R.id.nav_event:
                setNewRootFragment(EventFragment.newInstance(), 1);
                break;
            case R.id.nav_resume:
                setNewRootFragment(new MainFragment(), 2);
                break;
            case R.id.nav_look_back:
                setNewRootFragment(new MainFragment(), 3);
                break;
        }
        mCurrentMenuItem = id;
        menuItem.setChecked(true);
        return false;
    }

    @Override
    protected View getLoadingTargetView() {return null;}

    @Override
    protected boolean isBindEventBusHere() {return false;}

    @Override
    protected void onNetworkDisConnected() {}

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {}

    @Override
    protected boolean toggleOverridePendingTransition() {return false;}

    @Override
    protected void getBundleExtras(Bundle extras) {}

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {return null;}
}
