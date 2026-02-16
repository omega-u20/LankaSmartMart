package com.junkyard.lankasmartmart;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

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

        // Existing buttons...
        View btnLogout = view.findViewById(R.id.btnLogout);

        // NEW: Find Branch Button
        View btnFindBranch = view.findViewById(R.id.btnFindBranch);
        btnFindBranch.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Opening Branch Map...", Toast.LENGTH_SHORT).show();
        });

        // NEW: Track Order Button
        View btnTrackOrder = view.findViewById(R.id.btnTrackOrder);
        btnTrackOrder.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Checking Order Status...", Toast.LENGTH_SHORT).show();
        });

        // Logout logic...
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

    }
}