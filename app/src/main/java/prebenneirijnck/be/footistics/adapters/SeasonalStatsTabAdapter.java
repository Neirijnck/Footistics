package prebenneirijnck.be.footistics.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import prebenneirijnck.be.footistics.ui.GamesFragment;
import prebenneirijnck.be.footistics.ui.OverviewFragment;

public class SeasonalStatsTabAdapter extends FragmentStatePagerAdapter {

    private String[] mTitles;

    public SeasonalStatsTabAdapter(String[] titles, FragmentManager fm) {
        super(fm);
        this.mTitles = titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                //games fragment
                return GamesFragment.newInstance();
            case 1:
                //overview fragment
                return OverviewFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }
}
