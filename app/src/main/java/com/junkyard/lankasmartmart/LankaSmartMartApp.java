package com.junkyard.lankasmartmart;

import android.app.Application;
import com.google.firebase.database.FirebaseDatabase;

public class LankaSmartMartApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // This single line enables offline sync for the entire app
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
