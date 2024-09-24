package xyz.fadhilprawira.jmp_surveihakpilihkpu.ui;

import static xyz.fadhilprawira.jmp_surveihakpilihkpu.db.DatabaseContract.VoterColumns.ALAMAT;
import static xyz.fadhilprawira.jmp_surveihakpilihkpu.db.DatabaseContract.VoterColumns.GAMBAR;
import static xyz.fadhilprawira.jmp_surveihakpilihkpu.db.DatabaseContract.VoterColumns.JENIS_KELAMIN;
import static xyz.fadhilprawira.jmp_surveihakpilihkpu.db.DatabaseContract.VoterColumns.NAMA;
import static xyz.fadhilprawira.jmp_surveihakpilihkpu.db.DatabaseContract.VoterColumns.NIK;
import static xyz.fadhilprawira.jmp_surveihakpilihkpu.db.DatabaseContract.VoterColumns.NO_HP;
import static xyz.fadhilprawira.jmp_surveihakpilihkpu.db.DatabaseContract.VoterColumns.TANGGAL_INPUT;
import static xyz.fadhilprawira.jmp_surveihakpilihkpu.utils.ImageUtils.getImageUri;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import xyz.fadhilprawira.jmp_surveihakpilihkpu.databinding.ActivityVoterFormEntryBinding;
import xyz.fadhilprawira.jmp_surveihakpilihkpu.db.VoterHelper;
import xyz.fadhilprawira.jmp_surveihakpilihkpu.entity.Voter;

public class VoterFormEntryActivity extends AppCompatActivity {
    private static final int STORAGE_PERMISSION_CODE = 1;

    private ActivityVoterFormEntryBinding binding;
    private String selectedGenderValue;
    private Uri currentImageUri = null;
    private FusedLocationProviderClient fusedLocationClient;
    private Geocoder geocoder;


    private ActivityResultLauncher<Uri> launcherIntentCamera;
    private Voter voter;
    private VoterHelper voterHelper;

    private String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();

        return dateFormat.format(date);
    }

    private void showImage() {
        if (currentImageUri != null) {
            // Log the URI for debugging
            Log.d("Image URI", "showImage: " + currentImageUri);

            // Ensure the ImageView is correctly set up
            try {
                binding.ivPreview.setImageURI(currentImageUri);
            } catch (Exception e) {
                Log.e("Image Load Error", "Error setting image URI", e);
            }
        } else {
            Log.w("Image URI", "No image URI found");
        }
    }

    // Method to start camera and capture image
    private void startCamera() {
        currentImageUri = getImageUri(this);  // Get the image URI using your getImageUri() method
        if (currentImageUri != null) {
            launcherIntentCamera.launch(currentImageUri);  // Launch the camera
        }
    }

    private void checkStoragePermissions() {
        // Check if the permission is not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

            // Request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_CODE);
        }
    }

    private void getLocationAndInsert() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        String address = getAddressFromLocation(latitude, longitude);

                        // Set address to the EditText field or use it as needed
                        binding.etAlamat.setText(address);
                    } else {
                        Toast.makeText(VoterFormEntryActivity.this, "Unable to get location", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    private String getAddressFromLocation(double latitude, double longitude) {
        String address = "";
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address addr = addresses.get(0);
                address = addr.getAddressLine(0); // Get the full address
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }
    // Handle the result of the permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                // Permission denied
                Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVoterFormEntryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        geocoder = new Geocoder(this, Locale.getDefault());
        // Check for storage permissions
        checkStoragePermissions();
        voterHelper = VoterHelper.getInstance(getApplicationContext());
        voterHelper.open();


        // Initialize launcher for TakePicture contract
        launcherIntentCamera = registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                isSuccess -> {
                    if (isSuccess) {
                        showImage();  // Handle the image display if success
                    } else {
                        currentImageUri = null;  // Reset URI if failed
                    }
                }
        );
        binding.btnCekLokasi.setOnClickListener(v -> getLocationAndInsert());

        binding.btnAmbilGambar.setOnClickListener(v -> {
            startCamera();  // Call startCamera() method when button is clicked
        });
        binding.btnSimpan.setOnClickListener(v -> {
            // Initialize the Voter object if it's null
            if (voter == null) {
                voter = new Voter("", "", "", "", "", "", 0, "");  // Initialize with default values
            }
            int checkedRadioButtonId = binding.rgJenisKelamin.getCheckedRadioButtonId();

//            if(checkedRadioButtonId == -1) {
//                binding.rgJenisKelamin.requestFocus();
//                return;
//            } else
            if (checkedRadioButtonId != 0) {
                // Get the selected radio button value

                if (checkedRadioButtonId == binding.rbLaki.getId()) {
                    selectedGenderValue = "L";  // "Laki-laki"
                } else if (checkedRadioButtonId == binding.rbPerempuan.getId()) {
                    selectedGenderValue = "P";  // "Perempuan"
                }
            }

            String nik = Objects.requireNonNull(binding.etNIK.getText()).toString().trim();
            if (TextUtils.isEmpty(nik)) {
                binding.etNIK.setError("Field can not be blank");
                return;
            }

            String nama = Objects.requireNonNull(binding.etNama.getText()).toString().trim();
            if (TextUtils.isEmpty(nama)) {
                binding.etNama.setError("Field can not be blank");
                return;
            }

            String noHP = Objects.requireNonNull(binding.etNoHP.getText()).toString().trim();
            if (TextUtils.isEmpty(noHP)) {
                binding.etNoHP.setError("Field can not be blank");
                return;
            }

            String alamat = Objects.requireNonNull(binding.etAlamat.getText()).toString().trim();
            if (TextUtils.isEmpty(alamat)) {
                binding.etAlamat.setError("Field can not be blank");
                return;
            }

            voter.setNik(nik);
            voter.setNama(nama);
            voter.setNoHp(noHP);
            voter.setJenisKelamin(selectedGenderValue);
            voter.setAlamat(alamat);
            voter.setGambar(currentImageUri.toString());

            ContentValues values = new ContentValues();
            values.put(NIK, nik);
            values.put(NAMA, nama);
            values.put(NO_HP, noHP);
            values.put(JENIS_KELAMIN, selectedGenderValue);
            values.put(ALAMAT, alamat);
            values.put(TANGGAL_INPUT, getCurrentDate());  // Add current date here
            // Add the image file name if available
            if (currentImageUri != null) {
                values.put(GAMBAR, currentImageUri.toString());  // Save image file name in the database
            }

            long result = voterHelper.insert(values);
            if (result > 0) {
                Toast.makeText(VoterFormEntryActivity.this, "Data Successfully Inserted", Toast.LENGTH_SHORT).show();
                finish();  // Close the activity
            } else {
                Toast.makeText(VoterFormEntryActivity.this, "Data Insertion Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}