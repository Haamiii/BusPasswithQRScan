package com.example.buspasswithqrscan.Conductor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.network.ApiService;
import com.example.buspasswithqrscan.network.RetrofitClient;
import com.example.buspasswithqrscan.network.SharedPreferenceManager;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Qr_ConductoFragment extends Fragment {

    private Button scanqr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr__conducto, container, false);

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
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() != null) {
                String scannedData = intentResult.getContents();
                Log.d("ScannedData", "Scanned Data: " + scannedData); // Logging the scanned data
                fetchScanResults(scannedData);
            } else {
                Toast.makeText(getActivity(), "Scan cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void fetchScanResults(String scannedData) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        int passId = extractPassIdFromScannedData(scannedData);
        int busId = SharedPreferenceManager.getInstance().readInt("BusId", -1); // Use -1 as a default value to detect issues

        Log.d("ExtractedPassId", "Extracted passId: " + passId); // Logging the extracted passId
        Log.d("BusId", "BusId: " + busId); // Logging the busId

        if (passId == 0 || busId == -1) {
            Toast.makeText(getActivity(), "Invalid PassId or BusId", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<ResponseBody> call = apiService.scanQrCode(passId, busId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String jsonResponse = response.body().string();
                        Log.d("ApiResponse", "API Response: " + jsonResponse); // Logging the API response
                        showResultFragment(jsonResponse);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Failed to parse response", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), "API call failed", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private int extractPassIdFromScannedData(String scannedData) {
        try {
            Log.d("ScannedDataBeforeParse", "Scanned Data before parsing: " + scannedData); // Log the scanned data
            int passId;

            // Check if the scanned data is a plain integer
            try {
                passId = Integer.parseInt(scannedData);
            } catch (NumberFormatException e) {
                // If parsing as an integer fails, try parsing as JSON
                JSONObject jsonObject = new JSONObject(scannedData);
                passId = jsonObject.getInt("passId");
            }

            Log.d("ExtractedPassId", "Extracted passId: " + passId); // Logging the extracted passId
            return passId;
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Return a default or error value
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
