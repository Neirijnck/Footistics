package prebenneirijnck.be.footistics.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import prebenneirijnck.be.footistics.R;

public class LoginFragment extends Fragment{

    public static LoginFragment newInstance() {
        LoginFragment f = new LoginFragment();

        Bundle args = new Bundle();
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_login, container, false);

        return v;
    }

}
