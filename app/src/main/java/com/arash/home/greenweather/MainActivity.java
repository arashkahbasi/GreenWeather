package com.arash.home.greenweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cz.msebera.android.httpclient.Header;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.arash.home.greenweather.openweathermap_api_pojos.WeatherAPI;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.orhanobut.hawk.Hawk;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    static String unit;
    static String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView todaysTemp = findViewById(R.id.todaysTemp);
        TextView cityNameDemo = findViewById(R.id.cityNameDemo);

        final ArrayList<String> weekDaysTemp = new ArrayList<>();

        EditText edttxtCity = findViewById(R.id.edttxtCity);

        Hawk.init(this).build();
        if (edttxtCity.getText() != null) {
            cityName = edttxtCity.getText().toString();
        } else {
            cityName = "Tehran";
        }
//        cityName = "Tehran";
        cityNameDemo.setText(cityName);

        final String openWeatherMapAPIURL = "https://api.openweathermap.org/data/2.5/forecast?q=Tehran&appid=a66afa073601d5015bd14559a262fff5&units=metric";
//                String.format(Locale.getDefault(), "https://api.openweathermap.org/data/2.5/forecast?q=%s&appid=a66afa073601d5015bd14559a262fff5&units=metric", cityName);

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


        final TextView celText = findViewById(R.id.celsius);
        final TextView fahText = findViewById(R.id.fahrenheit);
        final Drawable unitBack = getResources().getDrawable(R.drawable.unit_background_shape);

        fahText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fahText.setBackground(unitBack);
                celText.setBackground(null);
                unit = "imperial";
            }
        });
        celText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                celText.setBackground(unitBack);
                fahText.setBackground(null);
                unit = "metric";
            }
        });
    }
}
