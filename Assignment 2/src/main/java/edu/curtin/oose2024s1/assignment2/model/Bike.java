package edu.curtin.oose2024s1.assignment2.model;

import edu.curtin.oose2024s1.assignment2.state.State;

// ***** Bike class ***** //
public class Bike 
{
    private State state;
    private String email;
    private int serviceStartDay;
    private boolean serviceCharge;

    // Setters
    public void setState(State state) 
    {
        this.state = state;
    }

    public void applyState() 
    {
        state.applyStateBehaviour(email);
    }

    public void setServiceStartDay(int serviceStartDay) 
    {
        this.serviceStartDay = serviceStartDay;
    }

    public void setServiceCharge(boolean serviceCharge) 
    {
        this.serviceCharge = serviceCharge;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    // Getters
    public State getState() 
    {
        return state;
    }

    public int getServiceStartDay() 
    {
        return serviceStartDay;
    } 

    public String getEmail()
    {
        return email;
    }

    public boolean isServiceChargeApplied() 
    {
        return serviceCharge;
    }
}