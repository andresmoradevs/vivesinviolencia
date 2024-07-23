package org.pastosalud.vivesinviolencia.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import org.pastosalud.vivesinviolencia.models.Item;
import org.pastosalud.vivesinviolencia.R;
import org.pastosalud.vivesinviolencia.adapters.IconAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private IconAdapter adapter;
    private List<Item> itemList;
    private ImageView iconChat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));


        itemList = new ArrayList<>();
        itemList.add(new Item(R.drawable.psychological_violence_icon));
        itemList.add(new Item(R.drawable.pshysical_violence_icon));
        itemList.add(new Item(R.drawable.sexual_violence_icon));
        itemList.add(new Item(R.drawable.behavior_suicide_icon));
        itemList.add(new Item(R.drawable.negligence_icon));
        itemList.add(new Item(R.drawable.patriomonial_violence_icon));
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

        adapter = new IconAdapter(itemList, new IconAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Fragment fragment = null;
                switch (position) {
                    case 0:
                        fragment = new PhychoFragment();
                        break;
                    case 1:
                        fragment = new PhysicalFragment();
                        break;
                    case 2:
                        fragment = new SexualFragment();
                        break;
                    case 3:
                        fragment = new SuicideFragment();
                        break;
                    case 4:
                        fragment = new NegligenceFragment();
                        break;
                    case 5:
                        fragment = new PatrimonialFragment();
                        break;
                }
                if (fragment != null) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        recyclerView.setAdapter(adapter);

        return view;
    }
}
