package edu.curtin.oose2024s1.assignment2;

import edu.curtin.oose2024s1.assignment2.model.BikeShop;
import edu.curtin.oose2024s1.assignment2.model.BikeShopFactory;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

// ***** Main Application ***** //
public class App 
{
    private static final Logger logger = Logger.getLogger(App.class.getName());
    public static void main(String[] args) 
    {
        System.out.println("\n==============================================================================");
        System.out.println("\n\t               BIKE SHOP SIMULATION START                  ");

        // Create a BikeShop instance using the factory
        BikeShop bikeShop = BikeShopFactory.createBikeShop();

        try 
        {
            while (System.in.available() == 0) 
            {
                bikeShop.simulateDay(); // Simulate a day

                // Wait 1 second
                try 
                {
                    Thread.sleep(1000);
                } 
                catch (InterruptedException e) 
                {
                    logger.log(Level.SEVERE, () -> "CRTICAL ERROR: Thread execution interuptted" + e + "\n");
                    throw new AssertionError(e);
                }
            }
        } 
        catch (IOException e) 
        {
            System.out.println("Error reading simulation input");
            logger.log(Level.SEVERE, () -> "CRTICAL ERROR: Unable to read simulation input" + e + "\n");
        } 
        finally 
        {
            // Display final messages and close resources
            bikeShop.displayFinalMessages();

            System.out.println("\n==============================================================================");
            System.out.println("\n\t               BIKE SHOP SIMULATION END                  ");
            System.out.println("\n==============================================================================");
            
            bikeShop.close();
        }
    }
}