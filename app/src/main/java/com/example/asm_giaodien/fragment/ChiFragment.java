package com.example.asm_giaodien.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.asm_giaodien.R;
import com.example.asm_giaodien.adapter.ChiAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ChiFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chi_fragment,container,false);
        TabLayout tabLayoutThu = view.findViewById(R.id.tabLayoutChi);
        ViewPager2 viewPager2Thu = view.findViewById(R.id.viewPager2Chi);


        ChiAdapter adapter = new ChiAdapter(getActivity());
        viewPager2Thu.setAdapter(adapter);
        new TabLayoutMediator(tabLayoutThu, viewPager2Thu, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position==0)
                    tab.setText("Loại chi");
                else
                    tab.setText("Khoản chi");
            }
        }).attach();

        return view;

    }
}
