package prebenneirijnck.be.footistics.util;


import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.util.TypedValue;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import prebenneirijnck.be.footistics.Analytics;
import prebenneirijnck.be.footistics.R;
import prebenneirijnck.be.footistics.ui.FootisticsPreferences;

public class Utils {

    /**
     * Returns the current version of the app
     */
    public static String getVersion(Context context) {
        String version;
        try {
            version = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            version = "UnknownVersion";
        }
        return version;
    }

    /**
     * Track an action event, e.g. when an action item is clicked.
     */
    public static void trackAction(Context context, String tag, String label) {
        Analytics.getTracker(context).send(new HitBuilders.EventBuilder()
                .setCategory(tag)
                .setAction("Action Item")
                .setLabel(label)
                .build());
    }

    /**
     * Track a screen view. This is commonly called in {@link android.support.v4.app.Fragment#onStart()}.
     */
    public static void trackView(Context context, String screenName) {
        Tracker tracker = Analytics.getTracker(context);
        tracker.setScreenName(screenName);
        tracker.send(new HitBuilders.AppViewBuilder().build());
    }

    /**
     * Track generic click that doesn't fit trackAction tracker
     */
    public static void trackClick(Context context, String tag, String label) {
        Analytics.getTracker(context).send(new HitBuilders.EventBuilder()
                .setCategory(tag)
                .setAction("Click")
                .setLabel(label)
                .build());
    }

    /**
     * Track a custom event that doesn't fit other trackers
     */
    public static void trackCustomEvent(Context context, String tag, String action,
                                        String label) {
        Analytics.getTracker(context).send(new HitBuilders.EventBuilder()
                .setCategory(tag)
                .setAction(action)
                .setLabel(label)
                .build());
    }

    /**
     * Resolves the given attribute to the resource id for the given theme.
     */
    public static int resolveAttributeToResourceId(Resources.Theme theme, int attributeResId) {
        TypedValue outValue = new TypedValue();
        theme.resolveAttribute(attributeResId, outValue, true);
        return outValue.resourceId;
    }

    /**
     * Sets the global app theme variable. Applied by all activities once they are created.
     */
    public static synchronized void updateTheme(String themeIndex) {
        int theme = Integer.valueOf(themeIndex);
        switch (theme) {
            case 1:
                FootisticsPreferences.THEME = R.style.Theme_Footistics_Light;
                break;
            default:
                FootisticsPreferences.THEME = R.style.Theme_Footistics;
                break;
        }
    }

}
