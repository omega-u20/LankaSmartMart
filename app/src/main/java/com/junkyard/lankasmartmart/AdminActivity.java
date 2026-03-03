package com.junkyard.lankasmartmart;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminActivity extends AppCompatActivity {

    private EditText etProductId, etProductName, etProductQuantity, etUnitPrice;
    private Spinner spinnerCategory;
    private Button btnSaveProduct, btnUpdateProduct, btnViewDashboard, btnLogoutAdmin;
    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        db = FirebaseDatabase.getInstance("https://lanka-smartmart-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("products");

        etProductId = findViewById(R.id.etProductId);
        etProductName = findViewById(R.id.etProductName);
        etProductQuantity = findViewById(R.id.etProductQuantity);
        etUnitPrice = findViewById(R.id.etUnitPrice);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        btnSaveProduct = findViewById(R.id.btnSaveProduct);
        btnUpdateProduct = findViewById(R.id.btnUpdateProduct);
        btnViewDashboard = findViewById(R.id.btnViewDashboard);
        btnLogoutAdmin = findViewById(R.id.btnLogoutAdmin);

        String[] categories = {"Groceries", "Household", "Personal Care", "Stationery", "Vegetables", "Fruits"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spinnerCategory.setAdapter(adapter);

        btnSaveProduct.setOnClickListener(v -> saveProduct());
        btnUpdateProduct.setOnClickListener(v -> updateProduct());
        
        btnViewDashboard.setOnClickListener(v -> {
            startActivity(new Intent(AdminActivity.this, AdminDashboardActivity.class));
        });

        btnLogoutAdmin.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(AdminActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void saveProduct() {
        String name = etProductName.getText().toString().trim();
        String qtyStr = etProductQuantity.getText().toString().trim();
        String priceStr = etUnitPrice.getText().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString();

        if (name.isEmpty() || qtyStr.isEmpty() || priceStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int quantity = Integer.parseInt(qtyStr);
            double price = Double.parseDouble(priceStr);

            String id = db.push().getKey();
            Product product = new Product(id, name, quantity, price, category);

            if (id != null) {
                db.child(id).setValue(product).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(AdminActivity.this, "Product Added", Toast.LENGTH_SHORT).show();
                        clearFields();
                    } else {
                        Toast.makeText(AdminActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateProduct() {
        String id = etProductId.getText().toString().trim();
        String name = etProductName.getText().toString().trim();
        String qtyStr = etProductQuantity.getText().toString().trim();
        String priceStr = etUnitPrice.getText().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString();

        if (id.isEmpty()) {
            Toast.makeText(this, "Please enter Product ID to update", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int quantity = Integer.parseInt(qtyStr);
            double price = Double.parseDouble(priceStr);

            Product product = new Product(id, name, quantity, price, category);

            db.child(id).setValue(product).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(AdminActivity.this, "Product Updated", Toast.LENGTH_SHORT).show();
                    clearFields();
                } else {
                    Toast.makeText(AdminActivity.this, "Update Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        etProductId.setText("");
        etProductName.setText("");
        etProductQuantity.setText("");
        etUnitPrice.setText("");
    }
}
