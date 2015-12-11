package fr.esstin.run4all;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;


public class PerfActivity extends Activity {

    DataBaseHandler bdd;
    ArrayList<Long> timestamp;
    ArrayList<Long> temps;
    ArrayList<Double> distance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bdd= new DataBaseHandler(this);

        setContentView(R.layout.activity_perf);

        timestamp = bdd.getAllTimestamp();
        temps = bdd.getAllTemps();
        distance = bdd.getAllDistance();

        for(int i=0; i<timestamp.size();i++){
            Log.d("timestamp","Timestamp["+i+"] = "+timestamp.get(i));
        }

        for(int i=0; i<temps.size();i++){
            Log.d("timestamp","Timestamp["+i+"] = "+temps.get(i));
        }

        for(int i=0; i<distance.size();i++){
            Log.d("timestamp","Timestamp["+i+"] = "+distance.get(i));
        }

        LineChart chart = (LineChart) findViewById(R.id.chart);

    }

}
