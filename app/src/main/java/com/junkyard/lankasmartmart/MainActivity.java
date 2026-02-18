package com.junkyard.lankasmartmart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private View btnNext;
    private View textSkip;
    private Button btnGetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Open the "AppPrefs" notepad
        android.content.SharedPreferences preferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        boolean isFirstTime = preferences.getBoolean("isFirstTime", true);

        // 2. Check if it's the first time
        if (!isFirstTime) {
            // Not the first time! Skip onboarding and go to Login
            android.content.Intent intent = new android.content.Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Destroy MainActivity so they can't go back to onboarding
            return; // Stop the rest of the code from running
        }

        // If it IS the first time, continue setting up the onboarding layout
        setContentView(R.layout.activity_main);

        ArrayList<OnboardingItem> onboardingItems = new ArrayList<>();

        // --- PAGE 1: Start Page (Green) ---
        onboardingItems.add(new OnboardingItem(
                "Lanka\nSmartMart",
                "Groceries delivered to your doorstep in minutes.",
                R.mipmap.logo,
                android.graphics.Color.parseColor("#E8F5E9") // Light Green
        ));

        // --- PAGE 2: Fresh from Farm (Cream) ---
        onboardingItems.add(new OnboardingItem(
                "Fresh from\nthe Farm",
                "We source organic vegetables directly from farmers in Nuwara Eliya.",
                R.mipmap.broccoli,
                android.graphics.Color.parseColor("#FFF5E1")
        ));

        // --- PAGE 3: Categories (Purple) ---
        onboardingItems.add(new OnboardingItem(
                "Shop by\nCategory",
                "Browse Groceries, Household, Personal Care, Stationery, and more.",
                R.mipmap.basket,
                android.graphics.Color.parseColor("#F3E5F5")
        ));

        // --- PAGE 4: Delivery (Blue) ---
        onboardingItems.add(new OnboardingItem(
                "Fast & Safe\nDelivery",
                "Get your daily essentials delivered to your doorstep within minutes.",
                R.mipmap.truck,
                android.graphics.Color.parseColor("#E1F5FE")
        ));

        ViewPager2 viewPager = findViewById(R.id.viewPager);
        OnboardingAdapter adapter = new OnboardingAdapter(onboardingItems);
        viewPager.setAdapter(adapter);

        // Find Buttons
        btnNext = findViewById(R.id.btnRet);
        textSkip = findViewById(R.id.textSkip);
        btnGetStarted = findViewById(R.id.btnGetStarted);

        // --- BUTTON CLICKS ---

        // "Next" Arrow -> Go to next page
        btnNext.setOnClickListener(v -> {
            if (viewPager.getCurrentItem() + 1 < adapter.getItemCount()) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });

        // "Skip" -> Jump to the LAST page (Index 3)
        textSkip.setOnClickListener(v -> {
            viewPager.setCurrentItem(3);
        });

        // "Get Started" OR "Log In" Button
        btnGetStarted.setOnClickListener(v -> {
            if (viewPager.getCurrentItem() == 0) {
                // If on Page 1, "Get Started" just moves us to Page 2
                viewPager.setCurrentItem(1);
            } else {
                // If on Last Page, "Log In" should open the Login Screen
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // --- SWIPE LISTENER (The Brains) ---
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                if (position == 0) {
                    // PAGE 1: Show "Get Started", Hide Arrow
                    btnGetStarted.setText(R.string.get_started);
                    btnGetStarted.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.GONE);
                    textSkip.setVisibility(View.GONE);

                } else if (position == 3) {
                    // LAST PAGE: Show "Log In", Hide Arrow
                    btnGetStarted.setText(R.string.log_in_sign_up);
                    btnGetStarted.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.GONE);
                    textSkip.setVisibility(View.GONE);

                } else {
                    // MIDDLE PAGES: Show Arrow, Hide Big Button
                    btnGetStarted.setVisibility(View.GONE);
                    btnNext.setVisibility(View.VISIBLE);
                    textSkip.setVisibility(View.VISIBLE);
                }
            }
        });

        btnGetStarted.setOnClickListener(v -> {
            if (viewPager.getCurrentItem() == 0) {
                viewPager.setCurrentItem(1);
            } else {
                // --- NEW CODE: Mark onboarding as complete ---
                android.content.SharedPreferences preferences1 = getSharedPreferences("AppPrefs", MODE_PRIVATE);
                android.content.SharedPreferences.Editor editor = preferences1.edit();
                editor.putBoolean("isFirstTime", false);
                editor.apply(); // Save the note!
                // ----------------------------------------------

                android.content.Intent intent = new android.content.Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}