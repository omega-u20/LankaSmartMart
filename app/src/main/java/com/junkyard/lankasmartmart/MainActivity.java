package com.junkyard.lankasmartmart;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList <OnboardingItem> onboardingItems = new ArrayList<>();

        OnboardingItem itemFresh = new OnboardingItem(
                "Fresh from\nthe Farm",
                "We source organic vegetables directly from farmers in Nuwara Eliya.",
                R.drawable.ic_launcher_foreground, // Placeholder image
                android.graphics.Color.parseColor("#FFF5E1") // Cream color
        );
        onboardingItems.add(itemFresh);
        OnboardingItem Category = new OnboardingItem(
                "Shop by Category",
                "We source organic vegetables directly from farmers in Nuwara Eliya.",
                R.drawable.ic_launcher_foreground, // Placeholder image
                android.graphics.Color.parseColor("#F3E5F5") // Cream color
        );
        onboardingItems.add(Category);
        OnboardingItem Delivery = new OnboardingItem(
                "Fast Delivery",
                "We deliver to your door 24 hours a day, 7 days a week.",
                R.drawable.ic_launcher_foreground, // Placeholder image
                android.graphics.Color.parseColor("#E1F5FE") // Cream color
        );
        onboardingItems.add(Delivery);

        androidx.viewpager2.widget.ViewPager2 viewPager = findViewById(R.id.viewPager);
        OnboardingAdapter adapter = new OnboardingAdapter(onboardingItems);
        viewPager.setAdapter(adapter);

        View btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
             }
        });
        View textSkip = findViewById(R.id.textSkip);
        textSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(onboardingItems.size() - 1, true);
            }
        });
    }
}