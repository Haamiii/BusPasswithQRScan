package com.example.buspasswithqrscan.Conductor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.buspasswithqrscan.R;

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

        // Parse the JSON string and set the text of TextViews
        String qrResult = getArguments().getString(ARG_QR_RESULT);
        // Assuming qrResult is in JSON format, you may need to parse it accordingly
        try {
            // Here, I'm assuming qrResult is a Base64 encoded image
            byte[] decodedString = android.util.Base64.decode(qrResult, android.util.Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ImageView imageView = view.findViewById(R.id.imageView);
            imageView.setImageBitmap(decodedByte);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Invalid QR Code", Toast.LENGTH_SHORT).show();
        }
    }
}
