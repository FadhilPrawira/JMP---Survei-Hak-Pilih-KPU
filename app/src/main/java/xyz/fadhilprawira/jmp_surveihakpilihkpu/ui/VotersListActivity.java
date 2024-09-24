package xyz.fadhilprawira.jmp_surveihakpilihkpu.ui;

import static xyz.fadhilprawira.jmp_surveihakpilihkpu.db.DatabaseContract.VoterColumns.ALAMAT;
import static xyz.fadhilprawira.jmp_surveihakpilihkpu.db.DatabaseContract.VoterColumns.GAMBAR;
import static xyz.fadhilprawira.jmp_surveihakpilihkpu.db.DatabaseContract.VoterColumns.JENIS_KELAMIN;
import static xyz.fadhilprawira.jmp_surveihakpilihkpu.db.DatabaseContract.VoterColumns.NAMA;
import static xyz.fadhilprawira.jmp_surveihakpilihkpu.db.DatabaseContract.VoterColumns.NIK;
import static xyz.fadhilprawira.jmp_surveihakpilihkpu.db.DatabaseContract.VoterColumns.NO_HP;
import static xyz.fadhilprawira.jmp_surveihakpilihkpu.db.DatabaseContract.VoterColumns.TANGGAL_INPUT;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import xyz.fadhilprawira.jmp_surveihakpilihkpu.R;
import xyz.fadhilprawira.jmp_surveihakpilihkpu.adapter.VoterAdapter;
import xyz.fadhilprawira.jmp_surveihakpilihkpu.db.VoterHelper;
import xyz.fadhilprawira.jmp_surveihakpilihkpu.entity.Voter;

public class VotersListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voters_list);


        RecyclerView recyclerView = findViewById(R.id.rv_voters);
        TextView textNoData = findViewById(R.id.text_no_data);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the VoterHelper to query voter data
        VoterHelper voterHelper = VoterHelper.getInstance(this);
        voterHelper.open();

        // Get all voter data from the database
        Cursor cursor = voterHelper.queryAll();
        ArrayList<Voter> voterList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                String nik = cursor.getString(cursor.getColumnIndexOrThrow(NIK));
                String nama = cursor.getString(cursor.getColumnIndexOrThrow(NAMA));
                String alamat = cursor.getString(cursor.getColumnIndexOrThrow(ALAMAT));
                String noHp = cursor.getString(cursor.getColumnIndexOrThrow(NO_HP));
                String jenisKelamin = cursor.getString(cursor.getColumnIndexOrThrow(JENIS_KELAMIN));
                String gambar = cursor.getString(cursor.getColumnIndexOrThrow(GAMBAR));  // Get the image file name
                String tanggalInput = cursor.getString(cursor.getColumnIndexOrThrow(TANGGAL_INPUT));

                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));

                // Create a Voter object and add it to the list
                Voter voter = new Voter(nama, nik, alamat, noHp, jenisKelamin, gambar, id, tanggalInput);
                voterList.add(voter);
            } while (cursor.moveToNext());
        }
        cursor.close();
        voterHelper.close();

        // Set up the adapter with the voter list and attach it to the RecyclerView
        VoterAdapter voterAdapter = new VoterAdapter(voterList);
        recyclerView.setAdapter(voterAdapter);

        // Update UI based on the data list
        if (voterList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            textNoData.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            textNoData.setVisibility(View.GONE);
        }
    }
}