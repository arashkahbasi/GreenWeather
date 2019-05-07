package com.arash.home.greenweather;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.arash.home.greenweather.openweathermap_api_pojos.WeatherAPI;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherAPIStatus {

    Context context;
    String cityName;
    String unit;

    WeatherAPIStatus(Context context, String cityName, String unit) {
        this.context = context;
        this.cityName = cityName;
        this.unit = unit;
    }

    final static String URL = "https://api.openweathermap.org/data/2.5/forecast?q=";

    public void getWeatherStatus(final WeatherStatus weatherStatus) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URL + cityName + "&appid=a66afa073601d5015bd14559a262fff5&units=" + unit, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Gson gson = new Gson();
                String strJson = response.toString();
                WeatherAPI weatherAPI = gson.fromJson(strJson, WeatherAPI.class);

//                try {
//                   JSONArray jsonArray = response.getJSONArray("list");
//                   JSONObject jsonObject = (JSONObject) jsonArray.get(0);
//                   jsonObject.getJSONObject("main").getDouble("temp");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                weatherStatus.onWeatherStatus(weatherAPI);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("idd", "onErrorResponse: " + error);
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public interface WeatherStatus {
        void onWeatherStatus(WeatherAPI weatherAPI);
    }
}
