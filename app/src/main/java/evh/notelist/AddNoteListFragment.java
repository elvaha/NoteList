package evh.notelist;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

/**
 * Created by Elias on 05-12-2016.
 */

public class AddNoteListFragment extends DialogFragment {

    AddNoteListClicks mCallback;
    android.app.AlertDialog.Builder alert;
    Resources resources;
    static String userInput = "";

    public AddNoteListFragment(){

    }

    public static String getUserInput()
    {
        return userInput;
    }

    public AddNoteListFragment(Context context, String title, String message){

    }

    public interface AddNoteListClicks{
        public void onAddBtnClick();
        public void onCancelClick();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (AddNoteListClicks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnPositiveListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final EditText input = new EditText(getActivity());

        AlertDialog.Builder alert = new AlertDialog.Builder(
                getActivity());
        alert.setTitle("Confirmation");
        alert.setView(input);
        alert.setPositiveButton("Add", pListener);
        alert.setNegativeButton("Cancel", nListener);
        return alert.create();
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
        mCallback.onAddBtnClick();
    }
    protected void negativeClick()
    {
        mCallback.onCancelClick();
    }

}
