package com.example.buspasswithqrscan.Conductor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.buspasswithqrscan.R;

public class QrResultFragment extends Fragment {

    private static final String ARG_QR_RESULT = "qr_result";

    private String qrResult;

    public QrResultFragment() {
        // Required empty public constructor
    }

    public static QrResultFragment newInstance(String qrResult) {
        QrResultFragment fragment = new QrResultFragment();
        Bundle args = new Bundle();
        args.putString(ARG_QR_RESULT, qrResult);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            qrResult = getArguments().getString(ARG_QR_RESULT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr_result, container, false);
        TextView resultTextView = view.findViewById(R.id.qrResultTextView);
        resultTextView.setText(qrResult);
        return view;
    }
}