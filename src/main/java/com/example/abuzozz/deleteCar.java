package com.example.abuzozz;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class deleteCar extends AppCompatActivity {

    String username;
    int carID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_delete_car);
        Intent intent = getIntent();
        carID = intent.getIntExtra("id", 0);
        Toast.makeText(getApplicationContext(), "Car ID: " + carID, Toast.LENGTH_LONG).show();
        username = intent.getStringExtra("username");
        showConfirmationDialog(carID);
    }

    private void showConfirmationDialog(int carID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Car");
        builder.setMessage("Are you sure you want to delete this car?");
        builder.setPositiveButton("Yes", (dialog, which) -> deleteCar(carID));
        builder.setNegativeButton("No", (dialog, which) -> {
            finish();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void deleteCar(int carID) {
        String url = "http://10.0.2.2:80/FinalProjectPHP/deleteCar.php?id=" + carID;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(deleteCar.this, mainPageOwners.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
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
