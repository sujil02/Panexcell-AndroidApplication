package com.panexcell;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.panexcell.Constants.MY_PREFS_NAME;
import static com.panexcell.Constants.SESSIONCONSTANT;
import static com.panexcell.Constants.SESSIONUSER;

public class UserAreaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ListView lvPrograms;
    private ProgramListAdaptar adaptar;
    private List<Programs> programs ;
    private  SharedPreferences.Editor editor;
    SharedPreferences prefs;
    String _id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lvPrograms = (ListView)findViewById(R.id.lv_Details);
        programs = new ArrayList<>();

        final Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    JSONArray recs = jsonObject.getJSONArray("data");
                    boolean success = jsonObject.getBoolean("success");

                    if(success){

                        for (int i = 0; i < recs.length(); ++i) {
                            JSONObject rec = recs.getJSONObject(i);
                            _id = rec.getString("_id");
                            String title = rec.getString("title");
                            String payment = rec.getString("payment");
                            programs.add(new Programs(_id,title,payment,"fff"));
                        }
//                        ArrayList<String> list = new ArrayList<String>();
//                       // JSONArray jsonArray = (JSONArray)jsonObject;
//                        if (jsonArray != null) {
//                            int len = jsonArray.length();
//                            for (int i=0;i<len;i++){
//                                list.add(jsonArray.get(i).toString());
//                            }
//                        }  programs.add(new Programs(1,"Title one","some desciption here",""));
//                        programs.add(new Programs(2,"Title two","some desciption here",""));
//                        programs.add(new Programs(3,"Title three","some desciption here",""));
//                        programs.add(new Programs(4, "Title four", "No descriptions",""));
//                        programs.add(new Programs(5,"Title five","",""));
//                        programs.add(new Programs(6,"Title six","some desciption here",""));
//                        programs.add(new Programs(7,"Title seven","",""));
//                        programs.add(new Programs(8, "Title eight", "",""));

                        adaptar = new ProgramListAdaptar(getApplication(), programs);
                        lvPrograms.setAdapter(adaptar);
                    }
                    else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        prefs = getSharedPreferences(Constants.MY_PREFS_NAME, MODE_PRIVATE);
        UserAreaRequest userAreaRequest = new UserAreaRequest( prefs.getString(SESSIONUSER,null), responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(UserAreaActivity.this);
        requestQueue.add(userAreaRequest);



        lvPrograms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Host call
                //save the data
                Programs p = new Programs();
                p = programs.get(position);
                Intent SpecificProgramActivityIntent = new Intent(UserAreaActivity.this, SpecificProgramActivity.class);
                SpecificProgramActivityIntent.putExtra("id", p.getId());
                UserAreaActivity.this.startActivity(SpecificProgramActivityIntent);
            }
        });


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_aboutUs) {

            Intent AboutUsIntent = new Intent(UserAreaActivity.this, AboutUs.class);
            UserAreaActivity.this.startActivity(AboutUsIntent);

        } else if (id == R.id.nav_logout) {
            editor = getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE).edit();
            editor.putBoolean(SESSIONCONSTANT,false);
            editor.commit();
            finish();
        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
