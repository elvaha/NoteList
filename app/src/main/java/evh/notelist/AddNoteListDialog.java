package evh.notelist;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

/**
 * Created by Elias on 05-12-2016.
 */

public class AddNoteListDialog extends DialogFragment {

    AddNoteListClicks mCallback;

    public AddNoteListDialog(){

    }

    public AddNoteListDialog(Context context, String title, String message){

    }

    public interface AddNoteListClicks{
        public void onAddBtnClick();
        public void onCancelClick();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (AddNoteListClicks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnPositiveListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Create note list");

        LayoutInflater inflater = getActivity().getLayoutInflater();

        alert.setView(inflater.inflate(R.layout.addNoteList_dialog, null));
        alert.setPositiveButton(R.string.btnAdd, pListener);
        alert.setNegativeButton(R.string.btnCancel, nListener);
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
