package com.panexcell;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

public class ProgramList extends AppCompatActivity {


    private DrawerLayout mdrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private ListView lvPrograms;
    private ProgramListAdaptar adaptar;
    private List<Programs> programs ;
    private  SharedPreferences.Editor editor;
    SharedPreferences prefs;
    String _id;
    private Toolbar mToolbar;
    private Button share,aboutus,home,logout;

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_list);

        mdrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mdrawerLayout, R.string.open,R.string.close);
        mToolbar = (Toolbar) findViewById(R.id.bar);
        mToolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(mToolbar);
        mdrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        lvPrograms = (ListView)findViewById(R.id.lv_Details);
        programs = new ArrayList<>();
        home =(Button) findViewById(R.id.btHome);
        share = (Button) findViewById(R.id.btShare);
        aboutus = (Button) findViewById(R.id.btAboutUs);
        logout = (Button) findViewById(R.id.btLogout);

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

                        adaptar = new ProgramListAdaptar(getApplication(), programs);
                        lvPrograms.setAdapter(adaptar);
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(ProgramList.this);
                        builder.setMessage("Please logout and re-login").setNegativeButton("retry", null).create().show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        prefs = getSharedPreferences(Constants.MY_PREFS_NAME, MODE_PRIVATE);
        UserAreaRequest userAreaRequest = new UserAreaRequest( prefs.getString(SESSIONUSER,null), responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(ProgramList.this);
        requestQueue.add(userAreaRequest);



        lvPrograms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Host call
                //save the data
                Programs p = new Programs();
                p = programs.get(position);
                Intent SpecificProgramActivityIntent = new Intent(ProgramList.this, SpecificProgramActivity.class);
                SpecificProgramActivityIntent.putExtra("id", p.getId());
                ProgramList.this.startActivity(SpecificProgramActivityIntent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE).edit();
                editor.putBoolean(SESSIONCONSTANT,false);
                editor.commit();
                finish();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int applicationNameId = getApplicationContext().getApplicationInfo().labelRes;
                    final String appPackageName = getApplicationContext().getPackageName();
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    //i.putExtra(Intent.EXTRA_SUBJECT, activity.getString(applicationNameId));
                    String text = "Install this cool application: ";
                    String link = "https://play.google.com/store/apps/details?id=" + appPackageName;
                    i.putExtra(Intent.EXTRA_TEXT, text + " " + link);
                    startActivity(Intent.createChooser(i, "Share link:"));

                } catch(Exception e) {
                    //e.toString();
                }
            }
        });

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AboutUsIntent = new Intent(ProgramList.this, AboutUs.class);
                ProgramList.this.startActivity(AboutUsIntent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdrawerLayout.closeDrawer(Gravity.LEFT);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;

        }

        return super.onOptionsItemSelected(item);
    }


}
