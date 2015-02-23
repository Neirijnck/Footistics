package prebenneirijnck.be.footistics.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import prebenneirijnck.be.footistics.R;

/**
 * Activity that allows adding games to the database
 */
public class AddActivity extends BaseNavDrawerActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
        setupActionBar();
        setupNavDrawer();

        if (savedInstanceState == null) {
            AddFragment f = AddFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();
        }
    }

    @Override
    protected void setupActionBar() {
        super.setupActionBar();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
