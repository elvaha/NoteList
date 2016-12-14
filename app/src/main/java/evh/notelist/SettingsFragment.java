package evh.notelist;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

/**
 * Created by Elias on 14-12-2016.
 */

public class SettingsFragment extends PreferenceFragment {

    private static String SETTINGS_GENDERKEY = "male";
    private static String SETTINGS_NAMEKEY = "name";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_pref);
    }

    public static String getName(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(SETTINGS_NAMEKEY, "");
    }


}
