package lico.example.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import butterknife.ButterKnife;
import lico.example.R;
import lico.example.app.BaseActivity;
import lico.example.fragment.BaseFragment;
import lico.example.fragment.EventFragment;
import lico.example.fragment.MainFragment;
import lico.example.utils.Navigator;

/**
 * Created by zwl on 2015/8/29.
 */
public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Bind(R.id.navigation_view)
    NavigationView navigationView;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private int mCurrentMenuItem;
    private Navigator mNavigator;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_home;
    }
    @Override
    protected void initToolbar() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolbar();
        initNavigator();
        initDrawerView();
        mCurrentMenuItem = R.id.nav_home;
        setNewRootFragment(MainFragment.newInstance());
    }

    private void initNavigator() {
        if(mNavigator != null) return;
        mNavigator = new Navigator(getSupportFragmentManager(), R.id.container);
    }

    private void setNewRootFragment(BaseFragment fragment){
        if(fragment.hasCustomToolbar()){
            hideActionBar();
        }else {
            showActionBar();
        }
        mNavigator.setRootFragment(fragment);
        drawerLayout.closeDrawers();
    }

    private void hideActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar == null) return;
        actionBar.hide();
    }

    private void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar == null) return;
        actionBar.show();
    }

    private void setupToolbar() {
        mToolbar = ButterKnife.findById(this, R.id.toolbar);
        if(mToolbar == null) {
            return;
        }
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar == null) return;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    private void initDrawerView(){
//        View views = getLayoutInflater().inflate(R.layout.navigation_header, null);
//        navigationView.addHeaderView(views);
        View view = navigationView.inflateHeaderView(R.layout.navigation_header);
        SimpleDraweeView avator = (SimpleDraweeView) view.findViewById(R.id.user_avator);
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mDrawerToggle != null && mDrawerToggle.onOptionsItemSelected(item))
            return true;

        if(android.R.id.home == item.getItemId()){
            if(drawerLayout.isDrawerVisible(GravityCompat.START))
                drawerLayout.closeDrawers();
            else
                drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if(mDrawerToggle != null){
            mDrawerToggle.syncState();
        }
    }

    public void openDrawer(){
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if(id == mCurrentMenuItem){
            drawerLayout.closeDrawers();
            return false;
        }
        switch (id){
            case R.id.nav_home:
                setNewRootFragment(MainFragment.newInstance());
                break;
            case R.id.nav_event:
                setNewRootFragment(EventFragment.newInstance());
                break;
            case R.id.nav_resume:
                setNewRootFragment(MainFragment.newInstance());
                break;
            case R.id.nav_look_back:
                setNewRootFragment(MainFragment.newInstance());
                break;
        }
        mCurrentMenuItem = id;
        menuItem.setChecked(true);
        return false;
    }
}
