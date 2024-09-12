package xyz.fadhilprawira.jmp_surveihakpilihkpu.ui;

import static xyz.fadhilprawira.jmp_surveihakpilihkpu.utils.ImageUtils.getImageUri;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import xyz.fadhilprawira.jmp_surveihakpilihkpu.R;
import xyz.fadhilprawira.jmp_surveihakpilihkpu.databinding.ActivityVoterFormEntryBinding;

public class VoterFormEntryActivity extends AppCompatActivity {

    private ActivityVoterFormEntryBinding binding;
    private String selectedGenderValue;
    private String selectedDate;  // Declare the selectedDate variable to store the date
    private Uri currentImageUri = null;
    private ActivityResultLauncher<Uri> launcherIntentCamera;

    private void showImage() {
        if (currentImageUri != null) {
            // Log the URI for debugging
            Log.d("Image URI", "showImage: " + currentImageUri.toString());

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVoterFormEntryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // on below line we are getting
        // the instance of our calendar.
        final Calendar calendar = Calendar.getInstance();
        selectedDate = calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);

        // Set the current date in DatePicker
        binding.dpTanggal.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Month is 0-based in DatePicker, so we add 1 to get the correct month
                        selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                    }
                });

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

        binding.btnAmbilGambar.setOnClickListener(v -> {
            startCamera();  // Call startCamera() method when button is clicked
        });
        binding.btnSimpan.setOnClickListener(v -> {
            if (binding.rgJenisKelamin.getCheckedRadioButtonId() != 0) {
                int checkedRadioButtonId = binding.rgJenisKelamin.getCheckedRadioButtonId();

                if (checkedRadioButtonId == binding.rbLaki.getId()) {
                    selectedGenderValue = "L";  // "Laki-laki"
                } else if (checkedRadioButtonId == binding.rbPerempuan.getId()) {
                    selectedGenderValue = "P";  // "Perempuan"
                }
            }

            // Pass all data to tvResult
            binding.tvResult.setText(
                    "NIK: " + binding.etNIK.getText().toString() + "\n" +
                            "Nama: " + binding.etNama.getText().toString() + "\n" +
                            "No. HP: " + binding.etNoHP.getText().toString() + "\n" +
                            "Jenis Kelamin: " + selectedGenderValue + "\n" +
                            "Tanggal: " + selectedDate + "\n" +
                            "Alamat: " + binding.etAlamat.getText().toString()
            );
        });
    }
}