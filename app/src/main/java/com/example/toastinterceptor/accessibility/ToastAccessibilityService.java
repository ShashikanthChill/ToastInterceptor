package com.example.toastinterceptor.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Notification;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.example.toastinterceptor.db.ToastEntity;
import com.example.toastinterceptor.db.ToastRepository;

import java.util.Date;

public class ToastAccessibilityService extends AccessibilityService {

    private static final String TAG = "Accessibility Service";
    private static boolean serviceEnabled = false;
    private ToastRepository repo;

    @Override
    protected void onServiceConnected() {
        serviceEnabled = true;
        Log.i(TAG, "Service Connected");

        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;
        this.setServiceInfo(info);

        repo = new ToastRepository(getApplication());
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        String pkgName = event.getPackageName().toString();
        Date eventDate = new Date(System.currentTimeMillis());
        String content = null;
        new Date(event.getEventTime());
        Parcelable parcelableData = event.getParcelableData();
        if (!(parcelableData instanceof Notification)) {
            content = event.getText().get(0).toString();
        } else return;

//        Log.i(TAG, "Event Fired");
//        Log.i(TAG, "From Package: " + pkgName);
//        Log.i(TAG, "Message: " + content);
//        Log.i(TAG, "At: " + eventDate.toString());

        if (repo != null) {
            repo.insert(new ToastEntity(pkgName, content, eventDate));
        }
    }

    @Override
    public void onInterrupt() {
        Log.e(TAG, "Event Interrupted");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        serviceEnabled = false;
        Log.d(TAG, "onUnbind: Service disconnected");
        return super.onUnbind(intent);
    }

    public static boolean isServiceEnabled() {
        return serviceEnabled;
    }
}
