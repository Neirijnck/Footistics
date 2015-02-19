package prebenneirijnck.be.footistics.ui;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.analytics.GoogleAnalytics;

import prebenneirijnck.be.footistics.R;
import prebenneirijnck.be.footistics.settings.DisplaySettings;
import prebenneirijnck.be.footistics.util.Utils;

/**
 * Allows tweaking of various Footistics settings.
 */
public class FootisticsPreferences extends ActionBarActivity {

    private static final String TAG = "Settings";

    public static int THEME = R.style.Theme_Footistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(FootisticsPreferences.THEME);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlepane);
        setupActionBar();

        //Add settings fragment to container
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, new SettingsFragment());
        ft.commit();
    }

    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.ftToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();

        GoogleAnalytics.getInstance(this).reportActivityStop(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    protected static void setupSettings(final Activity activity, final Intent startIntent, Preference themePref, Preference languagePref){

        themePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if(DisplaySettings.KEY_THEME.equals(preference.getKey())){
                    Utils.updateTheme((String) newValue);

                    TaskStackBuilder.create(activity).addNextIntent(new Intent(activity, SeasonalStatsActivity.class))
                            .addNextIntent(startIntent).startActivities();
                }
                return true;
            }
        });

        //show currently set values for list prefs
        setListPreferenceSummary((ListPreference) themePref);
        setListPreferenceSummary((ListPreference) languagePref);
    }

    public static void setListPreferenceSummary(ListPreference listPref) {
        //set summary to be the user-description for the selected value
        listPref.setSummary(listPref.getEntry().toString().replaceAll("%", "%%"));
    }

    public static class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.settings);
            setupSettings(getActivity(), getActivity().getIntent(),
                    findPreference(DisplaySettings.KEY_THEME),
                    findPreference(DisplaySettings.KEY_LANGUAGE));
        }

        @Override
        public void onStop() {
            super.onStop();
            final SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(getActivity());
            prefs.unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onStart() {
            super.onStart();
            final SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(getActivity());
            prefs.registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

            if(DisplaySettings.KEY_THEME.equals(key)
                    || DisplaySettings.KEY_LANGUAGE.equals(key)){
                Preference pref = findPreference(key);
                if(pref!=null){
                    setListPreferenceSummary((ListPreference) pref);
                }
            }

        }
    }

}
