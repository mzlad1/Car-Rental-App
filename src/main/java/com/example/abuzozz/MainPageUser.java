package com.example.abuzozz;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainPageUser extends AppCompatActivity {
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main_page_user);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
    }

    public void goCars(View view) {

        Intent intent = new Intent(this, userCars.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    public void userInfoUser(View view) {

        Intent intent = new Intent(this, userProfile.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    public void RentedCars(View view) {
        Intent intent = new Intent(this, rentedCarsUser.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    public void signout(View view) {
        Intent intent = new Intent(this, signin.class);
        startActivity(intent);
    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit");
        builder.setMessage("Are you sure you want to exit?");
        Intent intent = new Intent(this, signin.class);
        builder.setPositiveButton("Yes", (dialog, which) -> startActivity(intent));
        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
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