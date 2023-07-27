package com.example.asm_giaodien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.service.controls.Control;
import android.view.MenuItem;

import com.example.asm_giaodien.fragment.ChiFragment;
import com.example.asm_giaodien.fragment.GioiThieuFragment;
import com.example.asm_giaodien.fragment.ThongKeFragment;
import com.example.asm_giaodien.fragment.ThuFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment;
                switch (item.getItemId()){
                    case  R.id.mThu:
                        fragment = new ThuFragment();
                        break;
                    case  R.id.mChi:
                        fragment = new ChiFragment();
                        break;
                    case  R.id.mThongKe:
                        fragment = new ThongKeFragment();
                        break;
                    case R.id.mGioiThieu:
                        fragment= new GioiThieuFragment();
                        break;
                    case R.id.mThoat:
                        finish();

                    default:
                        fragment = new ThuFragment();
                        break;
                }

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.linearLayout,fragment)
                        .commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                setTitle(item.getTitle());

                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}