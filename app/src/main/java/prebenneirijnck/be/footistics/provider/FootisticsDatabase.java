package prebenneirijnck.be.footistics.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import timber.log.Timber;

public class FootisticsDatabase extends SQLiteOpenHelper{

    public static final String DATABASE_NAME="footisticsdatabase";

    /**
     * Different versions of the database.
     */
    public static final int DBVER_INITIAL = 1;

    /**
     * Current version of the database.
     */
    public static final int DATABASE_VERSION = DBVER_INITIAL;

    private DatabaseUtils.InsertHelper mProfileInserter;
    private DatabaseUtils.InsertHelper mGamesInserter;
    private DatabaseUtils.InsertHelper mSeasonsInserter;
    private DatabaseUtils.InsertHelper mGlobalInserter;

    /**
     * Qualifies column names by prefixing their {@link Tables} name.
     */
    public interface Qualified {

        String GAMES_ID = Tables.GAMES + "." + FootisticsContract.Games._ID;
        String GAMES_SEASON_ID = Tables.GAMES + "." + FootisticsContract.Seasons.REF_SEASON_ID;
        String SEASONS_ID = Tables.SEASONS + "." + FootisticsContract.Seasons._ID;
    }

    public interface Tables{

        String PROFILE = "profile";

        String GAMES = "games";

        String SEASONS = "seasons";

        String GLOBAL = "global";
    }

    interface References {

        String SEASON_ID = "REFERENCES " + Tables.SEASONS + "(" + BaseColumns._ID + ")";

    }

    private static final String CREATE_PROFILE_TABLE = "CREATE TABLE " + Tables.PROFILE
            + " ("

            + BaseColumns._ID + " INTEGER PRIMARY KEY,"

            + FootisticsContract.ProfileColumns.LASTNAME + " TEXT,"

            + FootisticsContract.ProfileColumns.FIRSTNAME + " TEXT,"

            + FootisticsContract.ProfileColumns.GENDER + " TEXT,"

            + FootisticsContract.ProfileColumns.BIRTHDAY + " TEXT,"

            + FootisticsContract.ProfileColumns.EMAIL + " TEXT,"

            + FootisticsContract.ProfileColumns.LOCATION + " TEXT,"

            + FootisticsContract.ProfileColumns.LENGTH + " TEXT,"

            + FootisticsContract.ProfileColumns.WEIGHT + " TEXT,"

            + FootisticsContract.ProfileColumns.POSITION + " TEXT,"

            + FootisticsContract.ProfileColumns.FACEBOOKTOKEN + " TEXT,"

            + FootisticsContract.ProfileColumns.FACEBOOKID + " TEXT,"

            + FootisticsContract.ProfileColumns.FACEBOOKIMAGE + " BLOB"

            + ");";

    private static final String CREATE_GAMES_TABLE = "CREATE TABLE " +Tables.GAMES

            + " ("

            + BaseColumns._ID + " INTEGER PRIMARY KEY,"

            + FootisticsContract.SeasonsColumns.REF_SEASON_ID + " TEXT " + References.SEASON_ID + ","

            + FootisticsContract.GamesColumns.OPPONENT + " TEXT,"

            + FootisticsContract.GamesColumns.DATE + " TEXT,"

            + FootisticsContract.GamesColumns.RESULT + " TEXT,"

            + FootisticsContract.GamesColumns.CATEGORY + " TEXT,"

            + FootisticsContract.GamesColumns.POSITION + " INTEGER DEFAULT 0,"

            + FootisticsContract.GamesColumns.PLAYTIME + " INTEGER DEFAULT 0,"

            + FootisticsContract.GamesColumns.ASSISTS + " INTEGER DEFAULT 0,"

            + FootisticsContract.GamesColumns.GOALS + " INTEGER DEFAULT 0,"

            + FootisticsContract.GamesColumns.YELLOWCARDS + " INTEGER DEFAULT 0,"

            + FootisticsContract.GamesColumns.REDCARD + " INTEGER DEFAULT 0,"

            + FootisticsContract.GamesColumns.BONUS + " INTEGER DEFAULT 0"

            + ");";

    private static final String CREATE_SEASONS_TABLE= "CREATE TABLE " + Tables.SEASONS

            + " ("

            + BaseColumns._ID + " INTEGER PRIMARY KEY,"

            + FootisticsContract.SeasonsColumns.YEAR + " TEXT,"

            + FootisticsContract.SeasonsColumns.TEAM + " TEXT,"

            + FootisticsContract.SeasonsColumns.T1 + " TEXT,"

            + FootisticsContract.SeasonsColumns.T2 + " TEXT,"

            + FootisticsContract.SeasonsColumns.TOTALGAMES + " INTEGER DEFAULT 0,"

            + FootisticsContract.SeasonsColumns.TOTALASSISTS + " INTEGER DEFAULT 0,"

            + FootisticsContract.SeasonsColumns.TOTALGOALS + " INTEGER DEFAULT 0,"

            + FootisticsContract.SeasonsColumns.TOTALYELLOWCARDS + " INTEGER DEFAULT 0,"

            + FootisticsContract.SeasonsColumns.TOTALREDCARDS + " INTEGER DEFAULT 0,"

            + FootisticsContract.SeasonsColumns.TOTALBONUS + " INTEGER DEFAULT 0"

            + ");";

    private static final String CREATE_GLOBAL_TABLE= "CREATE TABLE " + Tables.GLOBAL

            + " ("

            + BaseColumns._ID + " INTEGER PRIMARY KEY,"

            + FootisticsContract.GlobalColumns.TOTALTEAMS + " INTEGER DEFAULT 1,"

            + FootisticsContract.GlobalColumns.TOTALYEARS + " INTEGER DEFAULT 1,"

            + FootisticsContract.GlobalColumns.TOTALCAREERGAMES + " INTEGER DEFAULT 0,"

            + FootisticsContract.GlobalColumns.TOTALCAREERGOALS + " INTEGER DEFAULT 0,"

            + FootisticsContract.GlobalColumns.TOTALCAREERASSISTS + " INTEGER DEFAULT 0,"

            + FootisticsContract.GlobalColumns.TOTALCAREERYELLOWCARDS + " INTEGER DEFAULT 0,"

            + FootisticsContract.GlobalColumns.TOTALCAREERREDCARDS + " INTEGER DEFAULT 0"

            + ");";

    public FootisticsDatabase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public long insertProfile(ContentValues values){
        return mProfileInserter.insert(values);
    }

    public long insertGames(ContentValues values){
        return mGamesInserter.insert(values);
    }

    public long insertSeasons(ContentValues values){
        return mSeasonsInserter.insert(values);
    }

    public long insertGlobal(ContentValues values){
        return mGlobalInserter.insert(values);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROFILE_TABLE);

        db.execSQL(CREATE_GAMES_TABLE);

        db.execSQL(CREATE_SEASONS_TABLE);

        db.execSQL(CREATE_GLOBAL_TABLE);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        mProfileInserter = new DatabaseUtils.InsertHelper(db, Tables.PROFILE);
        mGamesInserter = new DatabaseUtils.InsertHelper(db, Tables.GAMES);
        mSeasonsInserter = new DatabaseUtils.InsertHelper(db, Tables.SEASONS);
        mGlobalInserter = new DatabaseUtils.InsertHelper(db, Tables.GLOBAL);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Timber.d("Can't downgrade from version " + oldVersion + " to " + newVersion);
        onResetDatabase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Timber.d("Upgrading from " + oldVersion + " to " + newVersion);

        //run necessary upgrades
        int version = oldVersion;
        switch(version){
            case DBVER_INITIAL:
                onCreate(db);
        }

        //drop all tables if version is not right
        Timber.d("After upgrade at version " + version);
        if(version != DATABASE_VERSION){
            onResetDatabase(db);
        }
    }

    /**
     * Drops all tables and creates an empty database.
     */
    private void onResetDatabase(SQLiteDatabase db){
        Timber.w("Resetting database");
        db.execSQL("DROP TABLE IF EXISTS " + Tables.PROFILE);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.GAMES);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.SEASONS);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.GLOBAL);

        onCreate(db);
    }

}
