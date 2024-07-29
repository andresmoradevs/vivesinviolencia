package org.pastosalud.vivesinviolencia.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.pastosalud.vivesinviolencia.R;

public class EmergencyFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_emergency, container, false);

        setupCallButton(view, R.id.btnPolicia, R.id.tv_policia);
        setupCallButton(view, R.id.btnFiscalia, R.id.tv_fiscalia);
        setupCallButton(view, R.id.btnUri, R.id.tv_uri);
        setupCallButton(view, R.id.btnICBF, R.id.tv_icbf);
        setupCallButton(view, R.id.btnCrue, R.id.tv_crue);
        setupCallButton(view, R.id.btnLineaPurpura, R.id.tv_lineap);
        setupCallButton(view, R.id.btnDnaranja, R.id.tv_duplan);
        setupCallButton(view, R.id.btnDvioleta, R.id.tv_duplav);
        setupCallButton(view, R.id.btnHSRafael, R.id.tv_hsrafael);
        setupCallButton(view, R.id.btnHMNSPS, R.id.tv_hmnsperpetuos);
        setupCallButton(view, R.id.btnHUD, R.id.tv_hudurgencias);

        return view;
    }

    private void setupCallButton(View view, int buttonId, int textViewId) {
        ImageView button = view.findViewById(buttonId);
        final TextView textView = view.findViewById(textViewId);

        if (button != null && textView != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phoneNumber = textView.getText().toString();
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + phoneNumber));
                    startActivity(intent);
                }
            });
        }
    }
}