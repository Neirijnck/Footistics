package prebenneirijnck.be.footistics.ui;


import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import prebenneirijnck.be.footistics.R;
import prebenneirijnck.be.footistics.provider.FootisticsContract;

/**
 * Super class for fragments allowing to add games to the database.
 */
public class AddFragment extends Fragment implements Validator.ValidationListener{

    @NotEmpty
    private EditText addEditTextDate;
    @NotEmpty
    private EditText addEditTextOpponent;
    @NotEmpty
    private EditText addEditTextScore;
    @NotEmpty
    private EditText addEditTextGoals;
    @NotEmpty
    private EditText addEditTextAssists;
    @NotEmpty
    private EditText addEditTextPlaytime;
    @NotEmpty
    private EditText addEditTextBonus;

    private Button addButtonNewGame;
    private TextView addTextViewYellowCards;
    private Spinner addSpinnerPosition;
    private Spinner addSpinnerCategory;
    private SeekBar addSeekBarYellowCards;
    private CheckBox addCheckBoxRedCard;
    private DatePickerDialog addDatePickerDialog;

    private Validator validator;

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
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_add_game, container, false);

        // Get views
        addButtonNewGame = (Button) v.findViewById(R.id.addGameSaveBtn);
        addSeekBarYellowCards = (SeekBar) v.findViewById(R.id.addGameYellowCard);
        addTextViewYellowCards = (TextView) v.findViewById(R.id.addGameYellowCardsNr);
        addCheckBoxRedCard = (CheckBox) v.findViewById(R.id.addGameRedCardCheck);
        addEditTextDate = (EditText) v.findViewById(R.id.addGameDateText);
        addEditTextOpponent = (EditText) v.findViewById(R.id.addGameOpponentText);
        addEditTextScore = (EditText) v.findViewById(R.id.addGameScoreText);
        addEditTextGoals = (EditText) v.findViewById(R.id.addGameGoalsText);
        addEditTextAssists = (EditText) v.findViewById(R.id.addGameAssistsText);
        addEditTextPlaytime = (EditText) v.findViewById(R.id.addGamePlaytimeText);
        addEditTextBonus = (EditText) v.findViewById(R.id.addGameBonusText);
        addSpinnerPosition = (Spinner) v.findViewById(R.id.addGamePosition);
        addSpinnerCategory = (Spinner) v.findViewById(R.id.addGameCategory);

        // Set listeners
        addButtonNewGame.setOnClickListener(onSaveGame);
        addSeekBarYellowCards.setOnSeekBarChangeListener(onSeekBarChangeListener);
        addEditTextDate.setOnClickListener(onDateClickListener);

        return v;
    }

    private View.OnClickListener onSaveGame = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            // Validation
            validator.validate();
        }
    };

    private View.OnClickListener onDateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addDatePickerDialog.show();
        }
    };

    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // Update textview with number of yellow cards and red card checkbox when 2 yellow cards
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

    private void saveGame(String opponent, String date, String result, String category, String position, String playtime, String assists, String goals, int yellowCards, boolean redCard, String bonus){
        String red = (redCard) ? "1" : "0";

        // Build ContentValues
        ContentValues values = new ContentValues();
        values.put(FootisticsContract.Games.OPPONENT, opponent);
        values.put(FootisticsContract.Games.DATE, date);
        values.put(FootisticsContract.Games.RESULT, result);
        values.put(FootisticsContract.Games.CATEGORY, category);
        values.put(FootisticsContract.Games.POSITION, position);
        values.put(FootisticsContract.Games.PLAYTIME, playtime);
        values.put(FootisticsContract.Games.ASSISTS, assists);
        values.put(FootisticsContract.Games.GOALS, goals);
        values.put(FootisticsContract.Games.YELLOWCARDS, String.valueOf(yellowCards));
        values.put(FootisticsContract.Games.REDCARD, red);
        values.put(FootisticsContract.Games.BONUS, bonus);

        // Insert the record into database
        getActivity().getContentResolver().insert(FootisticsContract.Games.CONTENT_URI, values);
    }

    @Override
    public void onValidationSucceeded() {
        // Save new game in db
        saveGame(
                addEditTextOpponent.getText().toString(),
                addEditTextDate.getText().toString(),
                addEditTextScore.getText().toString(),
                addSpinnerCategory.getSelectedItem().toString(),
                addSpinnerPosition.getSelectedItem().toString(),
                addEditTextPlaytime.getText().toString(),
                addEditTextAssists.getText().toString(),
                addEditTextGoals.getText().toString(),
                addSeekBarYellowCards.getProgress(),
                addCheckBoxRedCard.isChecked(),
                addEditTextBonus.getText().toString());
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        Toast.makeText(getActivity(), getResources().getString(R.string.should_not_be_empty), Toast.LENGTH_SHORT).show();
    }
}
