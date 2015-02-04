package prebenneirijnck.be.footistics.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;

import prebenneirijnck.be.footistics.R;
import prebenneirijnck.be.footistics.adapters.SeasonalStatsTabAdapter;
import prebenneirijnck.be.footistics.util.Utils;

public class SeasonalStatsActivity extends BaseTopActivity {

    protected static final String TAG = "Seasonal Statistics";

    private ViewPager mViewPager;
    private PagerSlidingTabStrip mSeasonalStatsTabStrip;
    private SeasonalStatsTabAdapter mTabsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seasonal_stats);
        setupActionBar();
        setTitle(getString(R.string.seasonal));
        setupNavDrawer();

        setupViews();
    }

    private void setupViews(){
        //set pageradapter
        mViewPager = (ViewPager) findViewById(R.id.seasonalStatsPager);
        mSeasonalStatsTabStrip = (PagerSlidingTabStrip) findViewById(R.id.seasonalStatsTabStrip);

        final String[] TITLES = {
                getString(R.string.tab_games),
                getString(R.string.tab_overview)
        };

        mTabsAdapter = new SeasonalStatsTabAdapter(TITLES, getSupportFragmentManager());

        mViewPager.setAdapter(mTabsAdapter);

        //bind tabs to viewpager
        mSeasonalStatsTabStrip.setViewPager(mViewPager);
    }

    @Override
    protected void fireTrackerEvent(String label) {
        Utils.trackAction(this, TAG, label);
    }

}
