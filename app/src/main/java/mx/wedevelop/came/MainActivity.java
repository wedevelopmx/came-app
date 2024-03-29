package mx.wedevelop.came;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import mx.wedevelop.came.adapter.VisitorAdapter;
import mx.wedevelop.came.model.Service;
import mx.wedevelop.came.model.Visitor;
import mx.wedevelop.came.rest.ServiceGenerator;
import mx.wedevelop.came.rest.VisitorClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    private static final int NEW_VISITOR_REQ = 5000;

    private List<Visitor> visitorList = new ArrayList();
    private List<Service> serviceList = new ArrayList();
    private VisitorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewVisitorActivity.class);
                startActivityForResult(intent, NEW_VISITOR_REQ);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        adapter = new VisitorAdapter(this, visitorList);
        ListView visitorListView = (ListView) findViewById(R.id.visitor_list_view);
        visitorListView.setAdapter(adapter);
        visitorListView.setOnItemClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        queryAllVisitors();
        queryAllServices();
    }

    private void queryAllVisitors() {
        // Create a very simple REST adapter which points the GitHub API endpoint.
        VisitorClient client = ServiceGenerator.createService(VisitorClient.class);

        // Fetch and print a list of the contributors to this library.
        Call<List<Visitor>> call = client.getVisitors();

        call.enqueue(new Callback<List<Visitor>>() {
            @Override
            public void onResponse(Call<List<Visitor>> call, Response<List<Visitor>> response) {
                visitorList.clear();
                visitorList.addAll(response.body());
                adapter.notifyDataSetChanged();
                RelativeLayout rl = (RelativeLayout)findViewById(R.id.progress_screen);
                rl.setVisibility(ViewGroup.GONE);
            }

            @Override
            public void onFailure(Call<List<Visitor>> call, Throwable t) {
                Log.i(MainActivity.class.getName(), t.toString());
            }
        });
    }

    private void queryAllServices() {
        // Create a very simple REST adapter which points the GitHub API endpoint.
        VisitorClient client = ServiceGenerator.createService(VisitorClient.class);

        // Fetch and print a list of the contributors to this library.
        Call<List<Service>> call2 = client.getServices();

        call2.enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                serviceList.clear();
                serviceList.addAll(response.body());
            }

            @Override
            public void onFailure(Call<List<Service>> call, Throwable t) {
                Log.i(MainActivity.class.getName(), t.toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
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

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long time) {
        Visitor currentVisitor = (Visitor)adapterView.getItemAtPosition(position);

        Intent intent = new Intent(this, VisitorActivity.class);
        intent.putExtra(VisitorActivity.VISITOR, currentVisitor);
        intent.putParcelableArrayListExtra(VisitorActivity.SERVICE, (ArrayList<Service>) serviceList);
        startActivity(intent);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NEW_VISITOR_REQ && resultCode == RESULT_OK) {
            queryAllVisitors();
        }
    }
}
