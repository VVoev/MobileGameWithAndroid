package me.icytower.UltimateCop.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbManager extends SQLiteOpenHelper {

    private static DbManager sInstance;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "scores.db";

    public static final String TABLE_SCORES = "scores";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "playerInitials";
    public static final String COLUMN_SCORE = "score";


    public DbManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    private DbManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DbManager getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DbManager(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_SCORES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                COLUMN_NAME + " TEXT ," +
                COLUMN_SCORE + " INTEGER " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + TABLE_SCORES);
        onCreate(db);
    }

    //ADD
    public void addNewScore(Scores score) {
        ContentValues values = new ContentValues();
        String name = score.getPlayerInitials();
        int points = score.getScore();
        values.put(COLUMN_NAME, score.getPlayerInitials());
        values.put(COLUMN_SCORE, score.getScore());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_SCORES, null, values);
        db.close();
    }

    //DELETE
    public void deleteScore(String playerInitials) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_SCORES + " WHERE " + COLUMN_NAME + "=\"" + playerInitials + "\";");
    }

    //DEBUG PRINT DATABASE
    public String databaseToString() {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_SCORES;

        Cursor c = db.rawQuery(query, null);
        //c.moveToFirst();

        while (c.moveToNext()) {
            if (c.getString(c.getColumnIndex("playerInitials")) != null) {
                dbString += c.getString(c.getColumnIndex("playerInitials"));
            }
            if (c.getString(c.getColumnIndex("score")) != null) {
                dbString += c.getString(c.getColumnIndex("score"));
                dbString += "\n";
            }
        }
        db.close();
        return dbString;
    }

    //SORT
    public String giveMeBestTenPlayers() {
        int i = 1;
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + COLUMN_NAME+"," +COLUMN_SCORE+ " FROM " + TABLE_SCORES + " ORDER BY " + COLUMN_SCORE + " DESC limit 10";

        Cursor c = db.rawQuery(query, null);

        while (c.moveToNext()) {
            dbString+=i + ".";
            if (c.getString(c.getColumnIndex("playerInitials")) != null) {
                dbString += c.getString(c.getColumnIndex("playerInitials"));

            }
            dbString += " ";
            if (c.getString(c.getColumnIndex("score")) != null) {
                dbString += c.getString(c.getColumnIndex("score"));
                dbString += "\n";
            }
            i++;
        }

        db.close();
        return dbString;

    }
}

