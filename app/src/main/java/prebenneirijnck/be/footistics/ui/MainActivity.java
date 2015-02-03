package prebenneirijnck.be.footistics.ui;

import android.os.Bundle;

import prebenneirijnck.be.footistics.R;
import prebenneirijnck.be.footistics.util.Utils;

public class MainActivity extends BaseTopActivity {

    protected static final String TAG = "Main Test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupActionBar();
        setupNavDrawer();
    }

    @Override
    protected void fireTrackerEvent(String label) {
        Utils.trackAction(this, TAG, label);
    }

}
