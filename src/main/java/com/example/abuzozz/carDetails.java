package com.example.abuzozz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

public class carDetails extends AppCompatActivity {
    int id;
    TextView name;
    TextView model;
    TextView status;
    TextView price;
    TextView description;
    ImageView image;
    DatePicker datePicker;

    String name1;
    String model1;
    String price1;
    String username;

    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_car_details);
        setup();
        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
        username = intent.getStringExtra("username");
        requestQueue = Volley.newRequestQueue(this);
        getCarDetails(id);
    }

    public void setup(){
        name = findViewById(R.id.carnameDetails);
        model = findViewById(R.id.carmodelDetails);
        price = findViewById(R.id.carpriceDetails);
        description = findViewById(R.id.cardescr);
        image = findViewById(R.id.CarImageDetails);
        datePicker = findViewById(R.id.datepicker);
    }

    public void getCarDetails(int id) {
        String url = "http://10.0.2.2:80/FinalProjectPHP/carDetails.php?id=" + id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            name1 = response.getString("name");
                            model1 = response.getString("model");
                            price1 = response.getString("price");
                            name.setText("Car Name: "+name1);
                            model.setText("Car Model: "+model1);
                            price.setText("Price Per Day: "+price1);
                            description.setText("Description: "+response.getString("description"));
                            String imageUrl = response.getString("img");
                            loadImageFromUrl(imageUrl);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
    private void loadImageFromUrl(String url) {
        Glide.with(this)
                .load(url)
                .into(image);
    }


    public void rentCar(View view) {
        checkDate(view);
}

    public void checkDate(View view) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        String date2 = year + "-" + month + "-" + day;

        String url = "http://10.0.2.2:80/FinalProjectPHP/dateCheck.php?carID=" + id + "&date=" + date2;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String res = response.trim();
                        if (res.equals("booked")) {
                            Toast.makeText(carDetails.this, "This date is already booked!", Toast.LENGTH_LONG).show();
                        } else if(res.equals("not booked")) {
                            Intent intent = new Intent(carDetails.this, payPage.class);
                            intent.putExtra("username", username);
                            intent.putExtra("id", id);
                            intent.putExtra("date", date2);
                            intent.putExtra("price", price1);
                            intent.putExtra("name", name1);
                            intent.putExtra("model", model1);
                            startActivity(intent);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(carDetails.this, "Error checking date: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(postRequest);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "Landscape mode", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "Portrait mode", Toast.LENGTH_SHORT).show();
        }
    }
}