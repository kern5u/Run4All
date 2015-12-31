package fr.esstin.run4all;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity {
    // On voudra détecter uniquement les clics sur ce bouton
    ImageButton but_start = null;
    ImageButton but_perf = null;

    //=====POUR DEBUG======
    DataBaseHandler bdd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        but_start = (ImageButton)findViewById(R.id.button_start);
        but_perf = (ImageButton)findViewById(R.id.button_perf);

        bdd = new DataBaseHandler(this);

        but_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Réagir au clic
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        but_perf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bdd.deleteAllTable();
                bdd.insertRunData(1451508355000l, 200l, 1l, 1f*60f / 200f);
                bdd.insertRunData(1451421955000l, 100l, 2l, 2.0f*60.0f/100.0f);
                Intent intent = new Intent(MainActivity.this, PerfFragment.class);
                startActivity(intent);
            }
        });
    }
}
