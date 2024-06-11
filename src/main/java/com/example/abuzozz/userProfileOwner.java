package com.example.abuzozz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class userProfileOwner extends AppCompatActivity {

    String username1;
    TextView username;
    TextView email;
    TextView phone;
    TextView address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_user_profile_owner);
        username1 = getIntent().getStringExtra("username");
        setup();
        new FetchOwnerInfoTask().execute(username1);
    }

    public void setup() {
        username = findViewById(R.id.tUsername);
        email = findViewById(R.id.tEmail);
        phone = findViewById(R.id.tPhone);
        address = findViewById(R.id.tAddress);
    }

    private class FetchOwnerInfoTask extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... params) {
            String username = params[0];
            String urlString = "http://10.0.2.2:80/FinalProjectPHP/userProfileOwner.php?username=" + username;
            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream in = urlConnection.getInputStream();
                Scanner scanner = new Scanner(in);
                StringBuilder response = new StringBuilder();
                while (scanner.hasNext()) {
                    response.append(scanner.nextLine());
                }
                scanner.close();
                return new JSONObject(response.toString());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject response) {
            if (response != null) {
                try {
                    username.setText("Username: " + username1);
                    email.setText("Email: " + response.getString("email"));
                    phone.setText("Phone: " + response.getString("phone"));
                    address.setText("Address: " + response.getString("address"));
                } catch (Exception e) {
                    Toast.makeText(userProfileOwner.this, "Failed to parse data!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(userProfileOwner.this, "Error fetching user info!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "Landscape mode", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "Portrait mode", Toast.LENGTH_SHORT).show();
        }
    }
}
