package edu.curtin.oose2024s1.assignment2.exceptions;

public class NoBikesMatchingEmailException extends BikeShopException 
{
    public NoBikesMatchingEmailException() 
    {
        super("FAILURE: No bike matching customer email (for pick-up)");
    }
}