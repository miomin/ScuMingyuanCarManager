package scu.mingyuan.com.carmanager.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import scu.mingyuan.com.carmanager.R;
import scu.mingyuan.com.carmanager.baseactivity.BaseActivity;
import scu.mingyuan.com.carmanager.fragment.MapFragment;
import scu.mingyuan.com.carmanager.fragment.MusicFragment;
import scu.mingyuan.com.carmanager.fragment.MyCarFragment;
import scu.mingyuan.com.carmanager.fragment.OrderFragment;

/**
 * Created by 莫绪旻 on 16/2/29.
 *
 */
public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        // 获取Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 获取并初始化Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_home);
        navigationView.setNavigationItemSelectedListener(this);

        // 初始化首页的Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = MapFragment.newInstance();
        fragmentTransaction.add(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }


    // 相应抽屉选项的监听器
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_home) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment mapFragment = MapFragment.newInstance();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragmentContainer, mapFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.nav_mycar) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment mapFragment = MyCarFragment.newInstance();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragmentContainer, mapFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.nav_order) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment mapFragment = OrderFragment.newInstance();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragmentContainer, mapFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.nav_music) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment mapFragment = MusicFragment.newInstance();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragmentContainer, mapFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        } else if (id == R.id.nav_query) {

        } else if (id == R.id.nav_setting) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
