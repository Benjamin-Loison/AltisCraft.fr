package fr.altiscraft.benjaminloison.api;

public class VehicleAPI
{
    private String name;
    private int price;

    public VehicleAPI(String name, int price)
    {
        this.name = name;
        this.price = price;
    }
    
    public VehicleAPI(String name)
    {
        this.name = name;
        price = 0;
    }

    public String getName()
    {
        return name;
    }

    public int getPrice()
    {
        return price;
    }
}