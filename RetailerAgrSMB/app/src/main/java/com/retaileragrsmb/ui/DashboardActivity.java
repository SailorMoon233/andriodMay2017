package com.retaileragrsmb.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.retaileragrsmb.R;
import com.retaileragrsmb.common.Constant;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String KEY_USER_TYPE = "user_type";
    private String userType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        userType = getIntent().getStringExtra(KEY_USER_TYPE);

        openDashBoardFragment(userType);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        Fragment f = getSupportFragmentManager().findFragmentById(R.id.content_main_db);
        if (f instanceof DashboardFragment || f instanceof DistributorDashboardFragment){
            finish();
        }else {
            openDashBoardFragment(userType);
        }
    }

    private void openDashBoardFragment(String userType){
        if(userType.equals(Constant.USER_DISTRIBUTOR) || userType.equals(Constant.USER_OPERATOR)){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main_db, DistributorDashboardFragment.newInstance(),
                    Constant.FG_DASHBOARD).commitAllowingStateLoss();
        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main_db, DashboardFragment.newInstance(),
                    Constant.FG_DASHBOARD).commitAllowingStateLoss();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
