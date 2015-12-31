package fr.esstin.run4all;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.List;

public class DeleteEntryFragment extends Fragment  implements OnItemSelectedListener {

    DataBaseHandler bdd;
    List<String> date;
    Spinner spinner;
    Button suppr_one;
    Button suppr_all;
    String date_coisie;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.fragment_delete_entry, container, false);

        bdd = new DataBaseHandler(getActivity());
        date = bdd.getAllDate();

        spinner = (Spinner)V.findViewById(R.id.spinner);
        suppr_one = (Button)V.findViewById(R.id.suppr_one);
        suppr_all = (Button)V.findViewById(R.id.suppr_all);
        spinner.setOnItemSelectedListener(this);
        loadSpinnerData();

        suppr_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!date.isEmpty()) {
                    bdd.deleteRun(date_coisie);
                    loadSpinnerData();
                }
            }
        });

        suppr_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bdd.deleteAllTable();
                loadSpinnerData();
            }
        });

        return V;
    }

    private void loadSpinnerData() {
        // database handler
        DataBaseHandler db = new DataBaseHandler(getActivity());

        // Spinner Drop down elements
        List<String> date = db.getAllDate();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, date);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        date_coisie = parent.getItemAtPosition(position).toString();
        Log.d("Debug", "Label choisi : "+date_coisie);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}