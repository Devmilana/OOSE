package edu.curtin.oose2024s1.assignment2.state;

import java.util.logging.Level;
import java.util.logging.Logger;

// ***** Servicing state ***** //
public class ServicingState implements State 
{
    private static final Logger logger = Logger.getLogger(ServicingState.class.getName());

    // ***** Handle servicing state ***** //
    @Override
    public void applyStateBehaviour(String email) 
    {
        System.out.println("Bike for " + email + " is being serviced");
        logger.log(Level.INFO, () -> "\nBike for " + email + " is being serviced\n");
    }
}