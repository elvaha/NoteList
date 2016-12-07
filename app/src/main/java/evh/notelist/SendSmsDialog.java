package evh.notelist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

/**
 * Created by Elias on 07-12-2016.
 */

public class SendSmsDialog extends DialogFragment {

    AddSmsSendClick mCallback;


    public interface AddSmsSendClick{
        public void onSendClick();
    }

    public SendSmsDialog(){}

    public SendSmsDialog(String phoneNumber, String message){

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (AddSmsSendClick) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnPositiveListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder smsAlert = new AlertDialog.Builder(getActivity());
        smsAlert.setTitle("Send list as SMS");

        LayoutInflater inflater = getActivity().getLayoutInflater();

        smsAlert.setView(inflater.inflate(R.layout.sendsms_dialog, null));
        smsAlert.setPositiveButton("Send", pListener);
        smsAlert.setNegativeButton(R.string.btnCancel, nListener);
        return smsAlert.create();
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
        mCallback.onSendClick();
    }
    protected void negativeClick()
    {

    }
}
