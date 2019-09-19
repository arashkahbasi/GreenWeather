package com.arash.home.greenweather.weather_contract;

import com.arash.home.greenweather.models.open_weather_model.WeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiServices {
    @GET("forecast")
    Call<WeatherModel> getWeatherDetails(@Query("q") String cityName,
                                         @Query("units") String units,
                                         @Query("appid") String apiKey);

}
