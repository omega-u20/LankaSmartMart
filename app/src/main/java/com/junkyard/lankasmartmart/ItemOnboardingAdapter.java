package com.junkyard.lankasmartmart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ItemOnboardingAdapter extends RecyclerView.Adapter<ItemOnboardingAdapter.ItemOnboardingViewHolder> {
        private List<ItemOnboarding> itemOnboardings;

    public ItemOnboardingAdapter(List<ItemOnboarding> itemOnboardings) {
        this.itemOnboardings = itemOnboardings;
    }

    @NonNull
    @Override
    public ItemOnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ItemOnboardingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemOnboardingViewHolder holder, int position) {
        ItemOnboarding item = itemOnboardings.get(position);
        holder.name.setText(item.getName());
        holder.price.setText(item.getPrice());
        holder.image.setImageResource(item.getImageRes());
        holder.quantity.setText(String.valueOf(item.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return itemOnboardings.size();
    }

    static class ItemOnboardingViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, quantity;
        ImageView image;

        public ItemOnboardingViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtProductName);
            price = itemView.findViewById(R.id.txtProductPrice);
            quantity = itemView.findViewById(R.id.txtQuantity);
            image = itemView.findViewById(R.id.imgProduct);
        }
    }
}
