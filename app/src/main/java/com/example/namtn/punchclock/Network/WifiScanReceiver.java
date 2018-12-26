package com.example.namtn.punchclock.Network;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.net.wifi.rtt.RangingRequest;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class WifiScanReceiver extends BroadcastReceiver {

    private String TAG = "PERMISSION";
    private WifiManager mWifiManager;
    public boolean mLocationPermissionApproved = false;
    List<ScanResult> mAccessPointsSupporting80211mc;

    public WifiScanReceiver(WifiManager mWifiManager, boolean mLocationPermissionApproved,
                            List<ScanResult> mAccessPointsSupporting80211mc) {
        this.mWifiManager = mWifiManager;
        this.mLocationPermissionApproved = mLocationPermissionApproved;
        this.mAccessPointsSupporting80211mc = mAccessPointsSupporting80211mc;
    }

    private List<ScanResult> find80211mcSupportedAccessPoints(
            @NonNull List<ScanResult> originalList) {
        List<ScanResult> newList = new ArrayList<>();

        for (ScanResult scanResult : originalList) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (scanResult.is80211mcResponder()) {
                    newList.add(scanResult);
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                if (newList.size() >= RangingRequest.getMaxPeers()) {
                    break;
                }
            }
        }
        return newList;
    }

    // This is checked via mLocationPermissionApproved boolean
    @SuppressLint("MissingPermission")
    public void onReceive(Context context, Intent intent) {

        List<ScanResult> scanResults = mWifiManager.getScanResults();

        if (scanResults != null) {

            if (mLocationPermissionApproved) {
                mAccessPointsSupporting80211mc = find80211mcSupportedAccessPoints(scanResults);
                for (int i = 0; i < mAccessPointsSupporting80211mc.size(); i++) {
                    Log.d("WIFI", mAccessPointsSupporting80211mc.get(0).SSID + "");
                };
            } else {
                // TODO (jewalker): Add Snackbar regarding permissions
                Log.d(TAG, "Permissions not allowed.");
            }
        }
    }
}
