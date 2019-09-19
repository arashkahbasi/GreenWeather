package com.arash.home.greenweather.weather_contract;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.widget.AppCompatImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.arash.home.greenweather.app_configs.AppController;
import com.arash.home.greenweather.app_configs.BasicConfigs;
import com.arash.home.greenweather.models.open_weather_model.WeatherModel;
import com.google.gson.Gson;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherAPIModel implements WeatherContract.Model {

    @Override
    public void getWeatherDetails(OnFinishedListener onFinishedListener, String city, String unit) {
        WeatherApiServices apiServices = AppController.getClient(BasicConfigs.BASE_URL).create(WeatherApiServices.class);
        apiServices.getWeatherDetails(city, unit, BasicConfigs.API_KEY).enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                if (response.body() != null) {
                    if (Integer.parseInt(response.body().getCod()) == 200) {
                        onFinishedListener.onFinished(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
