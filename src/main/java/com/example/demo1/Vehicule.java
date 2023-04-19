package com.example.demo1;

public class Vehicule {
    private String name,model,manufacturer,passengers,vehicle_class;

    public Vehicule() {
    }

    public Vehicule(String name, String model, String manufacturer, String passengers, String vehicle_class) {
        this.name = name;
        this.model = model;
        this.manufacturer = manufacturer;
        this.passengers = passengers;
        this.vehicle_class = vehicle_class;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getPassengers() {
        return passengers;
    }

    public void setPassengers(String passengers) {
        this.passengers = passengers;
    }

    public String getVehicle_class() {
        return vehicle_class;
    }

    public void setVehicle_class(String vehicle_class) {
        this.vehicle_class = vehicle_class;
    }
}
