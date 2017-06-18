package com.mta.livedataexample;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

/**
 * Created by amir on 6/17/17.
 */

public class Util {
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 654; // code must be < 2^16
    private static final String TAG = Util.class.getSimpleName();

    public static void checkUserStatus(MyCallback b) {
        // can replace this with a login logic, or user preference logic
        b.result(true);
    }

    /**
     * re. permissions, read:
     * https://developer.android.com/training/permissions/requesting.html
     * and possibly:
     * https://inthecheesefactory.com/blog/things-you-need-to-know-about-android-m-permission-developer-edition/en
     *
     * warning: this is not a good example with regards for memory leak: the dialog has a reference
     * to the activity, and it's still there after rotation.
     * Should actually implement requestPermissions() inside the activity, and call it via with EventBus or similar.
     * It will remove the need for passing the Activity here
     *
     * @param activity will leak when rotating the screen. but for a quick demo for something else it's ok
     */
    public static void requsetLocationPermission(Activity activity) {
        Log.i(TAG, "in requsetLocationPermission");

        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)) {

            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.

            explainRationale(activity);

        } else {

            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }

    }

    private static void explainRationale(final Activity activity) {
        new AlertDialog.Builder(activity)
                .setMessage(R.string.rationale)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // open the native request dialog
                        ActivityCompat.requestPermissions(activity,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                    }
                })
                .setNegativeButton("Cancel", null) // todo: tell the user we're sorry but we have to go
                .create()
                .show();
    }

    public static void onPermissionRequestResult(MainActivity mainActivity, int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    // hasPermission = true;

                    LocationLiveData locationLiveData = LocationLiveData.get(mainActivity);
                    locationLiveData.onPermissionGranted();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public interface MyCallback {

        void result(boolean b);
    }
}
