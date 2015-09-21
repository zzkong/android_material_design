package lico.example.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import lico.example.R;
import lico.example.app.BaseActivity;
import lico.example.fragment.EventFragment;
import lico.example.fragment.MainFragment;

/**
 * Created by zwl on 2015/8/29.
 */
public class HomeActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.container)
    FrameLayout container;
    @Bind(R.id.navigation_view)
    NavigationView navigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_home;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar(toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initDrawerView();
        init();
    }

    private void init(){
        Fragment fragment = MainFragment.newInstance();
        getSupportActionBar().setTitle("Android");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment, "text").commit();
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
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                disposeMenuAction(menuItem);
                drawerLayout.closeDrawers();

                return true;
            }
        });

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

    private void disposeMenuAction(MenuItem item){
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.nav_home:
                fragment = MainFragment.newInstance();
            break;
            case R.id.nav_event:
                fragment = EventFragment.newInstance();
                break;
            case R.id.nav_resume:
                fragment = EventFragment.newInstance();
                break;
            case R.id.nav_look_back:
                fragment = EventFragment.newInstance();
                break;
        }
        getSupportActionBar().setTitle("Android");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment, "text").commit();
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
}
