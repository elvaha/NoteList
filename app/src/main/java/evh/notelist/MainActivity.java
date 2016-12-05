package evh.notelist;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

import evh.notelist.Module.NoteList;

public class MainActivity extends AppCompatActivity implements AddNoteListFragment.AddNoteListClicks {

    FloatingActionButton fab;
    FirebaseListAdapter mAdapter;
    DatabaseReference firebase;
    String token = "";
    ListView listView;
    NoteList lastDeletedNoteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        if (prefs.contains("token"))
        {
            token = prefs.getString("token","");
        } else
        {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("token",token+ UUID.randomUUID());
            editor.commit();
        }

        firebase = FirebaseDatabase.getInstance().getReference().child(token);

        btnAddNoteListClick();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void btnAddNoteListClick(){
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                AddNoteListFragment dialog = new AddNoteListFragment();

                dialog.show(getFragmentManager(), "newNotelist");
            }
        });
    }

    public void setAdapter(){
        mAdapter = new FirebaseListAdapter<NoteList>(this, NoteList.class, android.R.layout.simple_list_item_activated_1, firebase) {
            @Override
            protected void populateView(View view, NoteList noteList, int position) {
                ((TextView)view.findViewById(android.R.id.text1)).setText(noteList.toString());
            }
        };

//        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(mAdapter);
//        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    public FirebaseListAdapter getMyAdapter()
    {
        return mAdapter;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAddBtnClick() {
        String notelistName = AddNoteListFragment.getUserInput();
        NoteList noteList = new NoteList(notelistName);
        firebase.push().setValue(noteList);
        getMyAdapter().notifyDataSetChanged();
    }

    @Override
    public void onCancelClick() {

    }
}
