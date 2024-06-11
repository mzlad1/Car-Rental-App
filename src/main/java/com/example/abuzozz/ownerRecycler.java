package com.example.abuzozz;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;


public class ownerRecycler extends RecyclerView.Adapter<ownerRecycler.ViewHolder> {
    //   private List<Car> carsList;
    private String[] image;
    private String[] status;
    private int[] price;
    //  private String description;
    private String[] model;
    private String[] name;
    private int[] id;
    int ids;
    String username2;

    private String[] username;

    public ownerRecycler(String[] status, int[] price, String[] model, String[] name , String[] image,int[] id,String[] username) {
        this.status = status;
        this.model = model;
        this.name = name;
        this.price = price;
        this.image = image;
        this.id = id;
        this.username = username;
    }


    public ownerRecycler(String[] status,  String[] model, String[] name , String[] image,int[] id) {
        this.status = status;
        this.model = model;
        this.name = name;
        this.price = price;
        this.image = image;
        this.id = id;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewowner,
                parent,
                false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardView cardView = (CardView) holder.cardView;
        ImageView imageView = cardView.findViewById(R.id.CarImage);
        TextView names = cardView.findViewById(R.id.carname);
        TextView models = cardView.findViewById(R.id.carmodel);
        TextView prices = cardView.findViewById(R.id.carprice);
        TextView statuse = cardView.findViewById(R.id.carstatus);
        Button button = cardView.findViewById(R.id.deleteButton);
        Button button2 = cardView.findViewById(R.id.updateButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                ids = id[position];
                username2 = username[position];
                Intent intent = new Intent(cardView.getContext(), deleteCar.class);
                intent.putExtra("id", ids);
                intent.putExtra("username", username2);
                cardView.getContext().startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                ids = id[position];
                username2 = username[position];
                Intent intent = new Intent(cardView.getContext(), UpdateCarOwner.class);
                intent.putExtra("id", ids);
                intent.putExtra("username", username2);
                cardView.getContext().startActivity(intent);
            }
        });



        names.setText("Name: " + name[position]);
        models.setText("Model: " + model[position]);
        prices.setText("Price: " + price[position]);
        if(status[position].equals("0")){
            statuse.setText("Status: Available");
        }else {
            statuse.setText("Status: Not Available");
        }
        // statuse.setText("Status: " + status[position]);
        Glide.with(cardView.getContext()).load(image[position]).into(imageView);
    }


    @Override
    public int getItemCount() {
        return model.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        public ViewHolder(View cardView) {
            super(cardView);
            this.cardView = (CardView) cardView;
        }
    }
}