package com.example.buspasswithqrscan.Student;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.network.SharedPreferenceManager;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.EnumMap;
import java.util.Map;

public class QrCodeFragment extends Fragment {

    private TextView name;
    private TextView passid;
    private static final String ARG_PASS_ID = "PassId";

    public static QrCodeFragment newInstance(String PassId) {
        QrCodeFragment fragment = new QrCodeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PASS_ID, PassId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr_code, container, false);

        name = view.findViewById(R.id.name);
        passid = view.findViewById(R.id.passid);
        try {
            JSONObject object = new JSONObject(SharedPreferenceManager.getInstance().read("user", null));
            name.setText(object.getString("Name"));
            passid.setText(object.getInt("PassId") + "");
        } catch (JSONException e) {
            Log.d("ero==>>", e.getMessage());
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            String PassId = args.getString(ARG_PASS_ID);
            if (PassId != null) {
                ImageView qrCodeImageView = view.findViewById(R.id.qrCodeImageView);
                Bitmap qrCodeBitmap = generateQRCode(PassId);
                qrCodeImageView.setImageBitmap(qrCodeBitmap);
                Log.d("PassId", "PassId: " + PassId);
                qrCodeImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Show PassId when clicked
                        showPassId(PassId);
                    }

                    private void showPassId(String passId) {
                        Toast.makeText(getContext(), "PassId: " + passId, Toast.LENGTH_SHORT).show();
                        Log.d("Clicked PassId", "PassId: " + passId);
                    }
                });
            }
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
            e.printStackTrace();
        }
        return bitmap;
    }

}
