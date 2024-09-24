package xyz.fadhilprawira.jmp_surveihakpilihkpu.db;

import android.provider.BaseColumns;

public class DatabaseContract {
    public static final class VoterColumns implements BaseColumns {
        public static final String TABLE_NAME = "voter";

        //Voter nik
        public static final String NIK = "nik";
        //Voter name
        public static final String NAMA = "nama";
        //Voter phone number
        public static final String NO_HP = "no_hp";
        //Voter jenis kelamin
        public static final String JENIS_KELAMIN = "jenis_kelamin";
        //Voter tanggal input
        public static final String TANGGAL_INPUT = "tanggal_input";
        //Voter alamat
        public static final String ALAMAT = "alamat";
        //Voter gambar
        public static final String GAMBAR = "gambar";
    }
}
