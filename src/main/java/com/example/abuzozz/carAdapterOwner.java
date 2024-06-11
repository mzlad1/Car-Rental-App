package com.example.abuzozz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class carAdapterOwner extends ArrayAdapter<CarRented> {

    private Context context;
    private List<CarRented> carList;

    public carAdapterOwner(Context context, List<CarRented> carList) {
        super(context, 0, carList);
        this.context = context;
        this.carList = carList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.carrentedlistowner, parent, false);
        }

        CarRented car = getItem(position);


        TextView cName = convertView.findViewById(R.id.coName);
        TextView cModel = convertView.findViewById(R.id.coModel);
        TextView cRentalDate = convertView.findViewById(R.id.coDate);
        TextView cPrice = convertView.findViewById(R.id.coPrice);
        ImageView cImage = convertView.findViewById(R.id.coImage);


        cName.setText("Car Name : "+car.getName());
        cModel.setText("Car Model : "+car.getModel());
        cRentalDate.setText("Car Rent Date : "+car.getRentalDate());
        cPrice.setText("Car Price : "+String.valueOf(car.getPrice()));
        Glide.with(context).load(car.getImage()).into(cImage);



        return convertView;
    }

}
