package com.junkyard.lankasmartmart;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;

public class AddBranchActivity extends AppCompatActivity {

    private EditText etBranchName, etBranchAddress, etBranchLat, etBranchLng;
    private Button btnSaveBranch, btnBack;
    private DatabaseReference db;
    private MapView map;
    private Marker selectedMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // OSMdroid configuration
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        setContentView(R.layout.activity_add_branch);

        db = FirebaseDatabase.getInstance("https://lanka-smartmart-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("branches");

        etBranchName = findViewById(R.id.etBranchName);
        etBranchAddress = findViewById(R.id.etBranchAddress);
        etBranchLat = findViewById(R.id.etBranchLat);
        etBranchLng = findViewById(R.id.etBranchLng);
        btnSaveBranch = findViewById(R.id.btnSaveBranch);
        btnBack = findViewById(R.id.btnBackFromBranch);

        // Initialize Map
        map = findViewById(R.id.adminMap);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);
        map.getController().setZoom(12.0);
        GeoPoint startPoint = new GeoPoint(6.9271, 79.8612); // Colombo
        map.getController().setCenter(startPoint);

        // Handle Map Clicks
        MapEventsReceiver mReceive = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                etBranchLat.setText(String.valueOf(p.getLatitude()));
                etBranchLng.setText(String.valueOf(p.getLongitude()));

                // Update marker on map
                if (selectedMarker == null) {
                    selectedMarker = new Marker(map);
                    selectedMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                    map.getOverlays().add(selectedMarker);
                }
                selectedMarker.setPosition(p);
                selectedMarker.setTitle("Selected Location");
                map.invalidate();
                return true;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        };

        MapEventsOverlay MapEventsOverlay = new MapEventsOverlay(mReceive);
        map.getOverlays().add(0, MapEventsOverlay);

        btnSaveBranch.setOnClickListener(v -> saveBranch());
        btnBack.setOnClickListener(v -> finish());
    }

    private void saveBranch() {
        String name = etBranchName.getText().toString().trim();
        String address = etBranchAddress.getText().toString().trim();
        String latStr = etBranchLat.getText().toString().trim();
        String lngStr = etBranchLng.getText().toString().trim();

        if (name.isEmpty() || address.isEmpty() || latStr.isEmpty() || lngStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double latitude = Double.parseDouble(latStr);
            double longitude = Double.parseDouble(lngStr);

            String id = db.push().getKey();
            Branch branch = new Branch(id, name, latitude, longitude, address);

            if (id != null) {
                db.child(id).setValue(branch).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(AddBranchActivity.this, "Branch Added to Map", Toast.LENGTH_SHORT).show();
                        clearFields();
                    } else {
                        Toast.makeText(AddBranchActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid coordinates", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        etBranchName.setText("");
        etBranchAddress.setText("");
        etBranchLat.setText("");
        etBranchLng.setText("");
        if (selectedMarker != null) {
            map.getOverlays().remove(selectedMarker);
            selectedMarker = null;
            map.invalidate();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }
}
