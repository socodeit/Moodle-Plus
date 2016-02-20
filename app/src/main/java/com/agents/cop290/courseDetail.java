package com.agents.cop290;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.agents.cop290.R;

import java.util.ArrayList;
import java.util.List;

import layout.aboutFragment;
import layout.assignmentFragment;
import layout.gradesFragment;
import layout.threadsFragment;


public class courseDetail extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    String tab[] ={"course","notification","grades","logout"};
    int icon[]={R.drawable.iitd1,R.drawable.iitd2,R.drawable.iitd4,R.drawable.iitd3};
    Toolbar bar;
    RecyclerView rec;
    RecyclerView.Adapter adp;
    RecyclerView.LayoutManager mang;
    DrawerLayout drawer;
    ActionBarDrawerToggle togg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        rec =(RecyclerView) findViewById(R.id.RecyclerView);
        rec.setHasFixedSize(true);
        adp= new MyAdapter(tab,icon,"ajay","ajaymahicha@gmail.com",R.drawable.iitd3);
        rec.setAdapter(adp);
        mang =new LinearLayoutManager(this);
        rec.setLayoutManager(mang);
        bar =(Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(bar);
        drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
        togg=new ActionBarDrawerToggle(this,drawer,bar,R.string.navigation_drawer_open,R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawer) {
                super.onDrawerOpened(drawer);
            }
        //setContentView(R.layout.activity_course_detail);

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }

        };
        drawer.setDrawerListener(togg); // Drawer Listener set to the Drawer toggle
        togg.syncState();               // Finally we set the drawer toggle sync State

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_about);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_assignment);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_thread);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_grade);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new aboutFragment(), "ABOUT");
        adapter.addFragment(new assignmentFragment(), "ASSIGNMENT");
        adapter.addFragment(new threadsFragment(),"THREADS");
        adapter.addFragment(new gradesFragment(), "GRADES");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
