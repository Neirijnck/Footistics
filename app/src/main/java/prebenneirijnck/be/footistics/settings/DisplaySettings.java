package prebenneirijnck.be.footistics.settings;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Settings related to appearance, display formats, etc...
 */
public class DisplaySettings {

    public static final String KEY_THEME = "be.prebenneirijnck.footistics.theme";

    public static String getThemeIndex(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).
                getString(KEY_THEME, "0");
    }

}
