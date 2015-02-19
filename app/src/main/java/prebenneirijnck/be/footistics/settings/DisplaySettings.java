package prebenneirijnck.be.footistics.settings;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Settings related to appearance, display formats, etc...
 */
public class DisplaySettings {

    public static final String KEY_THEME = "be.prebenneirijnck.footistics.theme";

    public static final String KEY_LANGUAGE = "be.prebenneirijnck.footistics.language";

    public static String getThemeIndex(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).
                getString(KEY_THEME, "0");
    }

    public static String getContentLanguage(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(KEY_LANGUAGE, "en");
    }

}
