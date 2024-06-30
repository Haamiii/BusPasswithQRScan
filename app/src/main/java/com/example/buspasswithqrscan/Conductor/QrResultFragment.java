package com.example.buspasswithqrscan.Conductor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.buspasswithqrscan.R;

import org.json.JSONObject;

public class QrResultFragment extends Fragment {

    private static final String ARG_QR_RESULT = "qr_result";

    public static QrResultFragment newInstance(String qrResult) {
        QrResultFragment fragment = new QrResultFragment();
        Bundle args = new Bundle();
        args.putString(ARG_QR_RESULT, qrResult);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_qr_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String qrResult = getArguments().getString(ARG_QR_RESULT);
        displayResult(view, qrResult);
    }

    private void displayResult(View view, String qrResult) {
        try {
            JSONObject jsonObject = new JSONObject(qrResult);
            String studentName = jsonObject.getString("Name");
            String regNo = jsonObject.getString("RegNo");
            String remainingJourneys = jsonObject.getString("RemainingJourneys");
            String gender = jsonObject.getString("Gender");
            String passId = jsonObject.getString("PassId");
            String passExpiry = jsonObject.getString("PassExpiry");
            String passStatus = jsonObject.getString("PassStatus");

            ((TextView) view.findViewById(R.id.studentNameValueTextView)).setText(studentName);
            ((TextView) view.findViewById(R.id.regNoValueTextView)).setText(regNo);
            ((TextView) view.findViewById(R.id.remainingJourneysValueTextView)).setText(remainingJourneys);
            ((TextView) view.findViewById(R.id.genderValueTextView)).setText(gender);
            ((TextView) view.findViewById(R.id.passIdValueTextView)).setText(passId);
            ((TextView) view.findViewById(R.id.passExpiryDateValueTextView)).setText(passExpiry);
            ((TextView) view.findViewById(R.id.tvvalid)).setText(passStatus);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Invalid QR Code", Toast.LENGTH_SHORT).show();
        }
    }
}
