package prebenneirijnck.be.footistics.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import prebenneirijnck.be.footistics.FootisticsApplication;
import prebenneirijnck.be.footistics.util.SelectionBuilder;
import timber.log.Timber;

public class FootisticsProvider extends ContentProvider{

    private static UriMatcher sUriMatcher;

    private static final int PROFILE = 100;

    private static final int PROFILE_ID = 101;

    private static final int GAMES = 200;

    private static final int GAMES_ID = 201;

    private static final int SEASONS = 300;

    private static final int SEASONS_ID = 301;

    private static final int GLOBAL = 400;

    private static final int GLOBAL_ID = 401;

    private FootisticsDatabase mDbHelper;

    /**
     * Build and return a {@link UriMatcher} that catches all {@link Uri} variations supported by
     * this {@link ContentProvider}.
     */
    private static UriMatcher buildUriMatcher(Context context){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = FootisticsApplication.CONTENT_AUTHORITY;

        //Profile
        matcher.addURI(authority, FootisticsContract.PATH_PROFILE, PROFILE);
        matcher.addURI(authority, FootisticsContract.PATH_PROFILE + "/*", PROFILE_ID);
        //Games
        matcher.addURI(authority, FootisticsContract.PATH_GAMES, GAMES);
        matcher.addURI(authority, FootisticsContract.PATH_GAMES + "/*", GAMES_ID);
        //Seasons
        matcher.addURI(authority, FootisticsContract.PATH_SEASONS, SEASONS);
        matcher.addURI(authority, FootisticsContract.PATH_SEASONS + "/*", SEASONS_ID);
        //Global
        matcher.addURI(authority, FootisticsContract.PATH_GLOBAL, GLOBAL);
        matcher.addURI(authority, FootisticsContract.PATH_GLOBAL + "/*", GLOBAL_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();

        sUriMatcher = buildUriMatcher(context);

        mDbHelper = new FootisticsDatabase(context);

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mDbHelper.getReadableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match){
            default: {
                final SelectionBuilder builder = buildSelection(uri, match);
                Cursor query = builder.where(selection, selectionArgs).query(db, projection, sortOrder);
                if(query != null){
                    query.setNotificationUri(getContext().getContentResolver(), uri);
                }
                return query;
            }
        }
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case PROFILE:
                return FootisticsContract.Profile.CONTENT_TYPE;
            case PROFILE_ID:
                return FootisticsContract.Profile.CONTENT_ITEM_TYPE;
            case GAMES:
                return FootisticsContract.Games.CONTENT_TYPE;
            case GAMES_ID:
                return FootisticsContract.Games.CONTENT_ITEM_TYPE;
            case SEASONS:
                return FootisticsContract.Seasons.CONTENT_TYPE;
            case SEASONS_ID:
                return FootisticsContract.Seasons.CONTENT_ITEM_TYPE;
            case GLOBAL:
                return FootisticsContract.Global.CONTENT_TYPE;
            case GLOBAL_ID:
                return FootisticsContract.Global.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri newItemUri;

        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.beginTransaction();
        try{
            newItemUri = insertInTransaction(uri, values);
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }

        if(newItemUri != null){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return newItemUri;
    }

    private Uri insertInTransaction(Uri uri, ContentValues values){
        Uri notifyUri = null;

        final int match = sUriMatcher.match(uri);
        switch (match){
            case PROFILE:{
                long id = mDbHelper.insertProfile(values);
                if(id<0){
                    break;
                }
                notifyUri = FootisticsContract.Profile.buildProfileUri(values.getAsString(FootisticsContract.Profile._ID));
                break;
            }
            case GAMES:{
                long id = mDbHelper.insertGames(values);
                if(id<0){
                    break;
                }
                notifyUri = FootisticsContract.Games.buildGameUri(values.getAsString(FootisticsContract.Games._ID));
                break;
            }
            case SEASONS:{
                long id = mDbHelper.insertSeasons(values);
                if(id<0){
                    break;
                }
                notifyUri = FootisticsContract.Seasons.buildSeasonUri(values.getAsString(FootisticsContract.Seasons._ID));
                break;
            }
            case GLOBAL:{
                long id = mDbHelper.insertGlobal(values);
                if(id<0){
                    break;
                }
                notifyUri = FootisticsContract.Global.buildGlobalUri(values.getAsString(FootisticsContract.Global._ID));
                break;
            }
        }
        return notifyUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Timber.v("delete(uri=" + uri + ")");

        int count = 0;

        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.beginTransaction();
        try{
            count = buildSelection(uri, sUriMatcher.match(uri)).where(selection, selectionArgs).delete(db);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        if(count > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Timber.v("update(uri=" + uri + ", values=" + values.toString() + ")");

        int count = 0;

        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.beginTransaction();
        try{
            count = buildSelection(uri, sUriMatcher.match(uri)).where(selection, selectionArgs).update(db, values);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        if(count > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return count;
    }

    private static SelectionBuilder buildSelection(Uri uri, int match){
        final SelectionBuilder builder = new SelectionBuilder();
        switch (match){
            case PROFILE:{
                return builder.table(FootisticsDatabase.Tables.PROFILE);
            }
            case PROFILE_ID:{
                return builder.table(FootisticsDatabase.Tables.PROFILE).where(FootisticsContract.Profile._ID + "=?", FootisticsContract.Profile.getProfileId(uri));
            }
            case GAMES:{
                return builder.table(FootisticsDatabase.Tables.GAMES);
            }
            case GAMES_ID:{
                return builder.table(FootisticsDatabase.Tables.GAMES).where(FootisticsContract.Games._ID + "=?", FootisticsContract.Games.getGameId(uri));
            }
            case SEASONS:{
                return builder.table(FootisticsDatabase.Tables.SEASONS);
            }
            case SEASONS_ID:{
                return builder.table(FootisticsDatabase.Tables.SEASONS).where(FootisticsContract.Seasons._ID + "=?", FootisticsContract.Seasons.getSeasonId(uri));
            }
            case GLOBAL:{
                return builder.table(FootisticsDatabase.Tables.GLOBAL);
            }
            case GLOBAL_ID:{
                return builder.table(FootisticsDatabase.Tables.GLOBAL).where(FootisticsContract.Global._ID + "=?", FootisticsContract.Global.getGlobalId(uri));
            }
            default:{
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

}
