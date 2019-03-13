package com.arash.home.greenweather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ForecastRecyclerAdaptor extends RecyclerView.Adapter<ForecastRecyclerAdaptor.ForecastViewHolder> {
    ArrayList<String> weekDayTemp;

    ForecastRecyclerAdaptor(ArrayList<String> weekDayTemp) {
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
        holder.txtView.setText(weekDayTemp.get(position));
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    class ForecastViewHolder extends RecyclerView.ViewHolder {
        TextView txtView;

        public ForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            txtView = itemView.findViewById(R.id.txtTemp);
        }
    }
}
