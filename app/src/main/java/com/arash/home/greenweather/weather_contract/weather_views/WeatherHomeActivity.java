package com.arash.home.greenweather.weather_contract.weather_views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.BlurMaskFilter;
import android.os.Bundle;
import android.view.View;

import com.arash.home.greenweather.R;
import com.arash.home.greenweather.app_configs.BasicConfigs;
import com.arash.home.greenweather.models.open_weather_model.WeatherModel;
import com.arash.home.greenweather.weather_contract.WeatherAPIModel;
import com.arash.home.greenweather.weather_contract.WeatherContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherHomeActivity extends AppCompatActivity implements WeatherContract.View, WeatherContract.Model.OnFinishedListener {

    static String unit;
    static String cityName;

    @BindView(R.id.tv_city_name)
    AppCompatTextView tvCityName;
    @BindView(R.id.tv_todays_temp)
    AppCompatTextView tvTodaysTemp;

    @BindView(R.id.recycler_days)
    RecyclerView recyclerForecast;
    private DaysRecyclerAdapter daysAdapter;

    private WeatherContract.Model weatherModel;
    private List<List<com.arash.home.greenweather.models.open_weather_model.List>> segregatedWeatherForDays;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_home);
        ButterKnife.bind(this);

        BlurMaskFilter blurFilter = new BlurMaskFilter(tvCityName.getTextSize() / 10, BlurMaskFilter.Blur.SOLID);
        tvCityName.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        tvCityName.getPaint().setMaskFilter(blurFilter);

        weatherModel = new WeatherAPIModel();
        weatherModel.getWeatherDetails(this, "Tehran", BasicConfigs.METRIC);

    }


    @Override
    public void onFinished(WeatherModel weather) {

        tvCityName.setText(weather.getCity().getName());
        String todayTemp = String.format(Locale.getDefault(), "%.0f %s", weather.getList().get(0).getMain().getTemp(), BasicConfigs.METRIC_SYMBOL);
        tvTodaysTemp.setText(todayTemp);

        int daysCount = weather.getList().size() / 8;
        int tempsCountInEachDay = weather.getList().size() / 5;

        segregatedWeatherForDays = new ArrayList<>();
        for (int i = 0; i < daysCount; i++) {
            List<com.arash.home.greenweather.models.open_weather_model.List> eachDay = new ArrayList<>();
            for (int j = i * tempsCountInEachDay; j < (i + 1) * tempsCountInEachDay; j++) {
                eachDay.add(weather.getList().get(j));
            }
            segregatedWeatherForDays.add(eachDay);
        }

        daysAdapter = new DaysRecyclerAdapter(this, segregatedWeatherForDays);
        recyclerForecast.setAdapter(daysAdapter);
        recyclerForecast.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    @Override
    public void onFailure(Throwable t) {

    }
}
