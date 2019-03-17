package com.arash.home.greenweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cz.msebera.android.httpclient.Header;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.arash.home.greenweather.openweathermap_api_pojos.Day;
import com.arash.home.greenweather.openweathermap_api_pojos.WeatherAPI;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView todaysTemp = findViewById(R.id.todaysTemp);

        String cityName = "Tehran";
        final ArrayList<String> weekDaysTemp = new ArrayList<>();

        final String openWeatherMapAPIURL = "https://api.openweathermap.org/data/2.5/find?q=London&units=metric&appid=a66afa073601d5015bd14559a262fff5&units=metric";

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//
//                try {
//                    URL obj = new URL(openWeatherMapAPIURL);
//                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//                    con.setRequestMethod("GET");
//                    con.setRequestProperty("User-Agent", "Mozilla/5.0");
//                    int responseCode = con.getResponseCode();
//                    if (responseCode == HttpURLConnection.HTTP_OK) {
//                        BufferedReader in = new BufferedReader(new InputStreamReader(
//                                con.getInputStream()));
//                        String inputLine;
//                        StringBuffer response = new StringBuffer();
//                        while ((inputLine = in.readLine()) != null) {
//                            response.append(inputLine);
//                        }
////
//                        System.out.println(response.toString());
//
////                String query = response.toString();
////                JSONObject jsonObject = new JSONObject(query);
////
////                String query1 = jsonObject.getString("list");
////                String[] theList = new String[5];
////                JSONArray jsonArray = new JSONArray(query1);
////                for (int i = 0; i < jsonArray.length(); i++) {
////                    JSONObject jsonobject = jsonArray.getJSONObject(i);
////                    theList[i] = jsonobject.getString("main");
////                }
////
////                JSONObject jsonObject1 = new JSONObject(theList[0]);
////                String query2 = jsonObject1.getString("temp");
////
////                todaysTemp.setText(query2);
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//----------------------------------------------------------------------------------------------------------------------------
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(openWeatherMapAPIURL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                String json = response.toString();
//                String jsonArrayList;
//                try {
//                    JSONObject jObj = new JSONObject(json);
//                    jsonArrayList = jObj.getString("list");
//                    System.out.println(jsonArrayList);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }


                Gson gson = new Gson();
                WeatherAPI weatherAPI = gson.fromJson(json, WeatherAPI.class);

                List<Day> day = weatherAPI.getDay();
                String todayTemp = String.format(Locale.getDefault(), "%.1f °C", day.get(0).getMain().getTemp());
                todaysTemp.setText(todayTemp);

                for (int i = 0; i < day.size(); i++) {
                    String weekDayTemp = String.format(Locale.getDefault(), "%.1f °C", day.get(i).getMain().getTemp());
                    weekDaysTemp.add(weekDayTemp);
                }

//------------------------------------------------------------------------------------------------
//                try {
//                    String query = response.toString();
//                    JSONObject jsonObject = new JSONObject(query);
//
//                    String query1 = jsonObject.getString("list");
//                    String[] theList = new String[5];
//                    JSONArray jsonArray = new JSONArray(query1);
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonobject = jsonArray.getJSONObject(i);
//                        theList[i] = jsonobject.getString("main");
//                    }
//
//                    JSONObject jsonObject1 = new JSONObject(theList[0]);
//                    String query2 = jsonObject1.getString("temp");
//
//                todaysTemp.setText(query2 + "°C");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
//-----------------------------------------------------------------------------------------------------------------------------
//        for (int i = 0; i < 9; i++) {
//            weekDaysTemp.add("" + (i + 1));
//        }

        RecyclerView recycler = findViewById(R.id.recycler_forecast);
        ForecastRecyclerAdaptor adaptor = new ForecastRecyclerAdaptor(weekDaysTemp);
        recycler.setAdapter(adaptor);
        recycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
    }
}
