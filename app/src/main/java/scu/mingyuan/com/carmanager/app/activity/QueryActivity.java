package scu.mingyuan.com.carmanager.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import scu.mingyuan.com.carmanager.R;
import scu.mingyuan.com.carmanager.app.baseactivity.BaseActivity;
import scu.mingyuan.com.carmanager.app.fragment.QueryBreakRuleFragment;
import scu.mingyuan.com.carmanager.app.fragment.QueryTrafficControlFragment;

/**
 * Created by 莫绪旻 on 16/3/19.
 */
public class QueryActivity extends BaseActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, QueryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 显示回退键
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (NavUtils.getParentActivityName(this) != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return QueryBreakRuleFragment.newInstance();
                case 1:
                    return QueryTrafficControlFragment.newInstance();
                case 2:
                    return QueryBreakRuleFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getString(R.string.break_rule_query);
                case 1:
                    return getResources().getString(R.string.traffic_control);
                case 2:
                    return getResources().getString(R.string.gadgets_query);
            }
            return null;
        }
    }
}