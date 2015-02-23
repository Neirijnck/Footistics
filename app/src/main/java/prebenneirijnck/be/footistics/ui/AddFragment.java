package prebenneirijnck.be.footistics.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import prebenneirijnck.be.footistics.R;

/**
 * Super class for fragments allowing to add games to the database.
 */
public class AddFragment extends Fragment{

    public static AddFragment newInstance() {
        AddFragment f = new AddFragment();

        Bundle args = new Bundle();
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_game, container, false);
    }

}
