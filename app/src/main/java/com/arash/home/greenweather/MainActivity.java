package com.arash.home.greenweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cz.msebera.android.httpclient.Header;

import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView todaysTemp = findViewById(R.id.todaysTemp);
        TextView cityNameDemo = findViewById(R.id.cityNameDemo);

        final ArrayList<String> weekDaysTemp = new ArrayList<>();

        Hawk.init(this).build();

        final TextView celText = findViewById(R.id.celsius);
        final TextView fahText = findViewById(R.id.fahrenheit);
        final Drawable unitBack = getResources().getDrawable(R.drawable.unit_background_shape);

        if (Hawk.get("unit") == null) {
            Hawk.put("unit", "metric");
            unit = "metric";
        } else {
            unit = Hawk.get("unit");
        }

        if (unit.equals("imperial")) {
            fahText.setBackground(unitBack);
            celText.setBackground(null);
        } else {
            celText.setBackground(unitBack);
            fahText.setBackground(null);
        }

        fahText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unit = "imperial";
                Hawk.put("unit", "imperial");
                MainActivity.this.recreate();
            }
        });
        celText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unit = "metric";
                Hawk.put("unit", "metric");
                MainActivity.this.recreate();
            }
        });

        final EditText edttxtCity = findViewById(R.id.edttxtCity);
        Button btnCitySelect = findViewById(R.id.btnCitySelect);

        if (Hawk.get("city") == null) {
            Hawk.put("city", "Tehran");
            cityName = "Tehran";
        } else {
            cityName = Hawk.get("city");
        }


        btnCitySelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hawk.put("city", edttxtCity.getText().toString());
                cityName = edttxtCity.getText().toString();
                MainActivity.this.recreate();
            }
        });

        cityNameDemo.setText(cityName);

        final String openWeatherMapAPIURL = "https://api.openweathermap.org/data/2.5/forecast?q=" + cityName + "&appid=a66afa073601d5015bd14559a262fff5&units=" + unit;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(openWeatherMapAPIURL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                String json = response.toString();

                System.out.println(json);

                Gson gson = new Gson();
                WeatherAPI weatherAPI = gson.fromJson(json, WeatherAPI.class);

                String unitSymbol;
                if (unit.equals("metric")) {
                    unitSymbol = "°C";
                } else {
                    unitSymbol = "°F";
                }

                java.util.List<com.arash.home.greenweather.openweathermap_api_pojos.List> list = weatherAPI.getList();
                String todayTemp = String.format(Locale.getDefault(), "%.1f %s", list.get(0).getMain().getTemp(), unitSymbol);
                todaysTemp.setText(todayTemp);

                for (int i = 0; i < list.size(); i += 8) {
                    String weekDayTemp = String.format(Locale.getDefault(), "%.1f %s", list.get(i).getMain().getTemp(), unitSymbol);
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

        Log.d("Activity", "Created");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Activity", "Started");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Activity", "Resumed");
    }
}
