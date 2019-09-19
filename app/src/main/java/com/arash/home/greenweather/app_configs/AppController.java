package com.arash.home.greenweather.app_configs;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.orhanobut.hawk.Hawk;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppController extends CoreAppController {

    private Context mContext;
    private static Retrofit mRetrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Hawk.init(mContext).build();
    }

    public static Retrofit getClient(String serverAddress) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(serverAddress)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }

        return mRetrofit;
    }

    public static void setLocale(String lang, Context context) {

        Locale myLocale = new Locale(lang);

        Locale.setDefault(myLocale);
        Configuration config = new Configuration();
        config.locale = myLocale;
        Resources resources = context.getResources();
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    public static void setCityName(int cityName) {
        Hawk.put(BasicConfigs.CITY, cityName);
    }
    public static int getCityName() {
        return Hawk.get(BasicConfigs.CITY);
    }

    public static void setUnits(String unit) {
        Hawk.put(BasicConfigs.UNIT, unit);
    }
    public static String getUnits() {
        return Hawk.get(BasicConfigs.UNIT);
    }

}
