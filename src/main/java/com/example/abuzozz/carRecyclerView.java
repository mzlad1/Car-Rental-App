package com.example.abuzozz;


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

import com.bumptech.glide.Glide;


public class carRecyclerView extends RecyclerView.Adapter<carRecyclerView.ViewHolder> {
    //   private List<Car> carsList;
    private String[] image;
    private String[] status;
    private int[] price;
    //  private String description;
    private String[] model;
    private String[] name;
    private int[] id;

    private String[] username;



    public carRecyclerView(String[] status, int[] price, String[] model, String[] name , String[] image,int[] id,String[] username) {
        this.status = status;
        this.model = model;
        this.name = name;
        this.price = price;
        this.image = image;
        this.id = id;
        this.username = username;
    }


    public carRecyclerView(String[] status,  String[] model, String[] name , String[] image,int[] id) {
        this.status = status;
        this.model = model;
        this.name = name;
        this.price = price;
        this.image = image;
        this.id = id;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,
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

        Button button = cardView.findViewById(R.id.rentButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                int ids = id[position];
                String username2 = username[position];

                Intent intent = new Intent(cardView.getContext(), carDetails.class);
                intent.putExtra("id", ids);
                intent.putExtra("username", username[position]);

                System.out.println("Teeeeeeeeeeest : " +username2);
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