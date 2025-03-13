package edu.curtin.oose2024s1.assignment2.exceptions;

public class NotEnoughSpaceException extends BikeShopException 
{
    public NotEnoughSpaceException() 
    {
        super("FAILURE: Not enough space (to store more bikes)");
    }
}