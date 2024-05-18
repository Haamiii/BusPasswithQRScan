package com.example.buspasswithqrscan.Student;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.EnumMap;
import java.util.Map;

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

        // Check if getArguments() is not null
        Bundle args = getArguments();
        if (args != null) {
            // Retrieve pass information from arguments
            String passInfo = args.getString(ARG_PASS_INFO);
            if (passInfo != null) {
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
            } else {
                // Handle the case where pass information is null
                Toast.makeText(getContext(), "Pass information is null", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Handle the case where arguments are null
            Toast.makeText(getContext(), "Arguments are null", Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap generateQRCode(String data) {
        Bitmap bitmap = null;
        QRCodeWriter writer = new QRCodeWriter();
        try {
            Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
            hints.put(EncodeHintType.MARGIN, 0);
            BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 400, 400, hints);

            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
        } catch (WriterException e) {
            Log.e("QRCode", "Error generating QR code: " + e.getMessage());
        }
        return bitmap;
    }

    // Method to initiate QR code scanning
    private void initiateScan() {
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
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
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layoutconductor, QrResultFragment.newInstance(qrResult))
                .addToBackStack(null)
                .commit();
    }
}
