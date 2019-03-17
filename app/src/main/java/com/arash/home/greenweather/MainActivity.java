package com.arash.home.greenweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cz.msebera.android.httpclient.Header;

import android.os.Bundle;
import android.widget.TextView;

import com.arash.home.greenweather.openweathermap_api_pojos.WeatherAPI;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView todaysTemp = findViewById(R.id.todaysTemp);
        TextView cityNameDemo = findViewById(R.id.cityNameDemo);

        final ArrayList<String> weekDaysTemp = new ArrayList<>();
        String cityName;

//        EditText edttxtCity = findViewById(R.id.edttxtCity);
//
//        if (edttxtCity.getText() != null) {
//            cityName = edttxtCity.getText().toString();
//        } else {
//            cityName = "Tehran";
//        }

        cityName = "Tehran";
        cityNameDemo.setText(cityName);

        final String openWeatherMapAPIURL = "https://api.openweathermap.org/data/2.5/forecast?q=" + cityName + "&appid=a66afa073601d5015bd14559a262fff5&units=metric";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(openWeatherMapAPIURL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                String json = response.toString();

                Gson gson = new Gson();
                WeatherAPI weatherAPI = gson.fromJson(json, WeatherAPI.class);

                java.util.List<com.arash.home.greenweather.openweathermap_api_pojos.List> list = weatherAPI.getList();
                String todayTemp = String.format(Locale.getDefault(), "%.1f °C", list.get(0).getMain().getTemp());
                todaysTemp.setText(todayTemp);

                for (int i = 0; i < list.size(); i += 8) {
                    String weekDayTemp = String.format(Locale.getDefault(), "%.1f °C", list.get(i).getMain().getTemp());
                    weekDaysTemp.add(weekDayTemp);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

        RecyclerView recycler = findViewById(R.id.recycler_forecast);
        ForecastRecyclerAdaptor adaptor = new ForecastRecyclerAdaptor(weekDaysTemp);
        recycler.setAdapter(adaptor);
        recycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
    }
}
