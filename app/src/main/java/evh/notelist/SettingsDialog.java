package evh.notelist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;
import android.view.LayoutInflater;

/**
 * Created by Elias on 11-12-2016.
 */

public class SettingsDialog extends DialogFragment {

    public SettingsDialog(){

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder settingsDialog = new AlertDialog.Builder(getActivity());
        settingsDialog.setTitle("Settings");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        settingsDialog.setView(inflater.inflate(R.layout.settings_dialog, null));

        settingsDialog.setPositiveButton("Send", pListener);
        settingsDialog.setNegativeButton(R.string.btnCancel, nListener);
        return settingsDialog.create();
    }


    //This is our positive listener for when the user presses
    //the yes button
    DialogInterface.OnClickListener pListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface arg0, int arg1) {
            // these will be executed when user click Yes button
            positiveClick();
        }
    };

    //This is our negative listener for when the user presses
    //the no button.
    DialogInterface.OnClickListener nListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface arg0, int arg1) {
            // these will be executed when user click No button
            negativeClick();
        }
    };


    protected void positiveClick()
    {

    }
    protected void negativeClick()
    {

    }
}
