package prebenneirijnck.be.footistics.provider;

import android.net.Uri;
import android.provider.BaseColumns;

import prebenneirijnck.be.footistics.FootisticsApplication;

public class FootisticsContract {

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://"
            + FootisticsApplication.CONTENT_AUTHORITY);

    public static final String PATH_PROFILE = "profile";

    public static final String PATH_GAMES = "games";

    public static final String PATH_SEASONS = "seasons";

    public static final String PATH_GLOBAL = "global";

    interface ProfileColums{

        /**
         * This column is NOT in this table, it is for reference purposes only.
         */
        String REF_PROFILE_ID = "profile_id";

        /**
         * The last name of the person logged in.
         */
        String LASTNAME ="lastname";

        /**
         * The first name of the person logged in.
         */
        String FIRSTNAME ="firstname";

        /**
         * The gender of the person.
         */
        String GENDER ="gender";

        /**
         * Day of birth of the person.
         */
        String BIRTHDAY ="birthday";

        /**
         * Email of the person.
         */
        String EMAIL ="email";

        /**
         * Location or city of the person.
         */
        String LOCATION ="location";

        /**
         * Length of the person.
         */
        String LENGTH ="length";

        /**
         * Weight of the person.
         */
        String WEIGHT ="weight";

        /**
         * Position on the field of the person.
         */
        String POSITION ="position";

        /**
         * Facebook token after login.
         */
        String FACEBOOKTOKEN ="facebook_token";

        /**
         * Facebook ID after login.
         */
        String FACEBOOKID ="facebook_id";

        /**
         * Facebook image after login.
         */
        String FACEBOOKIMAGE ="facebook_image";
    }

    interface GamesColumns{

        /**
         * This column is NOT in this table, it is for reference purposes only.
         */
        String REF_GAME_ID = "game_id";

        /**
         *  Season number. Reference to id of seasonscolumn.
         */
        String SEASON = "season";

        /**
         *  Team to play against.
         */
        String OPPONENT = "opponent";

        /**
         *  The date of the game played.
         */
        String DATE = "date";

        /**
         *  The result of the game played.
         */
        String RESULT = "result";

        /**
         *  The category of your team; first team, youth,...
         */
        String CATEGORY = "category";

        /**
         *  The position you played on the field.
         */
        String POSITION = "position";

        /**
         *  How long you played in this game.
         */
        String PLAYTIME = "playtime";

        /**
         *  How many assists you gave to other players.
         */
        String ASSISTS = "assists";

        /**
         *  How many goals you scored.
         */
        String GOALS = "goals";

        /**
         *  How many yellow cards you received.
         */
        String YELLOWCARDS = "yellowcards";

        /**
         *  Did you receive a red card?
         */
        String REDCARD = "redcard";

        /**
         *  How much money did you earn with this game?
         */
        String BONUS = "bonus";
    }

    interface SeasonsColumns{

        /**
         * This column is NOT in this table, it is for reference purposes only.
         */
        String REF_SEASON_ID = "season_id";

        /**
         *  The year of this season.
         */
        String YEAR = "year";

        /**
         *  The name of the team you played for.
         */
        String TEAM = "team";

        /**
         *  The head coach.
         */
        String T1 = "t1";

        /**
         *  The second coach.
         */
        String T2 = "t2";

        /**
         *  How many games you played this season.
         */
        String TOTALGAMES = "totalgames";

        /**
         *  How many goals you made this season.
         */
        String TOTALGOALS = "totalgoals";

        /**
         *  How many assists you gave this season.
         */
        String TOTALASSISTS= "totalassists";

        /**
         *  How many yellow cards you got this season.
         */
        String TOTALYELLOWCARDS = "totalyellowcards";

        /**
         *  How many red cards you got this season.
         */
        String TOTALREDCARDS = "totalredcards";

        /**
         *  How much money you received this season.
         */
        String TOTALBONUS = "totalbonus";
    }

    interface GlobalColumns{

        /**
         * This column is NOT in this table, it is for reference purposes only.
         */
        String REF_GLOBAL_ID = "global_id";

        /**
         * The total of different teams you played for.
         */
        String TOTALTEAMS = "totalteams";

        /**
         * The total of years you played football.
         */
        String TOTALYEARS = "totalyears";

        /**
         * The total of games you played in your career.
         */
        String TOTALCAREERGAMES = "totalcareergames";

        /**
         * The total of goals you made in your career.
         */
        String TOTALCAREERGOALS = "totalcareergoals";

        /**
         * The total of assists you gave in your career.
         */
        String TOTALCAREERASSISTS = "totalcareerassists";

        /**
         * The total of yellow cards you received in your career.
         */
        String TOTALCAREERYELLOWCARDS = "totalcareeryellowcards";

        /**
         * The total of red cards you received in your career.
         */
        String TOTALCAREERREDCARDS = "totalcareerredcards";
    }

    public static class Profile implements ProfileColums, BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PROFILE).build();

        /**
         * Use if multiple items get returned
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.footistics.profile";

        /**
         * Use if a single item is returned
         */
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.footistics.profile";

        public static Uri buildProfileUri(String profileId){
            return CONTENT_URI.buildUpon().appendPath(profileId).build();
        }

        public static String getProfileId(Uri uri){
            return uri.getLastPathSegment();
        }

    }

    public static class Games implements GamesColumns, BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_GAMES).build();

        /**
         * Use if multiple items get returned
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.footistics.game";

        /**
         * Use if a single item is returned
         */
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.footistics.game";

        public static Uri buildGameUri(String gameId){
            return CONTENT_URI.buildUpon().appendPath(gameId).build();
        }

        public static String getGameId(Uri uri){
            return uri.getLastPathSegment();
        }

    }

    public static class Seasons implements SeasonsColumns, BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SEASONS).build();

        /**
         * Use if multiple items get returned
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.footistics.season";

        /**
         * Use if a single item is returned
         */
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.footistics.season";

        public static Uri buildSeasonUri(String seasonId){
            return CONTENT_URI.buildUpon().appendPath(seasonId).build();
        }

        public static String getSeasonId(Uri uri){
            return uri.getLastPathSegment();
        }

    }

    public static class Global implements GlobalColumns, BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_GLOBAL).build();

        /**
         * Use if multiple items get returned
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.footistics.global";

        /**
         * Use if a single item is returned
         */
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.footistics.global";

        public static Uri buildGlobalUri(String globalId){
            return CONTENT_URI.buildUpon().appendPath(globalId).build();
        }

        public static String getGlobalId(Uri uri){
            return uri.getLastPathSegment();
        }

    }

}
