package com.example.namtn.punchclock.Activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.namtn.punchclock.Network.WifiScanReceiver;
import com.example.namtn.punchclock.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public abstract class BaseActivity extends AppCompatActivity implements GoogleApiClient
        .ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private Location location;
    private Geocoder geocoder;
    private GoogleApiClient googleApiClient;
    private static final int PLAY_SERVICE_REQUEST_CODE = 123;
    private LocationRequest locationRequest;
    private static final int UPDATE_INTERVAL = 5000, FAST_UPDATE = 3000;

    private ArrayList<String> permissionToRequest;
    private ArrayList<String> permissionRejected;
    private ArrayList<String> permissions;
    private static final int REQUEST_CODE_PERMISSION = 111;
    private Location currentLocation;
    private SharedPreferences preferences, preferencesUser;
    private SharedPreferences.Editor editor, editorUser;
    private String TAG = "DESTANCE___1";

    private boolean mLocationPermissionApproved = false;
    private List<ScanResult> mAccessPointsSupporting80211mc;
    private WifiManager mWifiManager;
    private WifiScanReceiver mWifiScanReceiver;
    private LocationManager lm;
    private boolean gps_enabled = false;
    private boolean network_enabled = false;
    private String role = "guest";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        hideStatusBar();
        initWifiScan();
        initView();
        initEventControl();
        initData();
        locationPermission();
        checkEnableGPS();
        setLayoutWithRole();
        preferences = this.getSharedPreferences("data_map", MODE_PRIVATE);
        preferencesUser = this.getSharedPreferences("user_data", Context.MODE_PRIVATE);
        editor = preferences.edit();
        editorUser = preferencesUser.edit();

        if (savedInstanceState != null) {

        } else {

        }
    }

    public static void main(String[] args) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void initWifiScan() {
        mAccessPointsSupporting80211mc = new ArrayList<>();

        mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        mWifiScanReceiver = new WifiScanReceiver(mWifiManager, mLocationPermissionApproved,
                mAccessPointsSupporting80211mc);

        if (mWifiScanReceiver.mLocationPermissionApproved) {
            finish();
        }
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

    public void setLayoutWithRole(){
        preferencesUser = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        if (preferencesUser != null){
            if (preferencesUser.getString("userRole", "guest") != null){
                role = preferencesUser.getString("userRole", "guest");
                if (role.equalsIgnoreCase("ADMIN")){
                    this.setAdminLayout();
                } else {
                    this.setGuestLayout();
                }
            } else {
                this.setGuestLayout();
            }
        }
    }

    public void setAdminLayout(){

    }

    public void setGuestLayout(){

    }

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
                        } else {
                            Toast.makeText(this, "Vui lòng bật GPS", Toast.LENGTH_SHORT).show();
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
        List<Address> mListAddress = new ArrayList<>();
        String mAddress = "Not address found";
        LatLng mLocationCurrent = new LatLng(location.getLatitude(), location.getLongitude());
        LatLng mLocationCompany = new LatLng(10.78595279, 106.68036476);
        double mDistance = SphericalUtil.computeDistanceBetween(mLocationCurrent, mLocationCompany);
        mDistance = Math.round(mDistance * 10) / 10;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            mListAddress = geocoder.getFromLocation(location.getLatitude(), location.getLongitude
                    (), 1);
            if (mListAddress.size() > 0) {
                mAddress = mListAddress.get(0).getAddressLine(0);
            } else {
                mAddress = "Not found address in here";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        long time = location.getTime();
        editor.putLong("mDistance", (long) mDistance);
        editor.putString("mAddress", mAddress);
        editor.putString("mLocationTime", getDate(time, "yyyy-MM-dd"));
        editor.commit();
        Log.d(TAG, "onLocationChanged: " + getDate(time, "yyyy-MM-dd"));
    }

    private String getDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        DateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to
        // date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public void checkEnableGPS(){
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled && !network_enabled) {
            // notify user
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Vui lòng bật GPS");
            dialog.setPositiveButton(getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    finish();
                }
            });
            dialog.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
