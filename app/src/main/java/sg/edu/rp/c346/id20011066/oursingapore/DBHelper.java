package sg.edu.rp.c346.id20011066.oursingapore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "simpleisland.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_SONGS = "island";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "island_name";
    private static final String COLUMN_DESCRIPTION = "island_description";
    private static final String COLUMN_SQUARE_KM = "island_sqKM";
    private static final String COLUMN_STARS = "island_stars";;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSongTableSQL = "CREATE TABLE " + TABLE_SONGS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_SQUARE_KM + " INTEGER, "
                + COLUMN_STARS + " INTEGER ) ";
        db.execSQL(createSongTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONGS);
        onCreate(db);
    }
    public long insertIsland(String name, String description, int sqKM, int stars) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_SQUARE_KM, sqKM);
        values.put(COLUMN_STARS, stars);
        long result = db.insert(TABLE_SONGS, null, values);
        db.close();
        return result;
    }
    public ArrayList<Island> getAllIsland() {
        ArrayList<Island> notes = new ArrayList<>();

        String SQL = "SELECT " + COLUMN_ID + ", "
                + COLUMN_NAME + ", "
                + COLUMN_DESCRIPTION  + ", "
                + COLUMN_SQUARE_KM + ", "
                + COLUMN_STARS + " FROM " + TABLE_SONGS;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(SQL, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                int sqKM = cursor.getInt(3);
                int stars = cursor.getInt(4);
                Island island = new Island(id, name, description, sqKM, stars);
                island.setId(id);
                notes.add(island);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }
    public int updateIsland(Island data) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, data.getName());
        values.put(COLUMN_DESCRIPTION, data.getDescription());
        values.put(COLUMN_SQUARE_KM, data.getSqKM());
        values.put(COLUMN_STARS, data.getStars());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_SONGS, values, condition, args);
        db.close();
        return result;
    }
    public int deleteIsland(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_SONGS, condition, args);
        db.close();
        return result;
    }
    public ArrayList<Island> getAllIslandByStars(String keyword) {
        ArrayList<Island> songs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_SQUARE_KM, COLUMN_STARS};
        String condition = COLUMN_STARS + "= ?";
        String[] args = {keyword};
        Cursor cursor = db.query(TABLE_SONGS, columns, condition, args, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                int sqKM = cursor.getInt(3);
                int stars = cursor.getInt(4);
                Island song = new Island(id, name, description, sqKM, stars);
                song.setId(id);
                songs.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }
    public ArrayList<String> getSqKM() {
        ArrayList<String> codes = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_SQUARE_KM};

        Cursor cursor;
        cursor = db.query(true, TABLE_SONGS, columns, null, null, null, null, null, null);
        // Loop through all rows and add to ArrayList
        if (cursor.moveToFirst()) {
            do {
                codes.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        // Close connection
        cursor.close();
        db.close();
        return codes;
    }
    public ArrayList<Island> getAllIslandByYear(int yearFilter) {
        ArrayList<Island> songs = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_SQUARE_KM, COLUMN_STARS};
        String condition = COLUMN_SQUARE_KM + "= ?";
        String[] args = {String.valueOf(yearFilter)};

        Cursor cursor;
        cursor = db.query(TABLE_SONGS, columns, condition, args, null, null, null, null);

        // Loop through all rows and add to ArrayList
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                int sqKM = cursor.getInt(3);
                int stars = cursor.getInt(4);

                Island newIsland = new Island(id, name, description, sqKM, stars);
                songs.add(newIsland);
            } while (cursor.moveToNext());
        }
        // Close connection
        cursor.close();
        db.close();
        return songs;
    }
}

