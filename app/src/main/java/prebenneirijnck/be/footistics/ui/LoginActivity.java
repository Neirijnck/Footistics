package prebenneirijnck.be.footistics.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import prebenneirijnck.be.footistics.R;

/**
 * Activity that allows you to log in with facebook account
 */
public class LoginActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (savedInstanceState == null) {
            LoginFragment f = LoginFragment.newInstance();
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.activity_fade_enter, R.anim.activity_fade_exit).replace(R.id.content_frame, f).commit();
        }
    }
}