package prebenneirijnck.be.footistics.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import prebenneirijnck.be.footistics.R;

public class OverviewFragment extends Fragment {

    public static OverviewFragment newInstance() {
        OverviewFragment f = new OverviewFragment();

        Bundle args = new Bundle();
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overview, container, false);
    }

}
