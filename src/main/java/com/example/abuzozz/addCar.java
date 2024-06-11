package com.example.abuzozz;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class addCar extends AppCompatActivity {
    private EditText name, model, price, description;
    String ownerName = "";
    String  encodedImage="";
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_car);
        setup();
        Intent intent = getIntent();
        ownerName = intent.getStringExtra("username");

    }

    public void setup() {
        name = findViewById(R.id.name);
        model = findViewById(R.id.model);
        price = findViewById(R.id.price);
        description = findViewById(R.id.descr);
        imageView = findViewById(R.id.CarImageadded);
    }

    public void sendCarDetails(String name, String model, int price, String description, String imageEncoded ,String ownerName) {
        String url = "http://10.0.2.2:80/FinalProjectPHP/addCar.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(addCar.this, mainPageOwners.class);
            intent.putExtra("username", ownerName);
            startActivity(intent);
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
                params.put("image",imageEncoded);
                params.put("username", ownerName);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }


    public void selectImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);


                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                byte[] imageBytes = outputStream.toByteArray();
                encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                Toast.makeText(this, "Failed to load image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
    public void addCar(View view) {
        String carName = name.getText().toString();
        String carModel = model.getText().toString();
        int carPrice = Integer.parseInt(price.getText().toString());
        String carDescription = description.getText().toString();
        if (carName.isEmpty() || carModel.isEmpty() || carDescription.isEmpty() || carPrice <= 0) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (encodedImage.isEmpty()) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            return;
        }
        sendCarDetails(carName, carModel, carPrice, carDescription, encodedImage ,ownerName);
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