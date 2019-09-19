package com.arash.home.greenweather.weather_contract.weather_views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.arash.home.greenweather.R;
import com.arash.home.greenweather.app_configs.BasicConfigs;
import com.arash.home.greenweather.models.open_weather_model.WeatherModel;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HoursRecyclerAdapter extends RecyclerView.Adapter<HoursRecyclerAdapter.HoursViewHolder> {

    private Context context;
    private java.util.List<com.arash.home.greenweather.models.open_weather_model.List> weatherList;

    public HoursRecyclerAdapter(Context context, java.util.List<com.arash.home.greenweather.models.open_weather_model.List> weatherList) {
        this.context = context;
        this.weatherList = weatherList;
    }

    @NonNull
    @Override
    public HoursViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_items_hours, parent, false);
        HoursViewHolder holder = new HoursViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HoursViewHolder holder, int position) {
        String todayTemp = String.format(Locale.getDefault(), "%.0f %s", weatherList.get(position).getMain().getTemp(), BasicConfigs.METRIC_SYMBOL);
        holder.tvDegree.setText(todayTemp);
        String s = weatherList.get(position).getDtTxt();
        String hour = "" + s.charAt(11) + s.charAt(12);
        int hourNumber = Integer.parseInt(hour);
        String showingHour;
        if (hourNumber == 0) {
            hour = 12 + "am";
        } else if (hourNumber == 12) {
            hour = 12 + "pm";
        } else if (hourNumber > 12) {
            hour = (hourNumber - 12) + "pm";
        } else {
            hour = hourNumber + "am";
        }
        holder.tvHour.setText(hour);
        switch (weatherList.get(position).getWeather().get(0).getId()) {
            case 800:
                Glide.with(context).load(R.drawable.ic_sunny).into(holder.ivWeatherIcon);
            case 804:
                Glide.with(context).load(R.drawable.ic_cloudy).into(holder.ivWeatherIcon);
            case 500:
                Glide.with(context).load(R.drawable.ic_rainy).into(holder.ivWeatherIcon);
            default:
                Glide.with(context).load(R.drawable.ic_sunny_cloudy).into(holder.ivWeatherIcon);
        }
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    class HoursViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_degree)
        AppCompatTextView tvDegree;
        @BindView(R.id.tv_hour)
        AppCompatTextView tvHour;
        @BindView(R.id.iv_weather_icon)
        AppCompatImageView ivWeatherIcon;

        public HoursViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
