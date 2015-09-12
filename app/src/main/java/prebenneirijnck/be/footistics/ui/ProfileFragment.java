package prebenneirijnck.be.footistics.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import prebenneirijnck.be.footistics.R;

public class ProfileFragment extends Fragment{

    private ActionBar toolbar;
    private Drawable mActionBarBackgroundDrawable;

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

        toolbar = ((ActionBarActivity)getActivity()).getSupportActionBar();
        mActionBarBackgroundDrawable = getResources().getDrawable(R.color.ft_primary);
        mActionBarBackgroundDrawable.setAlpha(255);
        toolbar.setBackgroundDrawable(mActionBarBackgroundDrawable);

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
                Intent intent = new Intent(getActivity(), EditActivity.class);
                Bundle b = new Bundle();
                b.putString("type", this.getClass().getSimpleName());
                intent.putExtras(b);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}