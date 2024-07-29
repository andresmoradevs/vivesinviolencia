package org.pastosalud.vivesinviolencia.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.pastosalud.vivesinviolencia.fragments.JusticiaFragment;
import org.pastosalud.vivesinviolencia.fragments.ProteccionFragment;
import org.pastosalud.vivesinviolencia.fragments.SaludFragment;

// DirectoryPagerAdapter.java
// DirectoryPagerAdapter.java
public class DirectoryPagerAdapter extends FragmentStateAdapter {

    public DirectoryPagerAdapter(Fragment fragment) {
        super(fragment);
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new JusticiaFragment();
            case 1:
                return new ProteccionFragment();
            case 2:
                return new SaludFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
