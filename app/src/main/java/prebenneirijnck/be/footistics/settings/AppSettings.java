package prebenneirijnck.be.footistics.settings;

import android.content.Context;
import android.preference.PreferenceManager;

public class AppSettings {

    public static final String KEY_GOOGLEANALYTICS = "enableGAnalytics";

    public static boolean isGaAppOptOut(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(KEY_GOOGLEANALYTICS, false);
    }

}
