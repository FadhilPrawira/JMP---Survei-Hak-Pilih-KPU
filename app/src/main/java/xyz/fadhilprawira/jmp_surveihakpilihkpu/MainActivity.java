package xyz.fadhilprawira.jmp_surveihakpilihkpu;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import xyz.fadhilprawira.jmp_surveihakpilihkpu.databinding.ActivityMainBinding;
import xyz.fadhilprawira.jmp_surveihakpilihkpu.ui.AboutActivity;
import xyz.fadhilprawira.jmp_surveihakpilihkpu.ui.VoterFormEntryActivity;
import xyz.fadhilprawira.jmp_surveihakpilihkpu.ui.VotersListActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnAddVoter.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, VoterFormEntryActivity.class);
            startActivity(i);
        });

        binding.btnShowVoters.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, VotersListActivity.class);
            startActivity(i);
        });

        binding.btnAbout.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(i);
        });

        binding.btnExit.setOnClickListener(v -> finish());
    }
}