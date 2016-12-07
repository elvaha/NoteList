package evh.notelist;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.zip.Inflater;

import static evh.notelist.R.id.button;
import static evh.notelist.R.id.fab;

/**
 * Created by Elias on 06-12-2016.
 */

public class NoteListFragment extends android.support.v4.app.Fragment{


    DatabaseReference firebase;
    FirebaseListAdapter mAdapter;
    String listId;
    String token;
    NoteListFragmentfunctionality mCallback;
    View inflatedView = null;


    public NoteListFragment(){

    }

    public interface NoteListFragmentfunctionality{
        public void fabClick();
        public void btnAddClick();
        public void btnDeleteClick();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        this.inflatedView = inflater.inflate(R.layout.fragment_notelist_items_fragment, container, false);
//        GET ID FROM BUNDLE
//        setAdapter();

        Button btnaddItem = (Button) inflatedView.findViewById(R.id.btnAddItem);
        btnaddItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast toast  = Toast.makeText(getActivity().getApplicationContext(), "somerset", Toast.LENGTH_SHORT);
                toast.show();
                System.out.println("CLICKED");
            }
        });

        return inflatedView;
    }


}
