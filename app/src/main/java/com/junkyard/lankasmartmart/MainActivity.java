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

        // If you are getting a black screen, let's bypass the check for now
        // to ensure the layout loads correctly.
        /*
        android.content.SharedPreferences preferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        boolean isFirstTime = preferences.getBoolean("isFirstTime", true);

        if (!isFirstTime) {
            android.content.Intent intent = new android.content.Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        */

        setContentView(R.layout.activity_main);

        ArrayList<OnboardingItem> onboardingItems = new ArrayList<>();

        onboardingItems.add(new OnboardingItem(
                "Lanka\nSmartMart",
                "Groceries delivered to your doorstep in minutes.",
                R.mipmap.logo,
                android.graphics.Color.parseColor("#E8F5E9") // Light Green
        ));

        onboardingItems.add(new OnboardingItem(
                "Fresh from\nthe Farm",
                "We source organic vegetables directly from farmers in Nuwara Eliya.",
                R.mipmap.broccoli,
                android.graphics.Color.parseColor("#FFF5E1")
        ));

        onboardingItems.add(new OnboardingItem(
                "Shop by\nCategory",
                "Browse Groceries, Household, Personal Care, Stationery, and more.",
                R.mipmap.basket,
                android.graphics.Color.parseColor("#F3E5F5")
        ));

        onboardingItems.add(new OnboardingItem(
                "Fast & Safe\nDelivery",
                "Get your daily essentials delivered to your doorstep within minutes.",
                R.mipmap.truck,
                android.graphics.Color.parseColor("#E1F5FE")
        ));

        ViewPager2 viewPager = findViewById(R.id.viewPager);
        OnboardingAdapter adapter = new OnboardingAdapter(onboardingItems);
        viewPager.setAdapter(adapter);

        btnNext = findViewById(R.id.btnRet);
        textSkip = findViewById(R.id.textSkip);
        btnGetStarted = findViewById(R.id.btnGetStarted);

        btnNext.setOnClickListener(v -> {
            if (viewPager.getCurrentItem() + 1 < adapter.getItemCount()) {
                viewPager.setPageTransformer(null); // Simple fix for some transition issues
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });

        textSkip.setOnClickListener(v -> {
            viewPager.setCurrentItem(3);
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                if (position == 0) {
                    btnGetStarted.setText(R.string.get_started);
                    btnGetStarted.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.GONE);
                    textSkip.setVisibility(View.GONE);
                } else if (position == 3) {
                    btnGetStarted.setText(R.string.log_in_sign_up);
                    btnGetStarted.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.GONE);
                    textSkip.setVisibility(View.GONE);
                } else {
                    btnGetStarted.setVisibility(View.GONE);
                    btnNext.setVisibility(View.VISIBLE);
                    textSkip.setVisibility(View.VISIBLE);
                }
            }
        });

        btnGetStarted.setOnClickListener(v -> {
            if (viewPager.getCurrentItem() < 3) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            } else {
                // Mark onboarding as complete
                android.content.SharedPreferences preferences1 = getSharedPreferences("AppPrefs", MODE_PRIVATE);
                android.content.SharedPreferences.Editor editor = preferences1.edit();
                editor.putBoolean("isFirstTime", false);
                editor.apply();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
