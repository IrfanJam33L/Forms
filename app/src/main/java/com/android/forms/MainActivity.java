package com.android.forms;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {
    final int REQUEST_CODE=123;
    Button infoactiv;
    Button OpenData;
    DatabaseHelper myDb;
    TextView Temprature;
    TextView Weather;
    TextView City;


    String weather_url = "http://api.openweathermap.org/data/2.5/weather?";
    String App_id = "f491932a955536bc59a504c3f74d71ca";
    long minTime = 5000;
    long minDistance = 10000;

    String LOCATION_PROVIDER = LocationManager.NETWORK_PROVIDER;

    LocationManager mLocationManager;
    LocationListener mLocationListener;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Temprature = (TextView) findViewById(R.id.Temprature);
        Weather = (TextView) findViewById(R.id.Weather);
        City =(TextView) findViewById(R.id.City);

        final Context context = this;
        myDb = new DatabaseHelper(this);


        infoactiv = findViewById(R.id.Inputinfo);

        infoactiv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, InputInfo.class);
                startActivity(i);
            }
        });

        OpenData = findViewById(R.id.button2);

        OpenData.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDb.getAllData();
                if (res.getCount() == 0) {
                    // show message
                    showMessage("Error", "Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("First Name :" + res.getString(0) + "\n");
                    buffer.append("Last Name :" + res.getString(1) + "\n");
                    buffer.append("Email :" + res.getString(2) + "\n");
                    buffer.append("Phone No. :" + res.getString(3) + "\n");
                    buffer.append("Address :" + res.getString(4) + "\n\n");
                }

                // Show all data
                showMessage("Data", buffer.toString());

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Form", "OnResumeCalled");
        Log.d("Form", "Getting Temp of Location");
        getTempforCurrentLocation();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getTempforCurrentLocation() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("Form", "onLocationChanged() Calledback recieved");
                String Longitute =String.valueOf(location.getLongitude());
                String Latitute =String.valueOf(location.getLatitude());

                Log.d("Form", "Longitude is="+Longitute);
                Log.d("Form", "Latitude is="+Latitute);

                RequestParams params = new RequestParams();
                params.put("lat", Latitute);
                params.put("lon", Longitute);
                params.put("appid", App_id);
                openWeatherCall(params);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Log.d("Form", "onProviderDisable() Calledback Recieved");

            }
        };

        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);
            return;
        }
        mLocationManager.requestLocationUpdates(LOCATION_PROVIDER, minTime, minDistance, mLocationListener);


    }

    private void openWeatherCall(RequestParams params) {

        AsyncHttpClient client= new AsyncHttpClient();

        client.get(weather_url, params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                Log.d("Form", "Success!" + response.toString());
                 WeatherDataModel weatherData = WeatherDataModel.fromJson(response);
                 updateUI(weatherData);

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response){
                Log.e("Form", "Fail " +e.toString());
                Log.d("Form","Failure Code " +statusCode);
                Toast.makeText(MainActivity.this, "TempRequest Failed",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if(requestCode==REQUEST_CODE)
        {
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    Log.d("Form","Location Request Granted");
                    getTempforCurrentLocation();
                }
                else {
                    Log.d("Form","Permissions Denied =(");
                }
        }
    }
    public void updateUI(WeatherDataModel weather){

        City.setText(weather.getmCity());
        Weather.setText(weather.getmWeather());
        Temprature.setText(weather.getmTemprature());


    }

    @Override
    public void onBackPressed(){
        Intent i= new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}