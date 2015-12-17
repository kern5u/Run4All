package fr.esstin.run4all;

    import android.app.Activity;
    import android.os.Bundle;
    import android.support.v4.content.ContextCompat;

    import com.github.mikephil.charting.charts.LineChart;
    import com.github.mikephil.charting.components.Legend;
    import com.github.mikephil.charting.components.XAxis;
    import com.github.mikephil.charting.components.YAxis;
    import com.github.mikephil.charting.data.Entry;
    import com.github.mikephil.charting.data.LineData;
    import com.github.mikephil.charting.data.LineDataSet;

    import java.util.ArrayList;

public class TestChart extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_chart);

        LineChart chart = (LineChart) findViewById(R.id.chart);

        ArrayList<Entry> valsComp1 = new ArrayList<>();
        ArrayList<Entry> valsComp2 = new ArrayList<>();


        Entry c1e1 = new Entry(100.000f, 0); // 0 == quarter 1
        valsComp1.add(c1e1);
        Entry c1e2 = new Entry(50.000f, 1); // 1 == quarter 2 ...
        valsComp1.add(c1e2);
        Entry c1e3 = new Entry(25.000f, 2); // 0 == quarter 3
        valsComp1.add(c1e3);
        Entry c1e4 = new Entry(12.500f, 3); // 1 == quarter 4 ...
        valsComp1.add(c1e4);
        Entry c1e5 = new Entry(100.000f, 4); // 0 == quarter 1
        valsComp1.add(c1e5);
        Entry c1e6 = new Entry(50.000f, 5); // 1 == quarter 2 ...
        valsComp1.add(c1e6);
        Entry c1e7 = new Entry(25.000f, 6); // 0 == quarter 3
        valsComp1.add(c1e7);
        Entry c1e8 = new Entry(12.500f, 7); // 1 == quarter 4 ...
        valsComp1.add(c1e8);

        Entry c2e1 = new Entry(120.000f, 0); // 0 == quarter 1
        valsComp2.add(c2e1);
        Entry c2e2 = new Entry(110.000f, 1); // 1 == quarter 2 ...
        valsComp2.add(c2e2);
        Entry c2e3 = new Entry(100.000f, 2); // 0 == quarter 3
        valsComp2.add(c2e3);
        Entry c2e4 = new Entry(90.000f, 3); // 1 == quarter 4 ...
        valsComp2.add(c2e4);
        Entry c2e5 = new Entry(120.000f, 4); // 0 == quarter 1
        valsComp2.add(c2e5);
        Entry c2e6 = new Entry(110.000f, 5); // 1 == quarter 2 ...
        valsComp2.add(c2e6);
        Entry c2e7 = new Entry(100.000f, 6); // 0 == quarter 3
        valsComp2.add(c2e7);
        Entry c2e8 = new Entry(90.00f, 7); // 1 == quarter 4 ...
        valsComp2.add(c2e8);


        LineDataSet setComp1 = new LineDataSet(valsComp1, "Company 1");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
        //Couleur des lignes pour le graphe 1
        setComp1.setColor(ContextCompat.getColor(getApplicationContext(), R.color.rouge));
        LineDataSet setComp2 = new LineDataSet(valsComp2, "Company 2");
        setComp2.setAxisDependency(YAxis.AxisDependency.LEFT);
        setComp1.setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));

        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(setComp1);
        dataSets.add(setComp2);

        ArrayList<String> xVals = new ArrayList<>();
        for (int i=0; i<valsComp1.size(); i++){
            xVals.add(String.valueOf(i)+".Q");
        }

        LineData data = new LineData(xVals, dataSets);
        chart.setData(data);

        XAxis xAxis = chart.getXAxis();
        YAxis leftAxis = chart.getAxisLeft();
        YAxis rightAxis = chart.getAxisRight();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        rightAxis.setEnabled(false);
        leftAxis.setStartAtZero(false);
        chart.setTouchEnabled(true);
        chart.setVisibleXRangeMaximum(4.0f);

        Legend legend = chart.getLegend();
        legend.setEnabled(false);
        chart.setDescription("");
        chart.invalidate(); // refresh


    }
}
