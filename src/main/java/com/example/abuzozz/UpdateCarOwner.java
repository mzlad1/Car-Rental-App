package com.example.abuzozz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UpdateCarOwner extends AppCompatActivity {
    String username;
    int id;
    EditText model, name, price, description;



    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_update_car_owner);
        username = getIntent().getStringExtra("username");
        id = getIntent().getIntExtra("id", 0);
        setupViews();


    }

    private void setupViews() {
        model = findViewById(R.id.Updatemodel);
        name = findViewById(R.id.Updatename);
        price = findViewById(R.id.Updateprice);
        description = findViewById(R.id.Updatedescr);

        requestQueue = Volley.newRequestQueue(this);
        getCarDetails(id);
    }


    public void getCarDetails(int id) {
        String url = "http://10.0.2.2:80/FinalProjectPHP/carDetails.php?id=" + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            name.setText(response.getString("name"));
                            model.setText(response.getString("model"));
                            price.setText(response.getString("price"));
                            description.setText(response.getString("description"));

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



    public void sendCarDetails(String name, String model, int price, String description,int ids) {
        String url = "http://10.0.2.2:80/FinalProjectPHP/UpdateCars.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
        }, error -> {
            Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("model", model);
                params.put("price", String.valueOf(price));
                params.put("description", description);

                params.put("id", String.valueOf(ids));
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }



    public void updateCar(View view) {
        String carName = name.getText().toString();
        String carModel = model.getText().toString();
        int carPrice = Integer.parseInt(price.getText().toString());
        String carDescription = description.getText().toString();
        if (carName.isEmpty() || carModel.isEmpty() || carDescription.isEmpty() || carPrice <= 0) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        sendCarDetails(carName, carModel, carPrice, carDescription, id);

        Intent intent = new Intent(UpdateCarOwner.this, ownerCars.class);
        intent.putExtra("username", username);
        startActivity(intent);
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