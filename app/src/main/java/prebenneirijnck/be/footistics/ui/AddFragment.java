package prebenneirijnck.be.footistics.ui;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import prebenneirijnck.be.footistics.R;

/**
 * Super class for fragments allowing to add games to the database.
 */
public class AddFragment extends Fragment{

    private SeekBar addSeekBarYellowCards;
    private TextView addTextViewYellowCards;
    private CheckBox addCheckBoxRedCard;
    private EditText addEditTextDate;
    private DatePickerDialog addDatePickerDialog;

    public static AddFragment newInstance() {
        AddFragment f = new AddFragment();

        Bundle args = new Bundle();
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetDateField();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_add_game, container, false);

        //Get views
        addSeekBarYellowCards = (SeekBar) v.findViewById(R.id.addGameYellowCard);
        addTextViewYellowCards = (TextView) v.findViewById(R.id.addGameYellowCardsNr);
        addCheckBoxRedCard = (CheckBox) v.findViewById(R.id.addGameRedCardCheck);
        addEditTextDate = (EditText) v.findViewById(R.id.addGameDateText);

        //Set  listeners
        addSeekBarYellowCards.setOnSeekBarChangeListener(onSeekBarChangeListener);
        addEditTextDate.setOnClickListener(onDateClickListener);

        return v;
    }

    private View.OnClickListener onDateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addDatePickerDialog.show();
        }
    };

    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            //Update textview with number of yellow cards and red card checkbox when 2 yellow cards
            addTextViewYellowCards.setText(Integer.toString(progress));
            if(progress == 2){
                addCheckBoxRedCard.setChecked(true);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            //Do nothing
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            //Do nothing
        }
    };

    private void SetDateField(){
        Calendar c = Calendar.getInstance();
        addDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
                newDate.set(year, monthOfYear, dayOfMonth);
                addEditTextDate.setText(dateFormatter.format(newDate.getTime()));
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
    }

}
