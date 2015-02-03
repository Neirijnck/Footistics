package prebenneirijnck.be.footistics.util;


import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import com.google.android.gms.analytics.HitBuilders;

import prebenneirijnck.be.footistics.Analytics;

public class Utils {

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
     * Resolves the given attribute to the resource id for the given theme.
     */
    public static int resolveAttributeToResourceId(Resources.Theme theme, int attributeResId) {
        TypedValue outValue = new TypedValue();
        theme.resolveAttribute(attributeResId, outValue, true);
        return outValue.resourceId;
    }

}