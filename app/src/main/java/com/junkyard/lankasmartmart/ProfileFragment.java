package com.junkyard.lankasmartmart;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
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
    private View btnLogout, btnSyncData, btnMyOrders, btnFindBranch, btnEditProfileImage;
    private ImageView imgSyncStatus, profileImage;

    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == android.app.Activity.RESULT_OK && result.getData() != null) {
                    Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                    profileImage.setImageBitmap(photo);
                    Toast.makeText(getContext(), "Profile photo updated (local only)", Toast.LENGTH_SHORT).show();
                }
            }
    );

    private final ActivityResultLauncher<String> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    profileImage.setImageURI(uri);
                    Toast.makeText(getContext(), "Profile image updated (local only)", Toast.LENGTH_SHORT).show();
                }
            }
    );

    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                if (isGranted) {
                    openCamera();
                } else {
                    Toast.makeText(getContext(), "Camera permission denied", Toast.LENGTH_SHORT).show();
                }
            }
    );

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
        btnFindBranch = view.findViewById(R.id.btnFindBranch);
        btnEditProfileImage = view.findViewById(R.id.btnEditProfileImage);
        profileImage = view.findViewById(R.id.profileImage);

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

        btnEditProfileImage.setOnClickListener(v -> showImageSourceDialog());

        btnMyOrders.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), OrdersActivity.class));
        });

        btnFindBranch.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), MapsActivity.class));
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

    private void showImageSourceDialog() {
        String[] options = {"Take Photo", "Choose from Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Update Profile Photo");
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.CAMERA);
                }
            } else {
                galleryLauncher.launch("image/*");
            }
        });
        builder.show();
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(takePictureIntent);
    }
}
