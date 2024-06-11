package com.example.abuzozz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class signin extends AppCompatActivity {

    private EditText Password, Email;
    private Button signin;
    private String mail, pass;
    private CheckBox chk;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public static final String NAME = "NAME";
    public static final String PASS = "PASS";
    public static final String FLAG = "FLAG";
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_signin);
        setup();
        setupSharedPrefs();
        checkPrefs();
        showWelcomeDialog();
    }

    private void setup() {
        Password = findViewById(R.id.passwordsignin);
        Email = findViewById(R.id.Emailsginin);
        signin = findViewById(R.id.signin);
        chk = findViewById(R.id.chk);
    }

    public void btnLoginOnClick(View view) {
        String name = Email.getText().toString();
        String password = Password.getText().toString();

        if(chk.isChecked()){
            if(!flag) {
                editor.putString(NAME, name);
                editor.putString(PASS, password);
                editor.putBoolean(FLAG, true);
                editor.commit();
            }

        }

    }
    private void setupSharedPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }

    private void checkPrefs() {
        flag = prefs.getBoolean(FLAG, false);
        if (flag) {
            String name = prefs.getString(NAME, "");
            String password = prefs.getString(PASS, "");
            Email.setText(name);
            Password.setText(password);
            chk.setChecked(true);
        }
    }

    private void showWelcomeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Welcome To 4EVER Car Rental")
                .setCancelable(false)
                .setPositiveButton("Continue", (dialog, id) -> dialog.dismiss());
        AlertDialog welcomeDialog = builder.create();
        welcomeDialog.show();
    }

    public void CheckUser(View view) {
        mail = Email.getText().toString();
        pass = Password.getText().toString();
        if (validate()) {
            new LoginTask().execute(mail, pass);
        }
    }

    private class LoginTask extends AsyncTask<String, Void, String[]> {
        @Override
        protected String[] doInBackground(String... credentials) {
            return credentials;
        }

        @Override
        protected void onPostExecute(String[] result) {
            loginUser(result[0], result[1]);
        }
    }

    private void loginUser(final String username, final String password) {
        String url = "http://10.0.2.2:80/FinalProjectPHP/signin.php";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> handleLoginResponse(response.trim(), username),
                error -> Toast.makeText(signin.this, "Login failed: " + error.toString(), Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        queue.add(postRequest);
    }

    private void handleLoginResponse(String response, String username) {
        if ("owner".equals(response)) {
            navigateToOwnerPage(username);
        } else if ("user".equals(response)) {
            navigateToUserPage(username);
        }
    }

    private void navigateToOwnerPage(String username) {
        Intent intent = new Intent(signin.this, mainPageOwners.class);
        intent.putExtra("username", username);
        Toast.makeText(signin.this, "Welcome " + username, Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    private void navigateToUserPage(String username) {
        Intent intent = new Intent(signin.this, MainPageUser.class);
        intent.putExtra("username", username);
        Toast.makeText(signin.this, "Welcome " + username, Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void SingUp(View view) {
        startActivity(new Intent(signin.this, MainActivity.class));
    }

    public boolean validate() {
        boolean valid = true;
        if (Email.getText().toString().isEmpty()) {
            Email.setError("Email is required");
            valid = false;
        }
        if (Password.getText().toString().isEmpty()) {
            Password.setError("Password is required");
            valid = false;
        }
        return valid;
    }



    public void onBackPressed() {
        super.onDestroy();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit");
        builder.setMessage("Are you sure you want to exit?");
        builder.setPositiveButton("Yes", (dialog, which) -> finish());
        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString("username", mail);
        outState.putString("password", pass);
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
