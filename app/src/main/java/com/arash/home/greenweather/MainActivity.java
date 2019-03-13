package com.arash.home.greenweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> weekDaysTemp = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            weekDaysTemp.add("" + i + "°C");
        }

        RecyclerView recycler = findViewById(R.id.recycler_forecast);
        ForecastRecyclerAdaptor adaptor = new ForecastRecyclerAdaptor(weekDaysTemp);
        recycler.setAdapter(adaptor);
        recycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
    }
}
