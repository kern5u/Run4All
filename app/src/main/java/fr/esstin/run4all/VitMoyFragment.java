package fr.esstin.run4all;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class VitMoyFragment extends Fragment {

    DataBaseHandler bdd;
    ArrayList<Long> timestamp;
    ArrayList<Float> vitesse;
    double listSize = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.fragment_distance, container, false);

        bdd= new DataBaseHandler(getActivity());

        timestamp = bdd.getAllTimestamp();
        vitesse = bdd.getAllvitesse();
        listSize = timestamp.size();

        LineChart chart = (LineChart)V.findViewById(R.id.chartVitesse);

        //Liste des Entry contenant les distances parcourues
        ArrayList<Entry> vitesseData = new ArrayList<>();

        //Définition des valeurs de l'axe des ordonnées et des l'abscisses
        ArrayList<String> xVals = new ArrayList<>();
        for (int i=0; i<listSize; i++){
            vitesseData.add(new Entry(vitesse.get(i),i)); //ordronnées
            String dateTimestamp = calculDateTimestamp(timestamp.get(i)); //abscisse
            xVals.add(dateTimestamp);
        }

        LineDataSet setComp1 = new LineDataSet(vitesseData, "distance");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
        setComp1.setColor(ContextCompat.getColor(getContext(), R.color.rouge));

        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(setComp1);

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

        return V;
    }

    private String calculDateTimestamp(Long aLong) {
        String mois = new String();
        Date date = new Date(aLong);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int annee = c.get(Calendar.YEAR);
        annee = annee - (int)Math.floor(annee/100)*100;
        switch(c.get(Calendar.MONTH)){
            case 0:
                mois = "Jan";
                break;
            case 1:
                mois = "Fev";
                break;
            case 2:
                mois = "Mar";
                break;
            case 3:
                mois = "Avr";
                break;
            case 4:
                mois = "Mai";
                break;
            case 5:
                mois = "Juin";
                break;
            case 6:
                mois = "Juil";
                break;
            case 7:
                mois = "Aou";
                break;
            case 8:
                mois = "Sep";
                break;
            case 9:
                mois = "Oct";
                break;
            case 10:
                mois = "Nov";
                break;
            case 11:
                mois = "Dec";
                break;
            default:
                mois = "00";
        }

        return String.valueOf(c.get(Calendar.DATE))+mois+String.valueOf(annee);
    }
}
