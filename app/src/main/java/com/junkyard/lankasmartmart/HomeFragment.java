package com.junkyard.lankasmartmart;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
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
    private String currentCategory = "All";

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

        db = FirebaseDatabase.getInstance("https://lanka-smartmart-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("products");

        fetchProducts();

        // Setup Category Buttons
        setupCategoryButtons(view);

        // Setup Search
        EditText etSearch = view.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchProducts(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Location click
        LinearLayout layoutLocation = view.findViewById(R.id.layoutLocation);
        layoutLocation.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MapsActivity.class);
            startActivity(intent);
        });

        // Notification click
        View btnNotification = view.findViewById(R.id.btnNotification);
        btnNotification.setOnClickListener(this::showNotificationMenu);
    }

    private void setupCategoryButtons(View view) {
        Button btnAll = view.findViewById(R.id.btnCatAll);
        Button btnGroceries = view.findViewById(R.id.btnCatGroceries);
        Button btnFruits = view.findViewById(R.id.btnCatFruits);
        Button btnVegetables = view.findViewById(R.id.btnCatVegetables);
        Button btnHousehold = view.findViewById(R.id.btnCatHousehold);
        Button btnPersonalCare = view.findViewById(R.id.btnCatPersonalCare);

        btnAll.setOnClickListener(v -> filterProducts("All"));
        btnGroceries.setOnClickListener(v -> filterProducts("Groceries"));
        btnFruits.setOnClickListener(v -> filterProducts("Fruits"));
        btnVegetables.setOnClickListener(v -> filterProducts("Vegetables"));
        btnHousehold.setOnClickListener(v -> filterProducts("Household"));
        btnPersonalCare.setOnClickListener(v -> filterProducts("Personal Care"));
    }

    private void showNotificationMenu(View v) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        popup.getMenu().add("30% New Year Sale is Live!");
        popup.getMenu().add("Your order #1234 is on the way");
        popup.getMenu().add("Clear all notifications");
        popup.setOnMenuItemClickListener(item -> {
            Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        });
        popup.show();
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
                filterProducts(currentCategory);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (isAdded()) {
                    Toast.makeText(getContext(), "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void filterProducts(String category) {
        currentCategory = category;
        filteredProducts.clear();
        for (Product p : allProducts) {
            if (category.equalsIgnoreCase("All") || p.getCategory().equalsIgnoreCase(category)) {
                filteredProducts.add(p);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void searchProducts(String query) {
        filteredProducts.clear();
        for (Product p : allProducts) {
            boolean matchesCategory = currentCategory.equalsIgnoreCase("All") || p.getCategory().equalsIgnoreCase(currentCategory);
            boolean matchesSearch = p.getName().toLowerCase().contains(query.toLowerCase());
            if (matchesCategory && matchesSearch) {
                filteredProducts.add(p);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
