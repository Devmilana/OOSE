package edu.curtin.oose2024s1.assignment2.exceptions;

public class BikeNotReadyException extends BikeShopException 
{
    public BikeNotReadyException() 
    {
        super("FAILURE: Bike not ready (still being serviced)");
    }
}