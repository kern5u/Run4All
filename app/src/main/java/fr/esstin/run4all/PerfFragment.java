package fr.esstin.run4all;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;


public class PerfFragment extends FragmentActivity {
    // Fragment TabHost as mTabHost
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_perf);

        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("Distance"),
                DistanceFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("Temps"),
                TempsFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab3").setIndicator("Vitesse moyenne"),
                VitMoyFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab4").setIndicator("Suppression de données"),
                DeleteEntryFragment.class, null);
    }
}