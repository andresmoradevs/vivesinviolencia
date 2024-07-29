package org.pastosalud.vivesinviolencia.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import org.pastosalud.vivesinviolencia.R;

public class PhysicalFragment extends Fragment {

    private ImageView iconChat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_physical, container, false);
        LottieAnimationView animationView = view.findViewById(R.id.lottie_animation);
        // Puedes controlar la animación aquí
        animationView.setAnimation("animation_justicia3.json");
        animationView.playAnimation();

        LottieAnimationView animationView2 = view.findViewById(R.id.lottie_animation2);
        // Puedes controlar la animación aquí
        animationView2.setAnimation("animation_salud.json");
        animationView2.playAnimation();

        LottieAnimationView animationView3 = view.findViewById(R.id.lottie_animation3);
        // Puedes controlar la animación aquí
        animationView3.setAnimation("animation_proteccion.json");
        animationView3.playAnimation();

        animationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment directoryFragment = new DirectoryFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, directoryFragment);
                transaction.addToBackStack("Justicia");
                transaction.commit();
            }
        });
        animationView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment directoryFragment = new DirectoryFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, directoryFragment);
                transaction.addToBackStack("Salud");
                transaction.commit();
            }
        });
        animationView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment directoryFragment = new DirectoryFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, directoryFragment);
                transaction.addToBackStack("Proteccion");
                transaction.commit();
            }
        });

        TextView textView = view.findViewById(R.id.title);
        TextView textView2 = view.findViewById(R.id.second_title);
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

        Typeface customFont2 = Typeface.createFromAsset(getContext().getAssets(), "fonts/kabut_hitam.ttf");
        textView2.setTypeface(customFont2);
        return view;
    }
}