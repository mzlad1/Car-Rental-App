package com.example.abuzozz;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class rentedCarsUser extends AppCompatActivity {
    public interface ImageCallback {
        void onSuccess(String imagePath);
        void onError(String error);
    }

    ListView listView;
    ArrayList<CarRented> carList;
    CarAdapter adapter;
    String username1;
    private RequestQueue requestQueue;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_rented_cars_user);
        username1 = getIntent().getStringExtra("username");
        listView = findViewById(R.id.ListView);
        carList = new ArrayList<>();
        adapter = new CarAdapter(this, carList);
        listView.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(this);
        fetchRentedCars(username1);
    }

    public void fetchRentedCars(String username1) {
        String url = "http://10.0.2.2:80/FinalProjectPHP/rentedCarsUser.php?username=" + username1;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String name = jsonObject.getString("name");
                                String model = jsonObject.getString("model");
                                String price = jsonObject.getString("price");
                                String id = jsonObject.getString("carID");
                                String rentalDate = jsonObject.getString("date");

                                int finalId = Integer.parseInt(id);
                                getCarImagePath(finalId, new rentedCarsUser.ImageCallback() {
                                    @Override
                                    public void onSuccess(String imagePath) {
                                        CarRented car = new CarRented(finalId, name, model, Integer.parseInt(price), rentalDate, imagePath);
                                        System.out.println("Car: " + car.toString());
                                        carList.add(car);
                                        runOnUiThread(() -> adapter.notifyDataSetChanged());
                                    }

                                    @Override
                                    public void onError(String error) {
                                        Log.e("ImageLoadError", "Error loading image for car " + finalId + ": " + error);
                                    }
                                });
                            }
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

        requestQueue.add(jsonArrayRequest);
    }

    public void getCarImagePath(int carId, ImageCallback callback) {
        String url = "http://10.0.2.2:80/FinalProjectPHP/getCarImage.php?id=" + carId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("img")) {
                                String imageUrl = response.getString("img");
                                callback.onSuccess(imageUrl);
                            } else if (response.has("error")) {
                                callback.onError(response.getString("error"));
                            } else {
                                callback.onError("Empty response");
                            }
                        } catch (Exception e) {
                            callback.onError("Failed to parse image URL");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError("Network error: " + error.toString());
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
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