package edu.curtin.oose2024s1.assignment2.exceptions;

public class NoBikesLeftException extends BikeShopException 
{
    public NoBikesLeftException() 
    {
        super("FAILURE: No bikes left (for a customer to purchase)");
    }
}