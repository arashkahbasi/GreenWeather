package com.arash.home.greenweather.weather_contract.weather_views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.BlurMaskFilter;
import android.os.Bundle;
import android.view.View;

import com.arash.home.greenweather.R;
import com.arash.home.greenweather.app_configs.AppController;
import com.arash.home.greenweather.app_configs.BasicConfigs;
import com.arash.home.greenweather.models.open_weather_model.WeatherModel;
import com.arash.home.greenweather.weather_contract.ChooseCityFragment;
import com.arash.home.greenweather.weather_contract.WeatherAPIModel;
import com.arash.home.greenweather.weather_contract.WeatherContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WeatherHomeActivity extends AppCompatActivity implements WeatherContract.View, WeatherContract.Model.OnFinishedListener,
        ChooseCityFragment.CityUnitChooser {

    private String unit;
    public static String unitSymbol;
    private String cityName;

    @BindView(R.id.tv_city_name)
    AppCompatTextView tvCityName;
    @BindView(R.id.tv_todays_temp)
    AppCompatTextView tvTodaysTemp;

    @BindView(R.id.recycler_days)
    RecyclerView recyclerForecast;
    private DaysRecyclerAdapter daysAdapter;

    private WeatherContract.Model weatherModel;
    private List<List<com.arash.home.greenweather.models.open_weather_model.List>> segregatedWeatherForDays;

    @OnClick(R.id.tv_city_name)
    void chooseCity() {
        ChooseCityFragment chooseCityFragment = new ChooseCityFragment(this);
        getSupportFragmentManager().beginTransaction().add(chooseCityFragment, "chooseCity").commit();
    }

    @OnClick(R.id.tv_todays_temp)
    void chooseUnit() {
        changeUnit();
        weatherModel.getWeatherDetails(this, cityName, unit);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_home);
        ButterKnife.bind(this);

        if (AppController.getCityName() != null)
            cityName = AppController.getCityName();
        else
            cityName = "Tehran";

        if (AppController.getUnits() != null)
            unit = AppController.getUnits();
        else
            unit = BasicConfigs.METRIC;

        if (unit.equals(BasicConfigs.METRIC))
            unitSymbol = BasicConfigs.METRIC_SYMBOL;
        else
            unitSymbol = BasicConfigs.IMPERIAL_SYMBOL;

        BlurMaskFilter blurFilter = new BlurMaskFilter(tvCityName.getTextSize() / 10, BlurMaskFilter.Blur.SOLID);
        tvCityName.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        tvCityName.getPaint().setMaskFilter(blurFilter);

        weatherModel = new WeatherAPIModel();
        weatherModel.getWeatherDetails(this, cityName, unit);

    }


    @Override
    public void onFinished(WeatherModel weather) {

        tvCityName.setText(weather.getCity().getName());
        String todayTemp = String.format(Locale.getDefault(), "%.0f%s", weather.getList().get(0).getMain().getTemp(), unitSymbol);
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

    @Override
    public void getCityName(String cityName) {
        weatherModel.getWeatherDetails(this, cityName, unit);
        AppController.setCityName(cityName);
    }

    private void changeUnit() {
        if (unit == BasicConfigs.METRIC) {
            unit = BasicConfigs.IMPERIAL;
            unitSymbol = BasicConfigs.IMPERIAL_SYMBOL;
        } else {
            unit = BasicConfigs.METRIC;
            unitSymbol = BasicConfigs.METRIC_SYMBOL;
        }

    }
}
