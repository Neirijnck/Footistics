package prebenneirijnck.be.footistics.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import prebenneirijnck.be.footistics.R;

public class ProfileFragment extends Fragment{

    public static ProfileFragment newInstance() {
        ProfileFragment f = new ProfileFragment();

        Bundle args = new Bundle();
        f.setArguments(args);

        return f;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_profile, container, false);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.profile_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            case R.id.menu_action_edit:
                EditProfileFragment f = EditProfileFragment.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}