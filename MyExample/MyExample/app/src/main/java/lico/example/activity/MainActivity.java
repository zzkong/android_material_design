package lico.example.activity;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import lico.example.R;
import lico.example.adapter.BaseAdapterHelper;
import lico.example.adapter.BaseQuickAdapter;
import lico.example.adapter.BaseRecyclerViewAdapter;
import lico.example.adapter.FeedAdapter;
import lico.example.adapter.ImageAdapter;
import lico.example.adapter.SimpleRecyclerAdapter;
import lico.example.app.BaseActivity;
import lico.example.bean.ImageInfo;
import lico.example.utils.NetUtils;


/**
 * Created by zwl on 15/5/22.
 */

public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.refresher)
    SwipeRefreshLayout refresher;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.progress_wheel)
    ProgressWheel progressWheel;
    @Bind(R.id.content)
    FrameLayout content;
    @Bind(R.id.navigation_view)
    NavigationView navigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.root)
    LinearLayout root;
    private FeedAdapter feedAdapter;
    private MenuItem inboxMenuItem;
    private ActionBarDrawerToggle mDrawerToggle;
    private List<ImageInfo> imageInfos;
    private SimpleRecyclerAdapter mAdapter;
    private ImageAdapter mAdapters;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDrawerView();
        initRecyclerView();
    }

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    private void initRecyclerView() {
        showProgressWheel(true);
        imageInfos = new ArrayList<ImageInfo>();


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mAdapter = new SimpleRecyclerAdapter<ImageInfo>(MainActivity.this,imageInfos, R.layout.view_image) {
            @Override
            protected void convert(BaseAdapterHelper helper, ImageInfo item) {
                helper.setText(R.id.title, item.title);
            }

            @Override
            protected Animator[] getAnimators(View view) {
                return new Animator[0];
            }
        };
        mAdapter.setOnInViewClickListener(R.id.root_layout,
                new BaseQuickAdapter.onInternalClickListenerImpl<ImageInfo>() {
                    @Override
                    public void OnClickListener(View parentV, View v, Integer position, ImageInfo values) {
                        Intent intent = new Intent(MainActivity.this, SwipeListViewActivity.class);
                        startActivity(intent);
                    }
                });


        mAdapters = new ImageAdapter(imageInfos, this);
        mAdapters.setOnInViewClickListener(R.id.root_layout,
                new BaseRecyclerViewAdapter.onInternalClickListenerImpl<ImageInfo>() {
                    @Override
                    public void OnClickListener(View parentV, View v, Integer position, ImageInfo values) {
                        Intent intent = new Intent(MainActivity.this, SwipeListViewActivity.class);
                        startActivity(intent);
                    }
                });

        mAdapters.setFirstOnly(false);
        mAdapters.setDuration(300);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


    //    recyclerView.setAdapter(mAdapter);

        showProgressWheel(false);
  //      refresher.setColorSchemeColors(getColorPrimary());
        refresher.setOnRefreshListener(this);
    }

    private void initDrawerView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Snackbar.make(content, menuItem.getTitle() + " pressed", Snackbar.LENGTH_LONG).show();
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, 0, 0) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                toolbar.setTitle("天天");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
                toolbar.setTitle("万里行");
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openOrCloseDrawer();
                }
            });
        }
    }

    private void openOrCloseDrawer() {
        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawers();
        } else {
            drawerLayout.openDrawer(navigationView);
        }
    }

    private void showProgressWheel(boolean visible) {
    //    progressWheel.setBarColor(getColorPrimary());
        if (visible) {
            if (!progressWheel.isSpinning()) {
                progressWheel.spin();
            } else {
                progressWheel.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (progressWheel.isSpinning()) {
                            progressWheel.stopSpinning();
                        }
                    }
                }, 500);
            }
        }
    }

    @Override
    public void onRefresh() {
        refresher.setRefreshing(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected int getContentViewLayoutID() {
        return 0;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

}
