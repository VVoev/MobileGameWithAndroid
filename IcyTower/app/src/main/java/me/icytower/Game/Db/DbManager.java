package me.icytower.Game.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "scores.db";

    public static final String TABLE_SCORES = "scores";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "playerInitials";

    public DbManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_SCORES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                COLUMN_NAME + " TEXT " +
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
        values.put(COLUMN_NAME, score.getPlayerInitials());
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
                dbString += "\n";
            }
        }
        db.close();
        return dbString;
    }
}
