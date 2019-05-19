package com.example.toastinterceptor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toastinterceptor.accessibility.ToastAccessibilityService;
import com.example.toastinterceptor.db.ToastEntity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    TextView emptyView;

    public boolean isServiceConnected(Context context) {
        int accessibilityEnabled = 0;
        final String service = getPackageName() + "/" + ToastAccessibilityService.class.getCanonicalName();
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    context.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
            Log.v("Service Status", "accessibilityEnabled = " + accessibilityEnabled);
        } catch (Settings.SettingNotFoundException e) {
            Log.e("Service Status", "Error finding setting, default accessibility to not found: "
                    + e.getMessage());
        }
        TextUtils.SimpleStringSplitter stringColonSplitter = new TextUtils.SimpleStringSplitter(':');
        if (accessibilityEnabled == 1) {
            Log.v("Service Status", "***ACCESSIBILITY IS ENABLED*** -----------------");
            String settingValue = Settings.Secure.getString(
                    context.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                stringColonSplitter.setString(settingValue);
                while (stringColonSplitter.hasNext()) {
                    String accessibilityService = stringColonSplitter.next();

                    Log.v("Service Status", "-------------- > accessibilityService :: " + accessibilityService + " " + service);
                    if (accessibilityService.equalsIgnoreCase(service)) {
                        Log.v("Service Status", "We've found the correct setting - accessibility is switched on!");
                        return true;
                    } else {
                        Log.v("Service Status", "We've not found your service. Not switched on!");
                    }
                }
            }
        } else {
            Log.v("Service Status", "***ACCESSIBILITY IS DISABLED***");
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (!ToastAccessibilityService.isServiceEnabled()) {
            Log.d("Service connected?", "No");
            getAlertDialog().show();
        } else {
            Log.d("Service connected?", "Yes");
        }


        recyclerView = findViewById(R.id.rv);
        emptyView = findViewById(R.id.empty_view);


        final ToastListAdapter toastListAdapter = new ToastListAdapter(getApplication());
        recyclerView.setAdapter(toastListAdapter);


        ToastViewModel toastViewModel = ViewModelProviders.of(this).get(ToastViewModel.class);

        LiveData<List<ToastEntity>> toasts = toastViewModel.getAllToasts();

        toasts.observe(this, new Observer<List<ToastEntity>>() {
            @Override
            public void onChanged(List<ToastEntity> toastEntities) {

                if (toastEntities.size() == 0) {
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                }

//                Update the cached copy of the words in the adapter.
                toastListAdapter.setToasts(toastEntities);
            }
        });
    }

    private AlertDialog getAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.perm_title)
                .setMessage(R.string.perm_message)
                .setCancelable(false)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
                        Toast.makeText(getApplicationContext(), R.string.perm_enable_info, Toast.LENGTH_SHORT).show();
                    }
                });
        return builder.create();
    }
}

