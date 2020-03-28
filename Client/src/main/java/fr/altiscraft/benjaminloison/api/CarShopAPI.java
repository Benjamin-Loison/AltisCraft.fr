package fr.altiscraft.benjaminloison.api;

public class CarShopAPI
{
    private VehicleAPI[] vehicles;
    private String name;
    private int x, y, z, direction, numberVehicles;
    private boolean air, isGarage;

    public CarShopAPI(VehicleAPI[] vehicles, String name, int x, int y, int z, int direction)
    {
        this.vehicles = vehicles;
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.direction = direction;
        air = false;
        isGarage = false;
    }

    public CarShopAPI(VehicleAPI[] vehicles, String name, int x, int y, int z, int direction, boolean isGarage)
    {
        this.vehicles = vehicles;
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.direction = direction;
        air = false;
        this.isGarage = isGarage;
    }

    public CarShopAPI(VehicleAPI[] vehicles, int x, int y, int z, int direction, boolean air)
    {
        this.vehicles = vehicles;
        this.x = x;
        this.y = y;
        this.z = z;
        this.direction = direction;
        this.air = air;
        if(air)
            name = "Air garage";
        else
            name = "Land garage";
        isGarage = true;
    }

    public VehicleAPI[] getVehicles()
    {
        return vehicles;
    }

    public VehicleAPI[] getUtilsVehicles()
    {
        VehicleAPI[] list = new VehicleAPI[getNumberVehicles()];
        for(int i = 0; i < list.length; i++)
            if(vehicles[i] != null)
                list[i] = vehicles[i];
        return list;
    }

    public String getName()
    {
        return name;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getZ()
    {
        return z;
    }

    public int getDirection()
    {
        return direction;
    }

    public boolean getAir()
    {
        return air;
    }

    public boolean isGarage()
    {
        return isGarage;
    }

    public int getNumberVehicles()
    {
        int number = 0;
        for(int i = 0; i < vehicles.length; i++)
            if(vehicles[i] != null)
                number++;
        return number;
    }
}