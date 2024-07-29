package org.pastosalud.vivesinviolencia.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.pastosalud.vivesinviolencia.R;
import org.pastosalud.vivesinviolencia.adapters.DirectoryPagerAdapter;

// DirectoryFragment.java
public class DirectoryFragment extends Fragment {
    private static final String ARG_INITIAL_TAB = "initial_tab";

    public static DirectoryFragment newInstance(int initialTab) {
        DirectoryFragment fragment = new DirectoryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INITIAL_TAB, initialTab);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_directory, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager2 viewPager = view.findViewById(R.id.viewPager);

        DirectoryPagerAdapter adapter = new DirectoryPagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Justicia");
                    break;
                case 1:
                    tab.setText("Protección");
                    break;
                case 2:
                    tab.setText("Salud");
                    break;
            }
        }).attach();

        if (getArguments() != null) {
            int initialTab = getArguments().getInt(ARG_INITIAL_TAB, 0);
            viewPager.setCurrentItem(initialTab);
        }

        return view;
    }
}
