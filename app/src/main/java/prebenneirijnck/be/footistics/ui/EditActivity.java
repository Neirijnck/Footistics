package prebenneirijnck.be.footistics.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import prebenneirijnck.be.footistics.R;

/**
 * Activity that allows editing
 */
public class EditActivity extends BaseNavDrawerActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setupActionBar();
        setupNavDrawer();

        if (savedInstanceState == null) {
            Bundle b = getIntent().getExtras();
            String value = b.getString("type");

            switch(value) {
                case "ProfileFragment":
                EditProfileFragment f = EditProfileFragment.newInstance();
                getSupportFragmentManager().beginTransaction().add(R.id.content_frame, f).commit();
                    break;
            }
        }
    }

    @Override
    protected void setupActionBar() {
        super.setupActionBar();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}