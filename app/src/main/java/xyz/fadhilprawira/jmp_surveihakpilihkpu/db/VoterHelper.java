package xyz.fadhilprawira.jmp_surveihakpilihkpu.db;

import static android.provider.BaseColumns._ID;
import static xyz.fadhilprawira.jmp_surveihakpilihkpu.db.DatabaseContract.VoterColumns.TABLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class VoterHelper {
    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DatabaseHelper dataBaseHelper;
    private static volatile VoterHelper INSTANCE;
    private static SQLiteDatabase database;

    // Singleton pattern. So it will only create one instance of the class
    private VoterHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static VoterHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new VoterHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    //    Open the database connection
    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    //    Close the database connection
    public void close() {
        dataBaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public Cursor queryAll() {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC");
    }

    //    Insert data to the database
    public long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

}
