package prebenneirijnck.be.footistics.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import prebenneirijnck.be.footistics.R;
import prebenneirijnck.be.footistics.adapters.DrawerAdapter;
import prebenneirijnck.be.footistics.util.Utils;

/**
 * Adds onto {@link BaseActivity} by attaching a navigation drawer.
 */
public abstract class BaseNavDrawerActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private static final String TAG_NAV_DRAWER = "Navigation Drawer";
    private static final int NAVDRAWER_CLOSE_DELAY = 250;

    public static final int MENU_ITEM_ACCOUNT = 0;
    // DIVIDER IN BETWEEN HERE                  1
    public static final int MENU_ITEM_SEASONAL_POSITION = 2;
    public static final int MENU_ITEM_GLOBAL_POSITION = 3;
    // DIVIDER IN BETWEEN HERE                         4
    public static final int MENU_ITEM_SETTINGS_POSITION = 5;
    public static final int MENU_ITEM_HELP_POSITION = 6;

    private Handler mHandler;
    private Toolbar mActionBarToolbar;
    private DrawerLayout mDrawerLayout;
    private View mDrawerView;
    private ListView mDrawerList;
    private DrawerAdapter mDrawerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // set a theme based on user preference
        setTheme(FootisticsPreferences.THEME);
        super.onCreate(savedInstanceState);

        mHandler = new Handler();
    }

    /**
     * Initializes the navigation drawer. Overriding activities should call this in their {@link
     * #onCreate(android.os.Bundle)} after {@link #setContentView(int)}.
     */
    public void setupNavDrawer()
    {
        mActionBarToolbar = (Toolbar) findViewById(R.id.ftToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerView = findViewById(R.id.drawer_view);

        mDrawerList = (ListView) findViewById(R.id.drawer_list);

        //setup menu adapter
        mDrawerAdapter = new DrawerAdapter(this);
        mDrawerAdapter.add(new DrawerItemAccount());

        mDrawerAdapter.add(new DrawerItemDivider());

        mDrawerAdapter.add(new DrawerItem(getString(R.string.seasonal),
                Utils.resolveAttributeToResourceId(getTheme(), R.attr.drawableSeasonalStats)));
        mDrawerAdapter.add(new DrawerItem(getString(R.string.global),
                Utils.resolveAttributeToResourceId(getTheme(), R.attr.drawableGlobalStats)));

        mDrawerAdapter.add(new DrawerItemDivider());

        mDrawerAdapter.add(new DrawerItem(getString(R.string.preferences),
                Utils.resolveAttributeToResourceId(getTheme(), R.attr.drawableSettings)));
        mDrawerAdapter.add(new DrawerItem(getString(R.string.help),
                Utils.resolveAttributeToResourceId(getTheme(), R.attr.drawableHelp)));

        mDrawerList.setAdapter(mDrawerAdapter);
        mDrawerList.setOnItemClickListener(this);
    }

    @Override
    public void onBackPressed() {
        if(isNavDrawerOpen()){
            closeNavDrawer();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent launchIntent = null;

        mDrawerAdapter.getItem(position);
        switch(position){
            case MENU_ITEM_ACCOUNT:
            {
//                launchIntent = new Intent(this, ....class);
                break;
            }
            case MENU_ITEM_SEASONAL_POSITION:
            {
                if(this instanceof SeasonalStatsActivity){
                    break;
                }
                launchIntent = new Intent(this, SeasonalStatsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                Utils.trackAction(this, TAG_NAV_DRAWER, "Seasonal Stats");
                break;
            }
            case MENU_ITEM_GLOBAL_POSITION:
            {
//                if(this instanceof ...Activity){
//                break;
//                }
//                launchIntent = new Intent(this, BaseActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                Utils.trackAction(this, TAG_NAV_DRAWER, "Global Stats");
                break;
            }
            case MENU_ITEM_SETTINGS_POSITION:
            {
                launchIntent = new Intent(this, FootisticsPreferences.class);
                Utils.trackAction(this, TAG_NAV_DRAWER, "Settings");
                break;
            }
            case MENU_ITEM_HELP_POSITION:
            {
//                launchIntent = new Intent(this, HelpActivity.class);
//                Utils.trackAction(this, TAG_NAV_DRAWER, "Help");
                break;
            }
        }

        // already displaying correct screen
        if (launchIntent != null) {
            final Intent finalLaunchIntent = launchIntent;
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    goToNavDrawerItem(finalLaunchIntent);
                }
            }, NAVDRAWER_CLOSE_DELAY);
        }

        mDrawerLayout.closeDrawer(Gravity.START);
    }

    private void goToNavDrawerItem(Intent intent){
        startActivity(intent);
        overridePendingTransition(R.anim.activity_fade_enter, R.anim.activity_fade_exit);
    }

    /**
     * Returns true if the navigation drawer is open.
     */
    public boolean isNavDrawerOpen() {
        return mDrawerLayout.isDrawerOpen(mDrawerView);
    }

    public void setDrawerIndicatorEnabled() {
        mActionBarToolbar.setNavigationIcon(R.drawable.ic_drawer);
        mActionBarToolbar.setNavigationContentDescription(R.string.drawer_open);
    }

    /**
     * Highlights the given position in the drawer menu. Activities listed in the drawer should call
     * this in {@link #onStart()}.
     */
    public void setDrawerSelectedItem(int menuItemPosition) {
        mDrawerList.setItemChecked(menuItemPosition, true);
    }

    public void openNavDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    public void closeNavDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    public boolean toggleDrawer(MenuItem item) {
        if (item != null && item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
            return true;
        }
        return false;
    }

    public static class DrawerItem {

        public String mTitle;
        public int mIconRes;

        public DrawerItem(String title, int iconRes) {
            mTitle = title;
            mIconRes = iconRes;
        }
    }

    public static class DrawerItemDivider extends DrawerItem {
        public DrawerItemDivider() {
            super(null, 0);
        }
    }

    public static class DrawerItemAccount extends DrawerItem {
        public DrawerItemAccount() {
            super(null, 0);
        }
    }

}
