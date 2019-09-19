package com.arash.home.greenweather.weather_contract;

import com.arash.home.greenweather.models.open_weather_model.WeatherModel;

public interface WeatherContract {
    interface Model {

        interface OnFinishedListener{
            void onFinished(WeatherModel weather);
            void onFailure(Throwable t);
        }

        void getWeatherDetails(OnFinishedListener onFinishedListener, String city, String unit);
    }

    interface View {
//        void showProgressbar();
//        void hideProgressbar();

//        void setDataToViews(WeatherModel weather);

//        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestWeatherData(String city, String unit);
    }
}
