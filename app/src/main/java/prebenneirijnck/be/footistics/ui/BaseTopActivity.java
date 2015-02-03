package prebenneirijnck.be.footistics.ui;

import android.view.MenuItem;

import prebenneirijnck.be.footistics.R;

/**
 * Activities at the top of the navigation hierarchy, show menu on going up.
 */
public abstract class BaseTopActivity extends BaseNavDrawerActivity {

    @Override
    protected void setupActionBar() {
        super.setupActionBar();
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setupNavDrawer() {
        super.setupNavDrawer();

        //show drawer indicator
        setDrawerIndicatorEnabled();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_fade_enter, R.anim.activity_fade_exit);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //should we toggle nav drawer? (icon was touched)
        if(toggleDrawer(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Google Analytics helper method for easy sending of click events.
     */
    protected abstract void fireTrackerEvent(String label);
}
