package com.junkyard.lankasmartmart;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class ActivityHome extends AppCompatActivity {

    private LinearLayout navHome, navCat, navCart, navProfile;
    private ImageView imgHome, imgCat, imgCart, imgProfile;
    private TextView txtHome, txtCat, txtCart, txtProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Find the custom bar views
        navHome = findViewById(R.id.navHome);
        navCat = findViewById(R.id.navCat);
        navCart = findViewById(R.id.navCart);
        navProfile = findViewById(R.id.navProfile);

        // Find icons and text for color switching
        imgHome = findViewById(R.id.imgHome);
        txtHome = findViewById(R.id.txtHome);
        imgCat = findViewById(R.id.imgCat);
        txtCat = findViewById(R.id.txtCat);
        imgCart = findViewById(R.id.imgCart);
        txtCart = findViewById(R.id.txtCart);
        imgProfile = findViewById(R.id.imgProfile);
        txtProfile = findViewById(R.id.txtProfile);

        // Default Fragment
        loadFragment(new HomeFragment(), 0);

        navHome.setOnClickListener(v -> loadFragment(new HomeFragment(), 0));
        navCat.setOnClickListener(v -> loadFragment(new CategoryFragment(), 1));
        navCart.setOnClickListener(v -> loadFragment(new CartFragment(), 2));
        navProfile.setOnClickListener(v -> loadFragment(new ProfileFragment(), 3));
    }

    private void loadFragment(Fragment fragment, int index) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
        updateNavUI(index);
    }

    private void updateNavUI(int selectedIndex) {
        // Reset all to light gray/alpha
        imgHome.setAlpha(0.3f); txtHome.setTextColor(Color.GRAY);
        imgCat.setAlpha(0.3f); txtCat.setTextColor(Color.GRAY);
        imgCart.setAlpha(0.3f); txtCart.setTextColor(Color.GRAY);
        imgProfile.setAlpha(0.3f); txtProfile.setTextColor(Color.GRAY);

        // Set selected to bold/black
        switch (selectedIndex) {
            case 0: imgHome.setAlpha(1.0f); txtHome.setTextColor(Color.BLACK); break;
            case 1: imgCat.setAlpha(1.0f); txtCat.setTextColor(Color.BLACK); break;
            case 2: imgCart.setAlpha(1.0f); txtCart.setTextColor(Color.BLACK); break;
            case 3: imgProfile.setAlpha(1.0f); txtProfile.setTextColor(Color.BLACK); break;
        }
    }
}
