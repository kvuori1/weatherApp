package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity  {
    private String mUrl = "https://api.openweathermap.org/data/2.5/weather?q=tampere&units=metric&appid=2aa4f3023b4f7a40a5fd2c9f19c92a82";
    private RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQueue = Volley.newRequestQueue(this);
    }

    public void getWeatherData(View view) {
        //haetaan JSON
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, mUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                        parseJsonAndUpdateUI(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        //lisätään json jonooon
        mQueue.add(jsonObjectRequest);
    }

    private void parseJsonAndUpdateUI (JSONObject weatherObject) {
        TextView temeratureTextView = (TextView) findViewById(R.id.cityTemperature);
        TextView weatherTypeTextView = (TextView) findViewById(R.id.cityWeather);
        TextView windSpeedTextView = (TextView) findViewById(R.id.cityWindSpeed);

        //haetaan ja pisteään lämpötila Json:ista
        try {
            double temperature = weatherObject.getJSONObject("main").getDouble("temp");
            temeratureTextView.setText("" + temperature + "C");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //haetaan ja pistetään sää tyyppi Json:ista
        try {
            String weatherType = weatherObject.getJSONArray("weather").getJSONObject(0).getString("description");
            weatherTypeTextView.setText("" + weatherType );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //haetaan ja pistetään tuulen nopeus Json:ista
        try {
            Double windSpeed = weatherObject.getJSONObject("wind").getDouble("speed");
            windSpeedTextView.setText("" + windSpeed + "m/s");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}