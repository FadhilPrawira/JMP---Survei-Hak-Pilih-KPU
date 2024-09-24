package xyz.fadhilprawira.jmp_surveihakpilihkpu.db;

import static xyz.fadhilprawira.jmp_surveihakpilihkpu.db.DatabaseContract.VoterColumns.TABLE_NAME;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import xyz.fadhilprawira.jmp_surveihakpilihkpu.db.DatabaseContract.VoterColumns;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "data_voter.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," + //nik
                    " %s TEXT NOT NULL," + //nama
                    " %s TEXT NOT NULL," + //no_hp
                    " %s TEXT NOT NULL," + //jenis_kelamin
                    " %s TEXT NOT NULL," + //tanggal_input
                    " %s TEXT NOT NULL," + //alamat
                    " %s TEXT NOT NULL)",//gambar
            TABLE_NAME,
            VoterColumns._ID,
            VoterColumns.NIK,
            VoterColumns.NAMA,
            VoterColumns.NO_HP,
            VoterColumns.JENIS_KELAMIN,
            VoterColumns.TANGGAL_INPUT,
            VoterColumns.ALAMAT,
            VoterColumns.GAMBAR
    );

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
