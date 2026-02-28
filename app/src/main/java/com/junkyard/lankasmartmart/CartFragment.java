package com.junkyard.lankasmartmart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CartFragment extends Fragment {

    private RecyclerView cartRecyclerView;
    private CartAdapter adapter;
    private List<CartItem> cartList = new ArrayList<>();
    private DatabaseReference cartRef;
    private TextView txtTotalAmount;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cartRecyclerView = view.findViewById(R.id.cartRecyclerView);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        txtTotalAmount = view.findViewById(R.id.txtTotalAmount);

        adapter = new CartAdapter(cartList);
        cartRecyclerView.setAdapter(adapter);

        String userId = FirebaseAuth.getInstance().getUid();
        if (userId != null) {
            cartRef = FirebaseDatabase.getInstance("https://lanka-smartmart-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference("carts")
                    .child(userId);

            fetchCartItems();
        }

        view.findViewById(R.id.btnClearAll).setOnClickListener(v -> {
            if (cartRef != null) cartRef.removeValue();
        });

        view.findViewById(R.id.btnCheckout).setOnClickListener(v -> {
            if (cartList.isEmpty()) {
                Toast.makeText(getContext(), "Your cart is empty", Toast.LENGTH_SHORT).show();
            } else {
                performCheckout();
            }
        });
    }

    private void fetchCartItems() {
        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartList.clear();
                double total = 0;
                for (DataSnapshot data : snapshot.getChildren()) {
                    CartItem item = data.getValue(CartItem.class);
                    if (item != null) {
                        cartList.add(item);
                        total += (item.getPrice() * item.getQuantity());
                    }
                }
                adapter.notifyDataSetChanged();
                txtTotalAmount.setText("Rs. " + total);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void performCheckout() {
        String userId = FirebaseAuth.getInstance().getUid();
        if (userId == null) return;

        DatabaseReference db = FirebaseDatabase.getInstance("https://lanka-smartmart-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        String orderId = db.child("orders").child(userId).push().getKey();

        if (orderId == null) return;

        // Calculate total again to be safe
        double total = 0;
        for (CartItem item : cartList) {
            total += (item.getPrice() * item.getQuantity());
        }

        // Prepare order data
        Map<String, Object> orderData = new HashMap<>();
        orderData.put("orderId", orderId);
        orderData.put("items", cartList);
        orderData.put("totalAmount", total);
        orderData.put("status", "Pending");
        orderData.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));

        // 1. Save to orders node
        db.child("orders").child(userId).child(orderId).setValue(orderData).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // 2. Clear the cart
                cartRef.removeValue();
                
                // 3. Optional: Logic to decrease stock could go here (for advanced integration)
                
                Toast.makeText(getContext(), "Order Placed Successfully!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Checkout Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
