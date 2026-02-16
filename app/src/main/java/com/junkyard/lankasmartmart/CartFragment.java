package com.junkyard.lankasmartmart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CartFragment extends Fragment {

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

        RecyclerView recyclerView = view.findViewById(R.id.cartRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 1. Create Dummy Data
        java.util.ArrayList<CartItem> cartList = new java.util.ArrayList<>();
        cartList.add(new CartItem("Carrots", "Rs. 350", R.drawable.ic_launcher_foreground, 1));
        cartList.add(new CartItem("Detergent", "Rs. 450", R.drawable.ic_launcher_foreground, 1));

        // 2. Set the Adapter
        CartAdapter adapter = new CartAdapter(cartList);
        recyclerView.setAdapter(adapter);

        // 3. Checkout Button
        view.findViewById(R.id.btnCheckout).setOnClickListener(v ->
                Toast.makeText(getContext(), "Processing Order...", Toast.LENGTH_SHORT).show()
        );
    }
}