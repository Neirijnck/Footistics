package prebenneirijnck.be.footistics.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.melnykov.fab.FloatingActionButton;

import prebenneirijnck.be.footistics.R;
import prebenneirijnck.be.footistics.adapters.GamesAdapter;
import prebenneirijnck.be.footistics.provider.FootisticsContract;
import prebenneirijnck.be.footistics.util.Utils;

public class GamesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String TAG = "Games";
    private static final int GAMES_LOADER = 0;

    private GamesAdapter mAdapter;

    private GridView mGrid;

    public static GamesFragment newInstance() {
        GamesFragment f = new GamesFragment();

        Bundle args = new Bundle();
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getLoaderManager().initLoader(GAMES_LOADER, null, this);

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

        // Setup grid view
        mGrid = (GridView) getView().findViewById(R.id.listGames);

        // Setting the adapter
        mGrid.setAdapter(mAdapter);

        // Setup floating action button for adding games
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
            // Not handled here
            return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    private void fireTrackerEventAction(String label) {
        Utils.trackAction(getActivity(), TAG, label);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        switch (loaderId) {
            case GAMES_LOADER:
                return new CursorLoader(getActivity(), FootisticsContract.Games.CONTENT_URI, GamesAdapter.GamesQuery.PROJECTION, null, null, null);
            default:
                // An invalid id was passed in
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
