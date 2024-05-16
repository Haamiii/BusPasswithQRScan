package com.example.buspasswithqrscan.Conductor;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.buspasswithqrscan.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class Qr_ConductoFragment extends Fragment {

    private Button scanqr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr__conducto, container, false);

        // Initialize the button correctly
        scanqr = view.findViewById(R.id.scanqr);
        scanqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = IntentIntegrator.forSupportFragment(Qr_ConductoFragment.this);
                intentIntegrator.setPrompt("Scan any QR Code");
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.initiateScan();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);  // Add this line to call the superclass implementation
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() != null) {
                // Handle the scanned result here
                String scannedData = intentResult.getContents();
                showResultFragment(scannedData);
            } else {
                Toast.makeText(getActivity(), "Scan cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showResultFragment(String qrResult) {
        Fragment qrResultFragment = QrResultFragment.newInstance(qrResult);
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layoutconductor, qrResultFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
