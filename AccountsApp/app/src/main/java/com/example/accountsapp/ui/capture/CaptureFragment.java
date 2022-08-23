package com.example.accountsapp.ui.capture;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.accountsapp.databinding.FragmentCaptureBinding;


public class CaptureFragment extends Fragment {

    private static final int PERMISSION_REQUEST_CODE = 1001;
    private static final int REQUEST_IMAGE_CAPTURE = 1002;
    private FragmentCaptureBinding binding;
    Bitmap image;
    Button downloadPdf;
    CaptureViewModel captureViewModel;
    ImageView clickImage;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        captureViewModel =
                new ViewModelProvider(this).get(CaptureViewModel.class);

        binding = FragmentCaptureBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ActivityCompat.requestPermissions(this.requireActivity(), new String[]{WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        clickImage = binding.clickImage;
        final Button takePhoto = binding.textHome;
        downloadPdf = binding.downloadPdf;

        takePhoto.setOnClickListener(v -> dispatchTakePictureIntent());

        downloadPdf.setOnClickListener(v -> {
            if (checkPermission()) {
                if (image != null) {
                    captureViewModel.savePdf(image);
                }
            } else {
                requestPermission();
            }
        });
        captureViewModel.getFilePath().observe(getViewLifecycleOwner(), s -> {
            Toast.makeText(this.requireActivity(), "File Saved at " + s, Toast.LENGTH_SHORT).show();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {

            image = (Bitmap) data.getExtras()
                    .get("data");
            clickImage.setVisibility(View.VISIBLE);
            clickImage.setImageBitmap(image);
            downloadPdf.setVisibility(View.VISIBLE);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ComponentName captureActivity = takePictureIntent.resolveActivity(this.requireActivity().getPackageManager());
        if (captureActivity != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private boolean checkPermission() {
        int writePermission = ActivityCompat.checkSelfPermission(this.requireActivity().getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        return writePermission == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this.requireActivity(), new String[]{WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (writeStorage) {
                    Toast.makeText(this.requireActivity(), "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this.requireActivity(), "Permission Denied.", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this.requireActivity(), "Permission Denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}