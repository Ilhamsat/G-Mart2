package com.gmartdev.komsi.g_mart.Class;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.gmartdev.komsi.g_mart.R;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.AndroidXMapFragment;

import java.io.File;

public class MapsActivity extends AppCompatActivity {

    private Map map = null;
    private AndroidXMapFragment mapFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_maps);
        initialize();
    }

    private void initialize() {
        setContentView(R.layout.activity_maps);

        // Search for the map fragment to finish setup by calling init().
        mapFragment = (AndroidXMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapfragment);

        // Set up disk cache path for the map service for this application
        // It is recommended to use a path under your application folder for storing the disk cache
        boolean success = com.here.android.mpa.common.MapSettings.setIsolatedDiskCacheRootPath(
                getApplicationContext().getExternalFilesDir(null) + File.separator + ".here-maps");

        if (!success) {
            Toast.makeText(getApplicationContext(), "Unable to set isolated disk cache path.", Toast.LENGTH_LONG);
        } else {
            mapFragment.init(new OnEngineInitListener() {
                @Override
                public void onEngineInitializationCompleted(OnEngineInitListener.Error error) {
                    if (error == OnEngineInitListener.Error.NONE) {
                        // retrieve a reference of the map from the map fragment
                        map = mapFragment.getMap();
                        // Set the map center to the Vancouver region (no animation)
                        map.setCenter(new GeoCoordinate(49.196261, -123.004773, 0.0),
                                Map.Animation.NONE);
                        // Set the zoom level to the average between min and max
                        map.setZoomLevel((map.getMaxZoomLevel() + map.getMinZoomLevel()) / 2);
                    } else {
                        System.out.println("ERROR: Cannot initialize Map Fragment");
                    }
                }
            });
        }
    }

}
