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

public class SaludFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_salud, container, false);

        setupCallButton(view, R.id.btnPastoESEPPal, R.id.tv_pastoeseppal);
        setupCallButton(view, R.id.btnHLCivil, R.id.tv_hlcivil);
        setupCallButton(view, R.id.btnHLRosa, R.id.tv_hlrosa);
        setupCallButton(view, R.id.btnCSLorenzo, R.id.tv_cslorenzo);
        setupCallButton(view, R.id.btnCSTamasagra, R.id.tv_cstamasagra);
        setupCallButton(view, R.id.btnCallCenter, R.id.tv_callcenter);
        setupCallButton(view, R.id.btnIpsMFamSAurora, R.id.tv_medfamsaurora);
        setupCallButton(view, R.id.btnIpsMFamSFatima, R.id.tv_medfamsfatima);
        setupCallButton(view, R.id.btnIPSPEspec, R.id.tv_ipspespc);
        setupCallButton(view, R.id.btnIpsSPolicia, R.id.tv_ipsspolicia);
        setupCallButton(view, R.id.btnIpsCNSDFatima, R.id.tv_ipscnsdfatima);
        setupCallButton(view, R.id.btnClinicaHisp, R.id.tv_clchispano);
        setupCallButton(view, R.id.btnClinicaCardioPabon, R.id.tv_clccardiopabon);
        setupCallButton(view, R.id.btnHILAngeles, R.id.tv_hilangeles);
        setupCallButton(view, R.id.btnHUDepart, R.id.tv_hudepart);
        setupCallButton(view, R.id.btnFHSPedro, R.id.tv_fhspedro);
        setupCallButton(view, R.id.btnCMVDAtriz, R.id.tv_cmvdatriz);

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