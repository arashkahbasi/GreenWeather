package com.arash.home.greenweather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForecastRecyclerAdapter extends RecyclerView.Adapter<ForecastRecyclerAdapter.ForecastViewHolder> {
    private ArrayList<String> weekDayTemp;

    ForecastRecyclerAdapter(ArrayList<String> weekDayTemp) {
        this.weekDayTemp = weekDayTemp;
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_recycler_items, parent, false);
        ForecastViewHolder holder = new ForecastViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        holder.txtTemp.setText(weekDayTemp.get(position));

        holder.txtDaysName.setText(new SimpleDateFormat("EEEE", Locale.US).format(new Date(((Calendar.getInstance().getTime()).getTime() + (position * 86400000)))));
    }

    @Override
    public int getItemCount() {
        return weekDayTemp.size();
    }

    class ForecastViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_days_name)
        TextView txtTemp;
        @BindView(R.id.tv_days_name)
        TextView txtDaysName;

        public ForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
