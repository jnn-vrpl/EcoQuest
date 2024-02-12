package com.app.ecoquest.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.app.ecoquest.fragments.HomeFragment;
import com.app.ecoquest.R;
import com.app.ecoquest.database.SQLiteHelper;
import com.app.ecoquest.fragments.TodoFragment;
import com.app.ecoquest.models.Stats;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;

    SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.bottomNavigation);

        sqLiteHelper = new SQLiteHelper(MainActivity.this);

        if (sqLiteHelper.readStats(1) == null) {
            Stats stats = new Stats(1, 1, 0);
            sqLiteHelper.createStats(stats);
        }

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int itemId = item.getItemId();
                if (itemId == R.id.iGame) {
                    Intent intent = new Intent(MainActivity.this, GameActivity.class);
                    startActivity(intent);
                } else if (itemId == R.id.iHome) {
                    loadFragment(new HomeFragment());
                } else if (itemId == R.id.iTodo) {
                    loadFragment(new TodoFragment());
                }
                return true;
            }
        });

        loadFragment(new HomeFragment());
        bottomNavigation.setSelectedItemId(R.id.iHome);
    }

    private void loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}