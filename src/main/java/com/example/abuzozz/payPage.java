package com.example.abuzozz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class payPage extends AppCompatActivity {

    int id;
    String name;
    String model;
    String price;
    String date;
    String username;
    EditText cardNumber;
    EditText cvv;
    EditText expDate;
    EditText cardName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_pay_page);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        id = intent.getIntExtra("id",0);
        name = intent.getStringExtra("name");
        model = intent.getStringExtra("model");
        price = intent.getStringExtra("price");
        date = intent.getStringExtra("date");
        setup();


    }

    public void setup(){
        cardNumber = findViewById(R.id.cardNumber);
        cvv = findViewById(R.id.cvv);
        expDate = findViewById(R.id.card_expiry_date);
        cardName = findViewById(R.id.cardName);
    }

    public void pay(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Payment Confirmation");
        builder.setMessage("Are you sure you want to pay?");
        builder.setPositiveButton("Yes", (dialog, which) -> payRent(view));
        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void payRent(View view){
        if (cardNumber.getText().toString().isEmpty() || cvv.getText().toString().isEmpty() || expDate.getText().toString().isEmpty() || cardName.getText().toString().isEmpty()){
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show();
                return;
            }else{
            String url = "http://10.0.2.2:80/FinalProjectPHP/carRent.php";
            RequestQueue queue = Volley.newRequestQueue(this);

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("model", model);
                    params.put("name", name);
                    params.put("price", price);
                    params.put("date", date);
                    params.put("carID", String.valueOf(id));
                    return params;
                }
            };
            queue.add(postRequest);
        }

        Intent intent = new Intent(payPage.this, MainPageUser.class);
        intent.putExtra("username", username);
        startActivity(intent);
        }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cancel Payment");
        builder.setMessage("Are you sure you want to cancel?");
        builder.setPositiveButton("Yes", (dialog, which) -> finish());
        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onStop() {
        super.onStop();
        finish();
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