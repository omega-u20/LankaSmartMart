package com.junkyard.lankasmartmart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItems;
    private String userId;
    private DatabaseReference cartRef;

    public CartAdapter(List<CartItem> cartItems) {
        this.cartItems = cartItems;
        this.userId = FirebaseAuth.getInstance().getUid();
        this.cartRef = FirebaseDatabase.getInstance("https://lanka-smartmart-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("carts")
                .child(userId);
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        holder.name.setText(item.getName());
        holder.price.setText("Rs. " + item.getPrice());
        holder.quantity.setText(String.valueOf(item.getQuantity()));
        holder.image.setImageResource(R.drawable.ic_launcher_foreground);

        holder.btnPlus.setOnClickListener(v -> {
            int newQty = item.getQuantity() + 1;
            cartRef.child(item.getId()).child("quantity").setValue(newQty);
        });

        holder.btnMinus.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                int newQty = item.getQuantity() - 1;
                cartRef.child(item.getId()).child("quantity").setValue(newQty);
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            cartRef.child(item.getId()).removeValue();
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, quantity;
        ImageView image, btnDelete;
        TextView btnPlus, btnMinus;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtProductName);
            price = itemView.findViewById(R.id.txtProductPrice);
            quantity = itemView.findViewById(R.id.txtQuantity);
            image = itemView.findViewById(R.id.imgProduct);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnMinus = itemView.findViewById(R.id.btnMinus);
        }
    }
}
