package com.agents.cop290;

import android.content.Intent;
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
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import layout.aboutFragment;
import layout.assignmentFragment;
import layout.gradesFragment;
import layout.threadsFragment;


public class courseDetail extends AppCompatActivity {
   // private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static String courseCode="cop290";
    public static courseDetail instance;

    public static courseDetail getinstance()
    {
        return instance;
    }

    String tab[] ={"Profile","Courses","Notifications","Grades","Log Out"};
    int icon[]={R.drawable.p,R.drawable.co,R.drawable.notifications,R.drawable.ic_grade,R.drawable.logout};
    Toolbar bar;
    RecyclerView rec;
    RecyclerView.Adapter adp;
    RecyclerView.LayoutManager mang;
    DrawerLayout drawer;
    ActionBarDrawerToggle togg;
Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        Intent i = getIntent();
         extras = i.getExtras();
        courseCode=extras.getString("COURSECODE");
        instance=this;

        rec =(RecyclerView) findViewById(R.id.Rview);
        //bar =(Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(bar);
        rec.setHasFixedSize(true);
        adp= new MyAdapter(tab,icon,extras.getString("name"),extras.getString("email"),R.drawable.iitd2);
        rec.setAdapter(adp);

        final GestureDetector mGestureDetector = new GestureDetector(courseDetail.this, new GestureDetector.SimpleOnGestureListener() {

            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });
        rec.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());


                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    drawer.closeDrawers();
                    // Toast.makeText(courseDetail.this, "The Item Clicked is: " + recyclerView.getChildPosition(child), Toast.LENGTH_SHORT).show();
                    switch (recyclerView.getChildAdapterPosition(child)) {
                        case 1:
                            Intent nextActivity = new Intent(courseDetail.this, profile.class);
                            nextActivity.putExtras(extras);
                            startActivity(nextActivity);
                            break;
                        case 3:
                            nextActivity = new Intent(courseDetail.this, notifications.class);
                            startActivity(nextActivity);
                            break;
                        case 2:
                            nextActivity = new Intent(courseDetail.this, CourseListStudents.class);
                            nextActivity.putExtras(extras);
                            startActivity(nextActivity);
                            break;
                        case 4:
                            nextActivity = new Intent(courseDetail.this, grades.class);
                            startActivity(nextActivity);
                            break;
                        case 5:
                            LoginActivity.cookie = "";
                            nextActivity = new Intent(courseDetail.this, LoginActivity.class);
                            startActivity(nextActivity);
                            break;

                    }


                    return true;

                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        mang =new LinearLayoutManager(this);
        rec.setLayoutManager(mang);
        drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
        togg=new ActionBarDrawerToggle(this,drawer,null,R.string.navigation_drawer_open,R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawer) {
                super.onDrawerOpened(drawer);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }

        };
        drawer.setDrawerListener(togg); // Drawer Listener set to the Drawer toggle
        togg.syncState();               // Finally we set the drawer toggle sync State

//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_courses_list__students_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_about);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_assignment);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_thread);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_grade);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Bundle detail = new Bundle();
        detail.putString("courseCode", courseCode);

        aboutFragment aboutFragmentobj = new aboutFragment();
        assignmentFragment assignmentFragmentobj= new assignmentFragment();
        threadsFragment threadsFragmentobj = new threadsFragment();
        gradesFragment gradesFragmentobj = new gradesFragment();

        aboutFragmentobj.setArguments(detail);
        assignmentFragmentobj.setArguments(detail);
        threadsFragmentobj.setArguments(detail);
        gradesFragmentobj.setArguments(detail);

        adapter.addFragment(aboutFragmentobj, "ABOUT");
        adapter.addFragment(assignmentFragmentobj, "ASSIGNMENT");
        adapter.addFragment(threadsFragmentobj,"THREADS");
        adapter.addFragment(gradesFragmentobj, "GRADES");
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