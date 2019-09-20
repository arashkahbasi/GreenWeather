package com.arash.home.greenweather.weather_contract;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.DialogFragment;

import com.arash.home.greenweather.R;

public class ChooseCityFragment extends DialogFragment {

    CityUnitChooser cityUnitChooser;

    AppCompatEditText etCity;
    AppCompatButton btnOk;

    public ChooseCityFragment(CityUnitChooser cityUnitChooser) {
        this.cityUnitChooser = cityUnitChooser;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_city, container, false);
        etCity = view.findViewById(R.id.et_city_select);
        btnOk = view.findViewById(R.id.btn_ok);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etCity.getText().toString() != ""){
                    cityUnitChooser.getCityName(etCity.getText().toString());
                    ChooseCityFragment.this.dismiss();
                }else{
                    ChooseCityFragment.this.dismiss();
                }
            }
        });
        return view;
    }

    public interface CityUnitChooser {
        void getCityName(String cityName);
    }
}
