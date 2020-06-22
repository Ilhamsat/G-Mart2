package com.gmartdev.komsi.g_mart.Class.Maps;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.gmartdev.komsi.g_mart.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
//import com.here.android.mpa.common.GeoCoordinate;
//import com.here.android.mpa.common.OnEngineInitListener;
//import com.here.android.mpa.mapping.Map;
//import com.here.android.mpa.mapping.AndroidXMapFragment;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private MarkerOptions mMarkerOptions;
    private LatLng mDevice;
    private LatLng mKios;
    private Polyline mPolyline;
    //private Map map = null;
    //private AndroidXMapFragment mapFragment = null;

    double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //initialize();
    }

    public void onMapReady(GoogleMap googleMap){
        mMap = googleMap;
        getDeviceLocation();

    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults){
        if (requestCode == 100){
            if (!verifyAllPermissions(grantResults)){
                Toast.makeText(getApplicationContext(),"No sufficient permissions",Toast.LENGTH_LONG).show();
            }else {
                getDeviceLocation();
            }
        }else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private boolean verifyAllPermissions(int[] grantResults){
        for (int result : grantResults){
            if (result != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    private void getDeviceLocation(){
        //Getting LocationManager object from System Service LOCATION_SERVICE
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mDevice = new LatLng(location.getLatitude(),location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDevice,13));
                if (mDevice != null && mKios != null)
                    drawRoute();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        int currentApiVersion = Build.VERSION.SDK_INT;
        if (currentApiVersion >= Build.VERSION_CODES.M){

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_DENIED){
                mMap.setMyLocationEnabled(true);
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000,0,mLocationListener);

                latitude = Double.parseDouble(getIntent().getStringExtra("latitude"));
                longitude = Double.parseDouble(getIntent().getStringExtra("longitude"));

                Log.d(TAG, "onCreate: latitude " + latitude);
                Log.d(TAG, "onCreate: longitude " + longitude);

                //Destination (Kios)
                LatLng kios = new LatLng(latitude,longitude);
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(kios).title("Kios"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kios,13));
                GroundOverlayOptions kiosOverlay = new GroundOverlayOptions()
                        .image(BitmapDescriptorFactory.fromResource(R.drawable.ic_store_name))
                        .position(kios,100);
                mMap.addGroundOverlay(kiosOverlay);

                if (mDevice != null && mKios !=null)
                    drawRoute();
            }else {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                },100);
            }
        }
    }

    private void drawRoute(){
        //Getting URL to the Google Directions API
        String url = getDirectionsUrl(mDevice, mKios);

        DownloadTask downloadTask = new DownloadTask();

        //Start downloading JSON data from Google Directions API
        downloadTask.execute(url);
    }

    private String getDirectionsUrl(LatLng device, LatLng kios){
        //Device
        String str_device = "device="+device.latitude+","+device.longitude;

        //Kios
        String str_kios = "kios="+kios.latitude+","+kios.longitude;

        //Key
        String key = "key=" + getString(R.string.google_maps_key);

        //Building the parameters to the web service
        String parameters = str_device+"&"+str_kios+"&"+key;

        //Output format
        String output = "json";

        //Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }

    //Method to download JSON data from URL
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            //http connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();
            String line ="";
            while ((line = bufferedReader.readLine()) != null){
                stringBuffer.append(line);
            }
            data = stringBuffer.toString();
            bufferedReader.close();

        }catch (Exception e){
            Log.d("Exception on download", e.toString());
        }finally {
            inputStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    //Download Data From URL
    private class DownloadTask extends AsyncTask<String,Void,String>{
        //Downloading data in non-ui thread
        protected  String doInBackground(String... url){
            //storing data
            String data = "";

            try {
                //fetching data
                data = downloadUrl(url[0]);
                Log.d("DownloadTask","DownloadTask : " + data);
            }catch (Exception e){
                Log.d("Background Task", e.toString());
            }
            return data;
        }
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();

            //Invokes the thread for parsing the JSON Data
            parserTask.execute(result);
        }
    }

    //Parse the Google Directions in JSON Format
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{
        //parsing the data in non-ui thread

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
            JSONObject jsonObject;
            List<List<HashMap<String,String>>> routes = null;

            try {
                jsonObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                //start parsing data
                routes = parser.parse(jsonObject);
            }catch (Exception e){
                e.printStackTrace();
            }
            return routes;
        }
        protected void onPostExecute(List<List<HashMap<String,String>>> result){
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;

            //Traversing through all the routes
            for (int i=0; i<result.size(); i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                //fetching i-th route
                List<HashMap<String,String>> path = result.get(i);

                //fetching all the points in i-th route
                for (int j=0; j<path.size(); j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat,lng);

                    points.add(position);
                }
                //adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(8);
                lineOptions.color(Color.BLUE);
            }
            //Drawing polyline in the Google Map for the i-th route
            if (lineOptions != null){
                if (mPolyline != null){
                    mPolyline.remove();
                }
                mPolyline = mMap.addPolyline(lineOptions);
            }else {
                Toast.makeText(getApplicationContext(),"No route is found", Toast.LENGTH_LONG).show();
            }
            }

    }

//    private void initialize() {
//        setContentView(R.layout.activity_maps);
//
//        // Search for the map fragment to finish setup by calling init().
//        mapFragment = (AndroidXMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapfragment);
//
//        // Set up disk cache path for the map service for this application
//        // It is recommended to use a path under your application folder for storing the disk cache
//        boolean success = com.here.android.mpa.common.MapSettings.setIsolatedDiskCacheRootPath(
//                getApplicationContext().getExternalFilesDir(null) + File.separator + ".here-maps");
//
//        if (!success) {
//            Toast.makeText(getApplicationContext(), "Unable to set isolated disk cache path.", Toast.LENGTH_LONG);
//        } else {
//            mapFragment.init(new OnEngineInitListener() {
//                @Override
//                public void onEngineInitializationCompleted(OnEngineInitListener.Error error) {
//                    if (error == OnEngineInitListener.Error.NONE) {
//                        // retrieve a reference of the map from the map fragment
//                        map = mapFragment.getMap();
//                        // Set the map center to the Vancouver region (no animation)
//                        map.setCenter(new GeoCoordinate(latitude, longitude, 0.0),
//                                Map.Animation.NONE);
//                        // Set the zoom level to the average between min and max
//                        map.setZoomLevel((map.getMaxZoomLevel() + map.getMinZoomLevel()) / 2);
//                    } else {
//                        System.out.println("ERROR: Cannot initialize Map Fragment");
//                    }
//                }
//            });
//        }
//    }

}
