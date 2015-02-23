package prebenneirijnck.be.footistics.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import prebenneirijnck.be.footistics.R;
import prebenneirijnck.be.footistics.util.Utils;

public class GamesFragment extends Fragment {

    private static final String TAG = "Games";

    public static GamesFragment newInstance() {
        GamesFragment f = new GamesFragment();

        Bundle args = new Bundle();
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_games, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.games_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.menu_action_add_game){
            startActivity(new Intent(getActivity(), AddActivity.class));
            return true;
        } else if(itemId == R.id.menu_action_filter_games){
            fireTrackerEventAction("Filter games");
            // not handled here
            return super.onOptionsItemSelected(item);
        }


        return super.onOptionsItemSelected(item);
    }

    private void fireTrackerEventAction(String label) {
        Utils.trackAction(getActivity(), TAG, label);
    }

}
