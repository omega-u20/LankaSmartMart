package com.junkyard.lankasmartmart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.txtName.setText(product.getName());
        holder.txtPrice.setText("Rs. " + product.getUnitPrice());
        holder.txtQuantity.setText("In Stock: " + product.getQuantity());

        holder.btnAddCart.setOnClickListener(v -> {
            String userId = FirebaseAuth.getInstance().getUid();
            if (userId != null) {
                // Fixed: Using explicit URL to match CartFragment and AdminActivity
                DatabaseReference cartRef = FirebaseDatabase.getInstance("https://lanka-smartmart-default-rtdb.asia-southeast1.firebasedatabase.app/")
                        .getReference("carts")
                        .child(userId)
                        .child(product.getId());

                CartItem item = new CartItem(product.getId(), product.getName(), product.getUnitPrice(), 1);
                cartRef.setValue(item).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(v.getContext(), "Added to Cart", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(v.getContext(), "Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(v.getContext(), "Please login first", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtPrice, txtQuantity;
        FloatingActionButton btnAddCart;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtProductName);
            txtPrice = itemView.findViewById(R.id.txtProductPrice);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            btnAddCart = itemView.findViewById(R.id.floatingActionButton);
        }
    }
}
