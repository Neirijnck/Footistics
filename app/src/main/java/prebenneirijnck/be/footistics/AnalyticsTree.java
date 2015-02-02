package prebenneirijnck.be.footistics;

import android.content.Context;

import com.crashlytics.android.Crashlytics;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import timber.log.Timber;

/**
 * Mostly copy of {@link timber.log.Timber.DebugTree}, but logs to Crashlytics. Drops debug and
 * verbose logs.
 */
public class AnalyticsTree extends Timber.HollowTree implements Timber.TaggedTree {

    private static final Pattern ANONYMOUS_CLASS = Pattern.compile("\\$\\d+$");
    private static final ThreadLocal<String> NEXT_TAG = new ThreadLocal<>();
    private final Context context;

    public AnalyticsTree(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public void i(String message, Object... args) {
        logToCrashlytics("INFO", createTag(), message, args);
    }

    @Override
    public void i(Throwable t, String message, Object... args) {
        logToCrashlytics("INFO", createTag(), message, args);
    }

    @Override
    public void w(String message, Object... args) {
        logToCrashlytics("WARN", createTag(), message, args);
    }

    @Override
    public void w(Throwable t, String message, Object... args) {
        logToCrashlytics("WARN", createTag(), message, args);
    }

    @Override
    public void e(String message, Object... args) {
        logToCrashlytics("ERROR", createTag(), message, args);
    }

    @Override
    public void tag(String tag) {
        NEXT_TAG.set(tag);
    }

    private static String createTag() {
        String tag = NEXT_TAG.get();
        if (tag != null) {
            NEXT_TAG.remove();
            return tag;
        }

        tag = new Throwable().getStackTrace()[4].getClassName();
        Matcher m = ANONYMOUS_CLASS.matcher(tag);
        if (m.find()) {
            tag = m.replaceAll("");
        }
        return tag.substring(tag.lastIndexOf('.') + 1);
    }

    private void logToCrashlytics(String level, String tag, String message, Object... args) {
        if (message == null) {
            return;
        }
        Crashlytics.log(level + "/" + tag + ": " + String.format(message, args));
    }

}
