package com.junkyard.lankasmartmart;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.graphics.drawable.GradientDrawable;

// 1. Extend the RecyclerView.Adapter class
public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder> {

    private List<OnboardingItem> onboardingItems;

    // 2. Constructor: Takes the list of data passed from MainActivity
    public OnboardingAdapter(List<OnboardingItem> onboardingItems) {
        this.onboardingItems = onboardingItems;
    }

    @NonNull
    @Override
    public OnboardingViewHolder onCreateViewHolder(@NonNull android.view.ViewGroup parent, int viewType) {
        return new OnboardingViewHolder(
                android.view.LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_onboarding_page, parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {
        holder.setOnboardingData(onboardingItems.get(position));
    }

    @Override
    public int getItemCount() {
        return onboardingItems.size();
    }
    // 3. Inner ViewHolder class: Maps the XML views to Java variables
    class OnboardingViewHolder extends RecyclerView.ViewHolder {
        private TextView textTitle;
        private TextView textDescription;
        private ImageView imageOnboarding;
        private View viewBackground; // For the colored shape

        public OnboardingViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDescription = itemView.findViewById(R.id.textDescription);
            imageOnboarding = itemView.findViewById(R.id.imageOnboarding);
            viewBackground = itemView.findViewById(R.id.viewBackground);
        }

        // We will add a helper method here later to set the data
        void setOnboardingData(OnboardingItem item) {
            textTitle.setText(item.getTitle());
            textDescription.setText(item.getDescription());
            imageOnboarding.setImageResource(item.getImageResId());

            // NEW: Handle the background color while keeping rounded corners
            if (viewBackground.getBackground() instanceof GradientDrawable) {
                ((GradientDrawable) viewBackground.getBackground()).setColor(item.getBackgroundColor());
            }
    }
}
}