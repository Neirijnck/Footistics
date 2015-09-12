package prebenneirijnck.be.footistics.ui;

import android.app.DatePickerDialog;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import prebenneirijnck.be.footistics.R;
import prebenneirijnck.be.footistics.widgets.NotifyingScrollView;

public class EditProfileFragment extends Fragment{

    private ActionBar toolbar;
    private ImageView imageHeader;
    private Drawable mActionBarBackgroundDrawable;
    private EditText profileEditTextDate;
    private DatePickerDialog profileDatePickerDialog;

    public static EditProfileFragment newInstance() {
        EditProfileFragment f = new EditProfileFragment();

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetDateField();

        //Callback for scrollable actionbar
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mActionBarBackgroundDrawable.setCallback(mDrawableCallback);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_edit_profile, container, false);

        //Get views
        toolbar = ((ActionBarActivity)getActivity()).getSupportActionBar();
        mActionBarBackgroundDrawable = getResources().getDrawable(R.color.ft_primary);
        mActionBarBackgroundDrawable.setAlpha(0);
        toolbar.setBackgroundDrawable(mActionBarBackgroundDrawable);
        imageHeader = (ImageView) v.findViewById(R.id.profileImg);
        profileEditTextDate = (EditText) v.findViewById(R.id.profileDateText);

        //Set listeners
        ((NotifyingScrollView) v.findViewById(R.id.editProfileScrollView)).setOnScrollChangedListener(mOnScrollChangedListener);
        profileEditTextDate.setOnClickListener(onDateClickListener);

        return v;
    }

    private void SetDateField(){
        Calendar c = Calendar.getInstance();
        profileDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
                newDate.set(year, monthOfYear, dayOfMonth);
                profileEditTextDate.setText(dateFormatter.format(newDate.getTime()));
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
    }

    private View.OnClickListener onDateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            profileDatePickerDialog.show();
        }
    };

    private NotifyingScrollView.OnScrollChangedListener mOnScrollChangedListener = new NotifyingScrollView.OnScrollChangedListener() {
        public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
            final int headerHeight = imageHeader.getHeight() - toolbar.getHeight();
            final float ratio = (float) Math.min(Math.max(t, 0), headerHeight) / headerHeight;
            final int newAlpha = (int) (ratio * 255);
            mActionBarBackgroundDrawable.setAlpha(newAlpha);
        }
    };

    private Drawable.Callback mDrawableCallback = new Drawable.Callback() {
        @Override
        public void invalidateDrawable(Drawable who) {
            toolbar.setBackgroundDrawable(who);
        }

        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when) {
        }

        @Override
        public void unscheduleDrawable(Drawable who, Runnable what) {
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}