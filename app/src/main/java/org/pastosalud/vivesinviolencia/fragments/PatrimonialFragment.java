package org.pastosalud.vivesinviolencia.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.pastosalud.vivesinviolencia.R;

public class PatrimonialFragment extends Fragment {

    private ImageView iconChat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_patrimonial, container, false);

        TextView textView = view.findViewById(R.id.titlePatr);
        iconChat = view.findViewById(R.id.chat_icon);
        iconChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = "+3216469661";

                // abrir WhatsApp
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber));
                startActivity(sendIntent);
            }
        });

        Typeface customFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/kabut_hitam.ttf");
        textView.setTypeface(customFont);

        return view;
    }
}