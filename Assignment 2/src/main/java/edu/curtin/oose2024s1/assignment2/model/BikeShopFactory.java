package edu.curtin.oose2024s1.assignment2.model;

import edu.curtin.oose2024s1.assignment2.writer.*;
import edu.curtin.oose2024s1.assignment2.observer.*;
import edu.curtin.oose2024s1.assignment2.BikeShopInput;

// ***** Bike shop factory ***** //
public class BikeShopFactory 
{
    // ***** Create new bike shop ***** //
    public static BikeShop createBikeShop() 
    {
        BikeShopInput input = new BikeShopInput();
        FileIO writer = new FileIO("sim_results.txt");
        ObserverOperations<BikeShopStatus> operation = new ObserverOperations<>();
        BikeShop bikeShop = new BikeShop(input, writer, operation);
        BikeShopObserver observer = new BikeShopObserver(bikeShop, writer); // Pass FileIO to the observer
        operation.addObserver(observer);
        
        return bikeShop;
    }
}