package prebenneirijnck.be.footistics.ui;

import android.os.Bundle;

import prebenneirijnck.be.footistics.R;
import prebenneirijnck.be.footistics.util.Utils;

public class GlobalStatsActivity extends BaseTopActivity {

    protected static final String TAG = "Global Statistics";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_stats);
        setupActionBar();
        setupNavDrawer();

        if (savedInstanceState == null) {
            GlobalStatsFragment f = GlobalStatsFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();
        }
    }

    @Override
    protected void fireTrackerEvent(String label) {
        Utils.trackAction(this, TAG, label);
    }

}
