package com.arash.home.greenweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.arash.home.greenweather.openweathermap_api_pojos.WeatherAPI;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherHomeActivity extends AppCompatActivity {

    static String unit;
    static String cityName;

    @BindView(R.id.tv_city_name)
    TextView tvCityName;
    @BindView(R.id.recycler_forecast)
    RecyclerView recyclerForcast;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_home);

        ButterKnife.bind(this);
        final TextView todaysTemp = findViewById(R.id.tv_todays_temp);

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
                WeatherHomeActivity.this.recreate();
            }
        });
        celText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unit = "metric";
                Hawk.put("unit", "metric");
                WeatherHomeActivity.this.recreate();
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
                WeatherHomeActivity.this.recreate();
            }
        });

        tvCityName.setText(cityName);

        WeatherAPIStatus weatherAPIStatus = new WeatherAPIStatus(this, cityName, unit);

        weatherAPIStatus.getWeatherStatus(new WeatherAPIStatus.WeatherStatus() {
            @Override
            public void onWeatherStatus(WeatherAPI weatherAPI) {
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
        });


        ForecastRecyclerAdapter adapter = new ForecastRecyclerAdapter(weekDaysTemp);
        recyclerForcast.setAdapter(adapter);
        recyclerForcast.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        Log.d("Activity", "Created");
    }

}
