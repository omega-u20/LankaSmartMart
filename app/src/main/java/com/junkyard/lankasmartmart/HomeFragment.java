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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerProducts;
    private ProductAdapter adapter;
    private List<Product> allProducts = new ArrayList<>();
    private List<Product> filteredProducts = new ArrayList<>();
    private DatabaseReference db;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerProducts = view.findViewById(R.id.recyclerProducts);
        recyclerProducts.setLayoutManager(new GridLayoutManager(getContext(), 2));

        adapter = new ProductAdapter(filteredProducts);
        recyclerProducts.setAdapter(adapter);

        // Fix: Explicitly setting the database URL for the Singapore region to match others
        db = FirebaseDatabase.getInstance("https://lanka-smartmart-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("products");

        fetchProducts();

        Button btnGroceries = view.findViewById(R.id.btnCatGroceries);
        Button btnHousehold = view.findViewById(R.id.btnCatHousehold);
        Button btnPersonalCare = view.findViewById(R.id.btnCatPersonalCare);

        btnGroceries.setOnClickListener(v -> filterProducts("Groceries"));
        btnHousehold.setOnClickListener(v -> filterProducts("Household"));
        btnPersonalCare.setOnClickListener(v -> filterProducts("Personal Care"));
    }

    private void fetchProducts() {
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allProducts.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Product product = data.getValue(Product.class);
                    if (product != null) {
                        allProducts.add(product);
                    }
                }
                // Show all by default or first category
                filterProducts("Groceries");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterProducts(String category) {
        filteredProducts.clear();
        for (Product p : allProducts) {
            if (p.getCategory().equalsIgnoreCase(category)) {
                filteredProducts.add(p);
            }
        }
        if (filteredProducts.isEmpty()) {
            Toast.makeText(getContext(), "No products in " + category, Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
    }
}
