package com.android.forms;


import org.json.JSONException;
import org.json.JSONObject;

public class WeatherDataModel {
    private String mTemprature;
    private String mCity;
    private String mWeather;

    public static WeatherDataModel fromJson(JSONObject jsonObject)
    {
        try {
        WeatherDataModel weatherData = new WeatherDataModel();

            weatherData.mCity = jsonObject.getString("name");
            weatherData.mWeather= jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");

            double tempresult= jsonObject.getJSONObject("main").getDouble("temp")-273.15;
            int RoundedValue =(int) Math.rint(tempresult);

            weatherData.mTemprature= Integer.toString(RoundedValue);

            return weatherData;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
    public String getmTemprature(){
        return mTemprature + "° c";
    }
    public String getmCity(){
        return mCity;
    }
    public String getmWeather(){
        return mWeather;
    }
}
