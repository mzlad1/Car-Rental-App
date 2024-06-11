package com.example.abuzozz;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;

public class userCars extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private SearchView searchView;

    String username;

    public List<Car> cars ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_user_cars);
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterlist(newText);
                return true;
            }
        });

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        recyclerView = findViewById(R.id.car_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        requestQueue = Volley.newRequestQueue(this);
        cars = new ArrayList<>();
        fetchCars();
    }

    private void fetchCars() {
        String url = "http://10.0.2.2:80/FinalProjectPHP/userCars.php";

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
                                String status = jsonObject.getString("status");
                                String image = jsonObject.getString("img");
                                String id = jsonObject.getString("id");

                                Car car = new Car(name, model,Integer.parseInt(price),status ,image,Integer.parseInt(id),username);
                                cars.add(car);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        fetchRecycler();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    private void fetchRecycler() {
        String[] usernames = new String[cars.size()];
        String[] status = new String[cars.size()];
        int[] price = new int[cars.size()];
        String[] model = new String[cars.size()];
        String[] name = new String[cars.size()];
        String[] image = new String[cars.size()];
        int[] id = new int[cars.size()];
        for (int i = 0; i < cars.size(); i++) {
            status[i] = cars.get(i).getStatus();
            price[i] = cars.get(i).getPrice();
            model[i] = cars.get(i).getModel();
            name[i] = cars.get(i).getName();
            image[i] = cars.get(i).getImage();
            id[i] = cars.get(i).getId();
            usernames[i] = cars.get(i).getUsername();

        }
        for (int i = 0; i < cars.size(); i++) {
            System.out.println("Status : " + status[i]);
            System.out.println("Price :" + price[i]);
            System.out.println("Modle : "+model[i]);
            System.out.println("Name :"+name[i]);
            System.out.println("Image :"+image[i]);
            System.out.println("ID :"+id[i]);
            System.out.println("Username :"+usernames[i]);
        }

        carRecyclerView carRecyclerView = new carRecyclerView(status, price, model, name, image, id, usernames);
        recyclerView.setAdapter(carRecyclerView);

    }

    private void filterlist(String newText) {
        List<Car> filteredList = new ArrayList<>();
        for (Car car : cars) {
            if (car.getName().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(car);
            }
        }

        if(filteredList.isEmpty())
        {
            Toast.makeText(this, "No cars found", Toast.LENGTH_SHORT).show();
        }else{
            String[] status = new String[filteredList.size()];
            String[] model = new String[filteredList.size()];
            String[] name = new String[filteredList.size()];
            String[] image = new String[filteredList.size()];
            int[] id = new int[filteredList.size()];
            int[] price = new int[filteredList.size()];
            String[] usernames = new String[filteredList.size()];
            for (int i = 0; i < filteredList.size(); i++) {
                status[i] = filteredList.get(i).getStatus();
                model[i] = filteredList.get(i).getModel();
                name[i] = filteredList.get(i).getName();
                image[i] = filteredList.get(i).getImage();
                id[i] = filteredList.get(i).getId();
                price[i] = filteredList.get(i).getPrice();
                usernames[i] = filteredList.get(i).getUsername();
            }
            carRecyclerView carRecyclerView = new carRecyclerView(status, price, model, name, image, id, usernames);
            recyclerView.setAdapter(carRecyclerView);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Handle changes here
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "Landscape mode", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "Portrait mode", Toast.LENGTH_SHORT).show();
        }
    }
}
