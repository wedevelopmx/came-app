package mx.wedevelop.came;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import mx.wedevelop.came.fragment.ServiceSelectionFmt;
import mx.wedevelop.came.fragment.VisitorCard;
import mx.wedevelop.came.fragment.VisitorHistoricalFmt;
import mx.wedevelop.came.fragment.VisitorProfileFmt;
import mx.wedevelop.came.layout.SlidingTabLayout;
import mx.wedevelop.came.model.Service;
import mx.wedevelop.came.model.Visitor;
import mx.wedevelop.came.rest.ServiceGenerator;
import mx.wedevelop.came.rest.VisitorClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisitorActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    public static final String VISITOR = "VISITOR";
    public static final String SERVICE = "SERVICE";

    private AppSectionsPagerAdapter appSectionsPagerAdapter;
    private Visitor visitor;
    private List<Service> serviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor);

        Intent intent = getIntent();
        if(intent != null) {
            visitor = intent.getParcelableExtra(VisitorActivity.VISITOR);
            serviceList = intent.getParcelableArrayListExtra(VisitorActivity.SERVICE);
        }

        //Setup user data
        VisitorCard visitorCard = (VisitorCard) getSupportFragmentManager().findFragmentById(R.id.visitor_card_fmt);
        visitorCard.updateUI(visitor);

        //Setup tabs
        appSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());
        appSectionsPagerAdapter.addPage(getString(R.string.visit), ServiceSelectionFmt.newInstance(serviceList));
        appSectionsPagerAdapter.addPage(getString(R.string.historical), VisitorHistoricalFmt.newInstance());
        appSectionsPagerAdapter.addPage(getString(R.string.profile), VisitorProfileFmt.newInstance(visitor));

        ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(appSectionsPagerAdapter);
        onPageSelected(0);

        SlidingTabLayout mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setOnPageChangeListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public static class AppSectionsPagerAdapter extends FragmentStatePagerAdapter {
        private ServiceSelectionFmt serviceSelectionFmt;
        private List<String> tabList = new ArrayList<String>();
        private List<Fragment> fragmentList = new ArrayList<Fragment>();


        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addPage(String tabName, Fragment frafment) {
            tabList.add(tabName);
            fragmentList.add(frafment);
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }


        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabList.get(position);
        }
    }
}
