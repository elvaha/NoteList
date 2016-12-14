package evh.notelist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

import evh.notelist.Module.NoteList;

public class MainActivity extends AppCompatActivity implements AddNoteListDialog.AddNoteListClicks {

    FloatingActionButton fab;
    FirebaseListAdapter mAdapter;
    FirebaseListAdapter fAdapter;
    DatabaseReference firebase;
    String token = "";
    ListView listView;
    NoteList lastDeletedNoteList;
    AddNoteListDialog dialog;
    NoteListFragment fragment = new NoteListFragment();
    Context context;
    String listId;
    int itemIndex = -1;
    NoteList deletedNoteList;
    Dialog settingsDialog;
    SharedPreferences prefs;
    String Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dialog = new AddNoteListDialog();

        prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        if (prefs.contains("NoteListToken"))
        {
            token = prefs.getString("NoteListToken","");
        } else
        {
            token = UUID.randomUUID().toString();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("NoteListToken", token);
            editor.commit();
        }
        if(prefs.contains("ListUserName")){
            Name = prefs.getString("ListUserName", "");
            setTitle(Name + "'s List" );
        }

        firebase = FirebaseDatabase.getInstance().getReference().child(token);
        setAdapter();
        context = getApplicationContext();
        btnAddNoteListClick();
        btnDeleteNoteListClick();
//        settingsDialogClickListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==1) //from settings
        {
            String name = SettingsFragment.getName(this);
            String message = "Welcome, "+name;
            setTitle(name + "'s List");

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("ListUserName", name);
            editor.commit();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void btnAddNoteListClick(){
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show(getFragmentManager(), "newNotelist");
            }
        });
    }

    public void btnDeleteNoteListClick(){
        FloatingActionButton fabDel = (FloatingActionButton) findViewById(R.id.fab_delete);
        fabDel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(itemIndex == -1){
                    Toast toast = Toast.makeText(context, "Item not selected", Toast.LENGTH_SHORT);
                    toast.show();
                }else {
                    deletedNoteList = (NoteList) getMyAdapter().getItem(itemIndex);
                    getMyAdapter().getRef(itemIndex).setValue(null);
                    showSnackbar();
                }
            }
        });
    }

    public void showSnackbar(){
        final View parent = listView;
        Snackbar snackbar = Snackbar
                .make(parent, "Product deleted!", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        firebase.push().setValue(deletedNoteList);
                        getMyAdapter().notifyDataSetChanged();
                        Snackbar snackbar = Snackbar.make(parent, "Item not deleted!", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                });
        snackbar.show();
    }

    public void setAdapter(){
        mAdapter = new FirebaseListAdapter<NoteList>(this, NoteList.class, android.R.layout.simple_list_item_checked, firebase) {
            @Override
            protected void populateView(View view, final NoteList noteList, int position) {
                TextView textView = (TextView)view.findViewById(android.R.id.text1);
                textView.setText(noteList.toString());
            }
        };
        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(mAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(itemIndex != position){
                    itemIndex = position;
                }else  if(itemIndex == position){
                    listView.setItemChecked(itemIndex, false);
                    itemIndex = -1;
                }
            }
        });

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //open notelist fragment
//                NoteList notelist = (NoteList) getMyAdapter().getItem(position);
//                listId =  getMyAdapter().getRef(position).getKey();
//
//                setContentView(R.layout.fragment_notelist_items_fragment);
//                Fragment frag = new NoteListFragment();
//
//                FragmentManager transaction = getSupportFragmentManager();
//                transaction.beginTransaction().addToBackStack(null);
//                transaction.beginTransaction().add(frag, "").commit();

//                Toast toast = Toast.makeText(context, noteId, Toast.LENGTH_SHORT);
//                toast.show();
//            }
//        });
    }

    public FirebaseListAdapter getMyAdapter()
    {
        return mAdapter;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                Toast.makeText(this, "Application icon clicked!",
                        Toast.LENGTH_SHORT).show();
                return true; //return true, means we have handled the event
            case R.id.action_settings:
                Intent intent = new Intent(this,SettingsActivity.class);
                startActivityForResult(intent,1);
                return true;
            case R.id.action_DeleteAll:
                warningDialog();
                return true;
            case R.id.action_SendList:
                SendList(createSms());
                return true;
        }

        return false;
    }

    public void openSettings(){
        settingsDialog = new Dialog(MainActivity.this);
        settingsDialog.setTitle("Settings");
        settingsDialog.setContentView(R.layout.settings_fragment);
        settingsDialog.show();
    }

    public void settingsDialogClickListeners(){
        final TextView tokenView = (TextView) findViewById(R.id.settings_token);
        final EditText tokenEdit = (EditText) findViewById(R.id.settings_token_edit);
        final Button btnTokenChange = (Button) findViewById(R.id.btn_settings_change);

        tokenView.setText(token);
        tokenEdit.setText(token);
        tokenView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                tokenView.setVisibility(View.GONE);
                tokenEdit.setVisibility(View.VISIBLE);
            }
        });

        btnTokenChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                token = tokenEdit.getText().toString();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("NoteListToken", token);
                editor.commit();

                tokenView.setText(token);
                tokenEdit.setVisibility(View.GONE);
                tokenView.setVisibility(View.VISIBLE);

                Toast.makeText(context, "Token has been Changed", Toast.LENGTH_SHORT).show();
                settingsDialog.dismiss();
            }
        });
    }

    public void warningDialog(){
        final AlertDialog.Builder warningDialog = new AlertDialog.Builder(this);
        warningDialog.setTitle("Warning");
        warningDialog.setMessage("Are you aure you want to clear the list?");
        warningDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                firebase.setValue(null);
            }
        });
        warningDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        warningDialog.show();
    }

    public void SendList(String message){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse( "sms:") );
        intent.putExtra( "sms_body", message );
        startActivity(intent);
    }

    public String createSms(){
        NoteList notelist;
        String sms = "";
        for(int i = 0; i < getMyAdapter().getCount(); i++){
            notelist = (NoteList) getMyAdapter().getItem(i);
            sms += notelist.toString()
                    + System.getProperty ("line.separator");
        }
        return sms;
    }

    @Override
    public void onAddBtnClick() {
        EditText noteName = (EditText) dialog.getDialog().findViewById(R.id.noteList_name);
        EditText noteQuant = (EditText) dialog.getDialog().findViewById(R.id.noteList_quantity);
        NoteList noteList = new NoteList(noteName.getText().toString(), noteQuant.getText().toString());
        firebase.push().setValue(noteList);
        getMyAdapter().notifyDataSetChanged();
        noteName.setText("");
        dialog.dismiss();
        Toast toast = Toast.makeText(context, "\"" + noteList.getName() + "\" has been created", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onCancelClick() {
        dialog.dismiss();
    }
}
