package prebenneirijnck.be.footistics;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;

import io.fabric.sdk.android.Fabric;
import prebenneirijnck.be.footistics.settings.AppSettings;
import timber.log.Timber;

/**
 * Initializes settings
 *
 * @author Preben Neirijnck
 */
public class FootisticsApplication extends Application {

    /**
     * The content authority used to identify the Footistics {@link android.content.ContentProvider}
     */
    public static final String CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID + ".provider";

    @Override
    public void onCreate() {
        super.onCreate();

        //logging setup
        if(BuildConfig.DEBUG) {
            //detailed logcat logging
            Timber.plant(new Timber.DebugTree());
        }
        else{
            //crash and error reporting
            Timber.plant(new AnalyticsTree(this));
            if(!Fabric.isInitialized()){
                Fabric.with(this, new Crashlytics());
            }
        }

        // Ensure GA opt-out
        GoogleAnalytics.getInstance(this).setAppOptOut(AppSettings.isGaAppOptOut(this));
        if (BuildConfig.DEBUG) {
            //GoogleAnalytics.getInstance(this).setDryRun(true);
            GoogleAnalytics.getInstance(this).getLogger().setLogLevel(Logger.LogLevel.VERBOSE);
        }
        // Initialize tracker
        Analytics.getTracker(this);

    }

}
