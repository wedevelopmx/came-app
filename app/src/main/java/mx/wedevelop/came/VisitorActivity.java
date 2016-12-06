package mx.wedevelop.came;

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

    private AppSectionsPagerAdapter appSectionsPagerAdapter;
    //private List<Service> serviceList = new ArrayList<Service>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor);

        VisitorCard visitorCard = (VisitorCard) getSupportFragmentManager().findFragmentById(R.id.visitor_card_fmt);
        Visitor visitor = getIntent().getParcelableExtra(VisitorActivity.VISITOR);
        visitorCard.updateUI(visitor);

        appSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

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

        // Create a very simple REST adapter which points the GitHub API endpoint.
        VisitorClient client = ServiceGenerator.createService(VisitorClient.class);

        // Fetch and print a list of the contributors to this library.
        Call<List<Service>> call = client.getServices();

        call.enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                appSectionsPagerAdapter.updateServiceList(response.body());
            }

            @Override
            public void onFailure(Call<List<Service>> call, Throwable t) {
                Log.i(MainActivity.class.getName(), t.toString());
            }
        });

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
//        double quantity, cost;
//        ReportListFragment fragment = (ReportListFragment) appSectionsPagerAdapter.getItem(position);
//        Bundle bundle = fragment.getArguments();
//        quantity = bundle.getDouble(ReportListFragment.QUANTITY);
//        cost = bundle.getDouble(ReportListFragment.COST);
//
//        SalesSummaryFragment salesSummaryFragment = (SalesSummaryFragment) getSupportFragmentManager().findFragmentById(R.id.sales_summary_fmt);
//        salesSummaryFragment.updateUI((int)quantity, (int)cost);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public static class AppSectionsPagerAdapter extends FragmentStatePagerAdapter {
        private ServiceSelectionFmt serviceSelectionFmt;
        private List<Service> serviceList = new ArrayList<Service>();


        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            serviceSelectionFmt = ServiceSelectionFmt.newInstance(serviceList);
        }

        @Override
        public Fragment getItem(int i) {
            return serviceSelectionFmt;
        }

        public void updateServiceList(List<Service> serviceList) {
            serviceSelectionFmt.notifyDataChanged(serviceList);
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Visita";
                case 1:
                    return "Historial";
                case 2:
                    return "Perfil";
                case 3:
                    return "Comentarios";
            }
            return "";
        }
    }
}
