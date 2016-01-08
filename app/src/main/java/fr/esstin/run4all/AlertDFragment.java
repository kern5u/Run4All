package fr.esstin.run4all;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class AlertDFragment extends DialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity())
                // Set Dialog Icon
                .setIcon(R.drawable.image_play)
                        // Set Dialog Title
                .setTitle("Run Terminé")
                        // Set Dialog Message
                .setMessage("Vous avez parcouru " + MapsActivity.distance+" km en "+MapsActivity.calculTempsString(MapsActivity.temps))

                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //MapsActivity.finish();
                    }
                })
                .create();
    }
    }
