package com.panexcell;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sujil on 06-01-2017.
 */

public class ProgramListAdaptar extends BaseAdapter {
    
    private Context context;
    private List<Programs> programs;

    public ProgramListAdaptar(Context context, List<Programs> programs) {
        this.context = context;
        this.programs = programs;
    }

    @Override
    public int getCount() {
        return programs.size();
    }

    @Override
    public Object getItem(int position) {
        return programs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(context, R.layout.project_list , null);
        TextView tvTittle = (TextView)v.findViewById(R.id.tvTitle);
        TextView tvPayment = (TextView)v.findViewById(R.id.tvCompensation);
        tvTittle.setText(programs.get(position).getTitle());
        tvPayment.setText(programs.get(position).getPayment());
        v.setTag(programs.get(position).getId());

        return v;
    }
}
