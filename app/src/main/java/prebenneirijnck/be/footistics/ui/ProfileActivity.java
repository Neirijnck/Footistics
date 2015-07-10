package prebenneirijnck.be.footistics.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import prebenneirijnck.be.footistics.R;

/**
 * Activity that allows viewing and editing of your profile
 */
public class ProfileActivity extends BaseNavDrawerActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setupActionBar();
        setupNavDrawer();

        if (savedInstanceState == null) {
            ProfileFragment f = ProfileFragment.newInstance();
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.activity_fade_enter, R.anim.activity_fade_exit).replace(R.id.content_frame, f).commit();
        }
    }

    @Override
    protected void setupActionBar() {
        super.setupActionBar();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
