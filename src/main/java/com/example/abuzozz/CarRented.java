package com.example.abuzozz;

public class CarRented {
    private int carId;
    private String name;
    private String model;
    int price;
    private String rentalDate;
    String Image;

    public CarRented(int carId, String name, String model, int price,String rentalDate, String Image) {
        this.carId = carId;
        this.name = name;
        this.model = model;
        this.price = price;
        this.rentalDate = rentalDate;
        this.Image = Image;

    }

    public CarRented(int carId, String name, String model, int price,String rentalDate) {
        this.carId = carId;
        this.name = name;
        this.model = model;
        this.price = price;
        this.rentalDate = rentalDate;

    }

    public int getCarId() {
        return carId;
    }

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public String getRentalDate() {
        return rentalDate;
    }

    public int getPrice() {
        return price;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    @Override
    public String toString() {
        return "CarRented{" +
                "carId=" + carId +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", rentalDate='" + rentalDate + '\'' +
                ", Image='" + Image + '\'' +
                '}';
    }


}
