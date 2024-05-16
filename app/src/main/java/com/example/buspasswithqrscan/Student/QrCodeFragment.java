package com.example.buspasswithqrscan.Student;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.buspasswithqrscan.Conductor.QrResultFragment;
import com.example.buspasswithqrscan.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QrCodeFragment extends Fragment {

    private static final String STUDENT_PASS_PREFIX = "StudentPass:";
    private static final String ARG_PASS_INFO = "pass_info";

    public static QrCodeFragment newInstance(String passInfo) {
        QrCodeFragment fragment = new QrCodeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PASS_INFO, passInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_qr_code, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieve pass information from arguments
        String passInfo = getArguments().getString(ARG_PASS_INFO);

        // Generate QR code based on pass information
        ImageView qrCodeIV = view.findViewById(R.id.ivqrcode);
        Bitmap qrCodeBitmap = generateQRCode(passInfo);
        qrCodeIV.setImageBitmap(qrCodeBitmap);

        // Set click listener for the QR code image view to initiate scanning
        qrCodeIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiateScan();
            }
        });
    }

    private Bitmap generateQRCode(String data) {
        // Your existing code for generating QR code remains unchanged
        return null;
    }

    // Method to initiate QR code scanning
    private void initiateScan() {
        android.app.Fragment QrCodeFragment = null;
        IntentIntegrator integrator = IntentIntegrator.forFragment(QrCodeFragment);
        integrator.setPrompt("Scan QR Code");
        integrator.setOrientationLocked(true);
        integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                // Handle the scanned result here
                String scannedData = result.getContents();
                if (scannedData.startsWith(STUDENT_PASS_PREFIX)) {
                    // Pass corresponds to a student pass
                    // Proceed to QrResultFragment with the scanned data
                    showQrResultFragment(scannedData.substring(STUDENT_PASS_PREFIX.length()));
                } else {
                    // Display message for invalid pass
                    Toast.makeText(getActivity(), "Invalid pass", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Display message if the scan was canceled
                Toast.makeText(getActivity(), "Scan Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Method to show QrResultFragment with the scanned data
    private void showQrResultFragment(String qrResult) {
        // Replace "YourActivityName" with the name of your activity
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layoutconductor, QrResultFragment.newInstance(qrResult))
                .addToBackStack(null)
                .commit();
    }
}
