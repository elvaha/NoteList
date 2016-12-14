package evh.notelist;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Elias on 14-12-2016.
 */

public class SettingsActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
        //note - there is not setContentView and no xml layout
        //for this activity. Because that is defined 100 %
        //in the fragment
    }
}
