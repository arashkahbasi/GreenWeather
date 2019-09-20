package com.arash.home.greenweather.weather_contract.weather_views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arash.home.greenweather.R;
import com.arash.home.greenweather.models.open_weather_model.List;
import com.arash.home.greenweather.models.open_weather_model.WeatherModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DaysRecyclerAdapter extends RecyclerView.Adapter<DaysRecyclerAdapter.DaysViewHolder> {

    private Context context;
    private java.util.List<java.util.List<List>> segregatedWeatherForDays;

    public DaysRecyclerAdapter(Context context, java.util.List<java.util.List<List>> segregatedWeatherForDays) {
        this.context = context;
        this.segregatedWeatherForDays = segregatedWeatherForDays;
    }

    @NonNull
    @Override
    public DaysViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_items_days, parent, false);
        DaysViewHolder holder = new DaysViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DaysViewHolder holder, int position) {

        holder.txtDaysName.setText(new SimpleDateFormat("EE", Locale.US).format(new Date(((Calendar.getInstance().getTime()).getTime() + (position * 86400000)))));

        HoursRecyclerAdapter hoursAdapter = new HoursRecyclerAdapter(context, segregatedWeatherForDays.get(position));
        holder.recyclerHours.setAdapter(hoursAdapter);
        holder.recyclerHours.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL,false));
    }

    @Override
    public int getItemCount() {
        return segregatedWeatherForDays.size();
    }

    class DaysViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_days_name)
        AppCompatTextView txtDaysName;
        @BindView(R.id.recycler_hours)
        RecyclerView recyclerHours;

        public DaysViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
