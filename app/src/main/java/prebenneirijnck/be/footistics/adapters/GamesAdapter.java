package prebenneirijnck.be.footistics.adapters;

import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import prebenneirijnck.be.footistics.R;
import prebenneirijnck.be.footistics.provider.FootisticsContract;

/**
 * Adapter using the item_game.xml layout with a Viewholder
 */
public class GamesAdapter extends CursorAdapter{

    protected LayoutInflater mLayoutInflater;

    private final int LAYOUT = R.layout.item_game;

    public GamesAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = mLayoutInflater.inflate(LAYOUT, parent, false);

        ViewHolder viewHolder = new ViewHolder();
        viewHolder.opponent = (TextView) v.findViewById(R.id.game_clubname_opponent);
        viewHolder.myTeam = (TextView) v.findViewById(R.id.game_clubname);
        viewHolder.score = (TextView) v.findViewById(R.id.game_score);
        viewHolder.goals = (TextView) v.findViewById(R.id.game_nr_goals);
        viewHolder.position = (TextView) v.findViewById(R.id.game_position);
        viewHolder.yellowCards = (TextView) v.findViewById(R.id.game_nr_yellow);
        viewHolder.redCards = (TextView) v.findViewById(R.id.game_nr_red);
        viewHolder.clubCrest = (ImageView) v.findViewById(R.id.game_clubicon);

        v.setTag(viewHolder);

        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        viewHolder.opponent.setText(cursor.getString(GamesQuery.OPPONENT));
        viewHolder.score.setText(cursor.getString(GamesQuery.RESULT));
        viewHolder.goals.setText(cursor.getString(GamesQuery.GOALS));
        viewHolder.position.setText(cursor.getString(GamesQuery.POSITION));
        viewHolder.yellowCards.setText(cursor.getString(GamesQuery.YELLOWCARDS));
        viewHolder.redCards.setText(cursor.getString(GamesQuery.REDCARD));
    }

    private interface GamesQuery{
        String[] PROJECTION = {
                BaseColumns._ID,
                FootisticsContract.Games.OPPONENT,
                FootisticsContract.Games.RESULT,
                FootisticsContract.Games.GOALS,
                FootisticsContract.Games.POSITION,
                FootisticsContract.Games.YELLOWCARDS,
                FootisticsContract.Games.REDCARD
        };

        int _ID = 0;
        int OPPONENT = 1;
        int RESULT = 2;
        int GOALS = 3;
        int POSITION = 4;
        int YELLOWCARDS = 5;
        int REDCARD = 6;
    }

    public static class ViewHolder{

        public TextView opponent;

        public TextView myTeam;

        public TextView score;

        public TextView goals;

        public TextView position;

        public TextView yellowCards;

        public TextView redCards;

        public ImageView clubCrest;
    }

}
