package edu.curtin.oose2024s1.assignment2.exceptions;

public class InvalidMessageException extends BikeShopException 
{
    public InvalidMessageException() 
    {
        super("FAILURE: Invalid message (parsing error)");
    }
}