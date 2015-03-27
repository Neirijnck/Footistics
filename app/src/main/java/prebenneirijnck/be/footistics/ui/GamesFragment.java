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
import android.widget.GridView;

import com.melnykov.fab.FloatingActionButton;

import prebenneirijnck.be.footistics.R;
import prebenneirijnck.be.footistics.util.Utils;

public class GamesFragment extends Fragment {

    private static final String TAG = "Games";

    private GridView mGrid;

    public static GamesFragment newInstance() {
        GamesFragment f = new GamesFragment();

        Bundle args = new Bundle();
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_games, container, false);

        v.findViewById(R.id.emptyViewGames).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddActivity.class));
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // setup grid view
        mGrid = (GridView) getView().findViewById(R.id.listGames);

        // setup floating action button for adding games
        final FloatingActionButton buttonAddGame = (FloatingActionButton) getView().findViewById(R.id.buttonGamesAdd);
        buttonAddGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddActivity.class));
            }
        });

        buttonAddGame.attachToListView(mGrid);

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
        if(itemId == R.id.menu_action_filter_games){
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
