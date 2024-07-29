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

public class JusticiaFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_justicia, container, false);

        setupCallButton(view, R.id.btnCTFamilia, R.id.tv_ctfamilia);
        setupCallButton(view, R.id.btnCZ1, R.id.tv_cz1);
        setupCallButton(view, R.id.btnCZ2, R.id.tv_cz2);
        setupCallButton(view, R.id.btnCaimorasurco, R.id.tv_caimorasurco);
        setupCallButton(view, R.id.btnCaiDorado, R.id.tv_caidorado);
        setupCallButton(view, R.id.btnNJdePraga, R.id.tv_njesusdepraga);
        setupCallButton(view, R.id.btnCaiAnganoy, R.id.tv_caiAnganoy);
        setupCallButton(view, R.id.btnGenoy, R.id.tv_genoy);
        setupCallButton(view, R.id.btnCDJesus, R.id.tv_cdjesus);
        setupCallButton(view, R.id.btnCaiBombona, R.id.tv_caibombona);
        setupCallButton(view, R.id.btnCaiSAgustin, R.id.tv_caisanagustin);
        setupCallButton(view, R.id.btnCaidCarnval, R.id.tv_caipdcarnaval);
        setupCallButton(view, R.id.btnCaiObrero, R.id.tv_caiobrero);
        setupCallButton(view, R.id.btnCaiTama, R.id.tv_caitama);
        setupCallButton(view, R.id.btnCaiGranada, R.id.tv_caigranada);
        setupCallButton(view, R.id.btnCatambumco, R.id.tv_catambuco);
        setupCallButton(view, R.id.btnPopular, R.id.tv_popular);
        setupCallButton(view, R.id.btnSMonica, R.id.tv_smonica);
        setupCallButton(view, R.id.btnPotrerillo, R.id.tv_potrerillo);
        setupCallButton(view, R.id.btnChambu, R.id.tv_chambu);
        setupCallButton(view, R.id.btnMiraflores, R.id.tv_miraflores);
        setupCallButton(view, R.id.btnCPasto, R.id.tv_cpasto);
        setupCallButton(view, R.id.btnSBolivar, R.id.tv_sbolivar);
        setupCallButton(view, R.id.btnEncano, R.id.tv_encano);
        setupCallButton(view, R.id.btnJamondino, R.id.tv_jamondino);

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