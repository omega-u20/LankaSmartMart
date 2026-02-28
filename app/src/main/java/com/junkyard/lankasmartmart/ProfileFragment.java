package com.junkyard.lankasmartmart;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    private TextView txtProfileName, txtProfileEmail;
    private View btnLogout, btnSyncData, btnMyOrders;
    private ImageView imgSyncStatus;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtProfileName = view.findViewById(R.id.txtProfileName);
        txtProfileEmail = view.findViewById(R.id.txtProfileEmail);
        btnLogout = view.findViewById(R.id.btnLogout);
        btnSyncData = view.findViewById(R.id.btnSyncData);
        imgSyncStatus = view.findViewById(R.id.imgSyncStatus);
        btnMyOrders = view.findViewById(R.id.btnMyOrders);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            txtProfileEmail.setText(email);
            
            DatabaseReference userRef = FirebaseDatabase.getInstance("https://lanka-smartmart-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference("users")
                    .child(user.getUid());
            
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String name = snapshot.child("name").getValue(String.class);
                        if (name != null) txtProfileName.setText(name);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }

        btnMyOrders.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), OrdersActivity.class));
        });

        btnSyncData.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Offline sync is active. Your data will sync automatically.", Toast.LENGTH_LONG).show();
            imgSyncStatus.animate().rotationBy(360).setDuration(500).start();
        });

        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}
