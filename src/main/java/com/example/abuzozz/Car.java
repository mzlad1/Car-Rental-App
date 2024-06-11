package com.example.abuzozz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Car {
    int id;
    private String username;
    private String image;
    private String status;
    private int price;
    private String description;
    private String model;
    private String name;

    public Car(String image, String status, int price, String description, String model, String name) {
        this.image = image;
        this.status = status;
        this.price = price;
        this.description = description;
        this.model = model;
        this.name = name;
    }

    public Car(String name, String model, int price, String status , String image , int id, String username) {
        this.status = status;
        this.price = price;
        this.model = model;
        this.name = name;
        this.image = image;
        this.id = id;
        this.username = username;
    }

    public Car(String status ,String model,String name , String image) {
        this.status = status;
        this.model = model;
        this.name = name;
        this.image = image;
    }


    //getters and setters for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Car{" +
                "img='" + image + '\'' +
                ", status='" + status + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", model='" + model + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}