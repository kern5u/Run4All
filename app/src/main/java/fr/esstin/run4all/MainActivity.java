package fr.esstin.run4all;
    import android.app.Activity;
    import android.content.Intent;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.ImageButton;

public class MainActivity extends Activity {
    // On voudra détecter uniquement les clics sur ce bouton
    ImageButton but_start = null;
    ImageButton but_perf = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        but_start = (ImageButton)findViewById(R.id.button_start);
        but_perf = (ImageButton)findViewById(R.id.button_perf);

        /*but_start.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Réagir à un long clic
                return false;
            }
        });*/

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
                Intent intent = new Intent(MainActivity.this, PerfActivity.class);
                startActivity(intent);
            }
        });
    }
}
