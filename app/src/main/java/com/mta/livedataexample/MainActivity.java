package com.mta.livedataexample;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState == null) {
            LocFragment frag = LocFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment,
                            frag, LocFragment.class.getSimpleName()).commit();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        Util.onPermissionRequestResult(this,requestCode, permissions,grantResults);
    }
}
