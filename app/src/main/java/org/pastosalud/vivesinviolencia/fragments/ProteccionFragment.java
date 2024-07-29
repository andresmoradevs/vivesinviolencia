package org.pastosalud.vivesinviolencia.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.pastosalud.vivesinviolencia.R;

public class ProteccionFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_proteccion, container, false);

        setupCallButton(view, R.id.btnCti, R.id.tv_cti);
        setupCallButton(view, R.id.btnSijin, R.id.tv_sijin);
        setupCallButton(view, R.id.btnFiscalia, R.id.tv_fiscalia);
        setupCallButton(view, R.id.btnCaivas, R.id.tv_caivas);
        setupCallButton(view, R.id.btnMedicinaLegal, R.id.tv_mlegal);

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