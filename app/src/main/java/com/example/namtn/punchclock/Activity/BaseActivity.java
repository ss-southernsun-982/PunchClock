package com.example.namtn.punchclock.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

public abstract class BaseActivity extends AppCompatActivity implements GoogleApiClient
        .ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private Location location;
    private GoogleApiClient googleApiClient;
    private static final int PLAY_SERVICE_REQUEST_CODE = 123;
    private LocationRequest locationRequest;
    private static final int UPDATE_INTERVAL = 5000, FAST_UPDATE = 3000;

    private ArrayList<String> permissionToRequest;
    private ArrayList<String> permissionRejected;
    private ArrayList<String> permissions;
    private static final int REQUEST_CODE_PERMISSION = 111;
    private Location currentLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        hideStatusBar();
        initView();
        initEventControl();
        initData();
        locationPermission();
    }

    private void locationPermission() {
        permissionRejected = new ArrayList<>();
        permissionToRequest = new ArrayList<>();
        permissions = new ArrayList<>();

        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        permissionToRequest = permissionReject(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionToRequest.size() > 0) {
                requestPermissions(permissionToRequest.toArray(new String[permissionToRequest
                        .size()]), REQUEST_CODE_PERMISSION);
            }
        }

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
    }

    private ArrayList<String> permissionReject(ArrayList<String> permissions) {
        ArrayList<String> listResult = new ArrayList<>();
        for (String per : permissions) {
            if (!hashPermission(per)) {
                listResult.add(per);
            }
        }
        return listResult;
    }

    private boolean hashPermission(String per) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(per) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private void hideStatusBar() {
        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
    }

    protected abstract int getContentView();

    protected abstract void initView();

    protected abstract void initEventControl();

    protected abstract void initData();

    @Override
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!checkPlayService()) {
            Toast.makeText(this, "Device not support!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (googleApiClient != null && googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            googleApiClient.disconnect();
        }
    }

    private boolean checkPlayService() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICE_REQUEST_CODE);
            } else {

            }
            return false;
        }
        return true;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (currentLocation != null) {
        }

        getLocationUpdate();
    }

    private void getLocationUpdate() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Enable permission location", Toast.LENGTH_SHORT).show();
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,
                locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION:
                for (String perm : permissionToRequest) {
                    if (!hashPermission(perm)) {
                        permissionRejected.add(perm);
                    }
                }
                if (permissionRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionRejected.get(0))) {
                            new AlertDialog.Builder(getApplicationContext())
                                    .setMessage("Enable location permission")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                                                requestPermissions(permissionRejected.toArray(new
                                                                String[permissionRejected.size()]),
                                                        REQUEST_CODE_PERMISSION);
                                            }
                                        }
                                    })
                                    .setNegativeButton("Cancel", null)
                                    .create()
                                    .show();
                        }
                    }
                } else {
                    if (googleApiClient != null) {
                        googleApiClient.connect();
                    }
                }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
    }
}
