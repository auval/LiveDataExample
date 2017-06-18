package com.mta.livedataexample;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by amir on 6/17/17.
 */

public class LocationLiveData extends LiveData<Location> {
    private static final String TAG = LocationLiveData.class.getSimpleName();
    // the instance can be singleton, since the lifecycle awareness prevents memory leak
    private static LocationLiveData sInstance;
    private boolean hasPermission = false;
    private LocationManager locationManager;
    private LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.i(TAG, "LocationListener: onLocationChanged to " + location);

            setValue(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            Log.i(TAG, "LocationListener: onStatusChanged to " + s + "; " + i + "; " + bundle);

        }

        @Override
        public void onProviderEnabled(String s) {
            Log.i(TAG, "LocationListener: onProviderEnabled: " + s);

        }

        @Override
        public void onProviderDisabled(String s) {
            Log.i(TAG, "LocationListener: onProviderDisabled: " + s);

        }
    };


    private LocationLiveData(Activity activity) {
        locationManager = (LocationManager) activity.getApplicationContext().getSystemService(
                Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            hasPermission = false;
            //  throw new IllegalStateException("Location permission not granted!");

            Util.requsetLocationPermission(activity);
        } else {
            hasPermission = true;
        }
    }


    @MainThread
    public static LocationLiveData get(Activity context) {
        if (sInstance == null) {
            sInstance = new LocationLiveData(context);
        }
        return sInstance;
    }

    /**
     * “Active” is a STARTED or RESUMED observable (LifecycleOwner)
     */
    @SuppressWarnings("MissingPermission") // handling the permission part in the constructor
    @Override
    protected void onActive() {
        Log.i(TAG, "onActive: hasPermission? " + hasPermission);
        if (hasPermission) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
        }
    }

    @Override
    protected void onInactive() {
        locationManager.removeUpdates(listener);
    }

    public void onPermissionGranted() {
        hasPermission = true;
    }
}
