package com.junkyard.lankasmartmart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder> {

    private List<Product> productList;

    public InventoryAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public InventoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inventory, parent, false);
        return new InventoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.invName.setText(product.getName());
        holder.invId.setText("ID: " + product.getId());
        holder.invCategory.setText("Category: " + product.getCategory());
        holder.invPrice.setText("Rs. " + product.getUnitPrice());
        holder.invStock.setText("Stock: " + product.getQuantity());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class InventoryViewHolder extends RecyclerView.ViewHolder {
        TextView invName, invId, invCategory, invPrice, invStock;

        public InventoryViewHolder(@NonNull View itemView) {
            super(itemView);
            invName = itemView.findViewById(R.id.invName);
            invId = itemView.findViewById(R.id.invId);
            invCategory = itemView.findViewById(R.id.invCategory);
            invPrice = itemView.findViewById(R.id.invPrice);
            invStock = itemView.findViewById(R.id.invStock);
        }
    }
}
