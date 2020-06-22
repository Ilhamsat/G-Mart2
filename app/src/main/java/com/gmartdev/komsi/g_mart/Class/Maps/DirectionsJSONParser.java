package com.gmartdev.komsi.g_mart.Class.Maps;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DirectionsJSONParser {
    //Menerima JSONObject dan mengembalikan daftar lat dan lng
    public List<List<HashMap<String,String>>> parse(JSONObject jsonObject){
        List<List<HashMap<String,String>>> routes = new ArrayList<>();
        JSONArray jsonRoutes = null;
        JSONArray jsonLegs = null;
        JSONArray jsonSteps = null;

        try {
            jsonRoutes = jsonObject.getJSONArray("routes");

            //Traversing all routes
            for (int i=0; i<jsonRoutes.length(); i++){
                jsonLegs = ((JSONObject)jsonRoutes.get(i)).getJSONArray("legs");
                List path = new ArrayList<HashMap<String,String>>();

                //Traversing all legs
                for (int j=0; j<jsonLegs.length(); j++){
                    jsonSteps = ((JSONObject)jsonLegs.get(j)).getJSONArray("steps");

                    //Traversing all steps
                    for (int k=0; k<jsonSteps.length(); k++){
                        String polyline = "";
                        polyline = (String)((JSONObject)((JSONObject)jsonSteps.get(k)).get("polyline")).get("points");
                        List<LatLng> list = decodePoly(polyline);

                        //Traversing all points
                        for (int l=0; l<list.size(); l++){
                            HashMap<String,String> hashMap = new HashMap<String, String>();
                            hashMap.put("lat", Double.toString(((LatLng)list.get(l)).latitude));
                            hashMap.put("lng", Double.toString(((LatLng)list.get(l)).longitude));
                            path.add(hashMap);
                        }
                    }
                    routes.add(path);
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }catch (Exception e){
        }
        return routes;
    }

    //Decode Polyline Points
    private List<LatLng> decodePoly(String encoded){
        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len){
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) !=0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            }while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }
        return poly;

    }

}