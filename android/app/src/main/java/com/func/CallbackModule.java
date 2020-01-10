package com.func;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;


import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.func.zxing.android.CaptureActivity;

import static android.app.Activity.RESULT_OK;

public class CallbackModule extends ReactContextBaseJavaModule {
    private static final int REQUEST_CODE_SCAN = 0x0000;
    private static final String SCAN_CANCELLED = "SCAN_CANCELLED";
    private static final String DECODED_CONTENT_KEY = "codedContent";
    private ReactContext mReactContext;

    public CallbackModule(@NonNull ReactApplicationContext reactContext) {
        super(reactContext);
        this.mReactContext = reactContext;
        reactContext.addActivityEventListener(mActivityEventListener);
    }

    @NonNull
    @Override
    public String getName() {
        return "CallbackModule1";
    }


    private Promise mPickerPromise;

    private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {

        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent intent) {

            System.out.println("activity = [" + activity + "], requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], intent = [" + intent + "]");
            if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK){
                Toast.makeText(activity, intent.getStringExtra(DECODED_CONTENT_KEY), Toast.LENGTH_SHORT).show();
                mPickerPromise.resolve(intent.getStringExtra(DECODED_CONTENT_KEY));
            }
        }
    };

    @ReactMethod
    public void doScan(Promise promise){
        mPickerPromise = promise;
        Activity currentActivity = getCurrentActivity();
        if (ContextCompat.checkSelfPermission(currentActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(currentActivity, new String[]{Manifest.permission.CAMERA}, 1);
        } else {
            Intent intent = new Intent(mReactContext, CaptureActivity.class);
            currentActivity.startActivityForResult(intent,REQUEST_CODE_SCAN);
        }

    }
}
