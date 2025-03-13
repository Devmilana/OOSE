package edu.curtin.oose2024s1.assignment2.exceptions;

public class NotEnoughCashException extends BikeShopException 
{
    public NotEnoughCashException() 
    {
        super("FAILURE: Not enough cash (for shop to purchase new bikes)");
    }
}