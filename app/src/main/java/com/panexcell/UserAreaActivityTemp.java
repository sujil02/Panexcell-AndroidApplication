package com.panexcell;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UserAreaActivityTemp extends AppCompatActivity {

    private ListView lvPrograms;
    private ProgramListAdaptar adaptar;
    private List<Programs> programs ;
    int ins;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);
        lvPrograms = (ListView)findViewById(R.id.lv_details);
        programs = new ArrayList<>();

        programs.add(new Programs(1,"one","",""));
        programs.add(new Programs(2,"two","",""));
        programs.add(new Programs(3,"three","",""));
        programs.add(new Programs(4, "four", "",""));
        programs.add(new Programs(5,"one","",""));
        programs.add(new Programs(6,"two","",""));
        programs.add(new Programs(7,"three","",""));
        programs.add(new Programs(8, "four", "",""));

        adaptar = new ProgramListAdaptar(getApplication(), programs);
        lvPrograms.setAdapter(adaptar);

        lvPrograms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "id" +view.getTag(), Toast.LENGTH_SHORT).show();
                //Host call
                //save the data
                Intent descriptionIntent = new Intent(UserAreaActivityTemp.this, UserAreaActivity.class);
                UserAreaActivityTemp.this.startActivity(descriptionIntent);
            }
        });

    }


}
