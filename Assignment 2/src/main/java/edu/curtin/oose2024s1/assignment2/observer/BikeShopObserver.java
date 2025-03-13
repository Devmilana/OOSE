package edu.curtin.oose2024s1.assignment2.observer;

import edu.curtin.oose2024s1.assignment2.writer.FileIO;
import edu.curtin.oose2024s1.assignment2.model.BikeShop;
import edu.curtin.oose2024s1.assignment2.model.BikeShopStatus;

import java.util.logging.Level;
import java.util.logging.Logger;

// ***** Observer for bike shop ***** //
public class BikeShopObserver implements Observer<BikeShopStatus> 
{
    private BikeShop bikeShop;
    private FileIO writer;
    private static final Logger logger = Logger.getLogger(BikeShopObserver.class.getName());

    public BikeShopObserver(BikeShop bikeShop, FileIO writer) 
    {
        this.bikeShop = bikeShop;
        this.writer = writer;
    }

    // ***** Update observer with event ***** //
    @Override
    public void update(String event) 
    {
        bikeShop.eventHandling(event);
    }

    // ***** Update observer with status ***** //
    @Override
    public void updateStatus(BikeShopStatus status) 
    {
        String message = "\nDay: " + status.getdayCount() + 
                        "\nTotal cash: $" + status.getCash() +
                        "\nBikes available for purchase: " + status.getAvailableBikes() + 
                        "\nBikes being serviced: " + status.getBikesBeingServiced() + 
                        "\nBikes awaiting pick-up: " + status.getBikesAwaitingPickup() + "\n";
        
        System.out.println("\n==============================================================================");
        System.out.println(message);
        writer.writeToFile(message);
        logger.log(Level.INFO, () -> "Daily bike shop status displayed successfully\n");
    }

    // ***** Print final stats ***** //
    public void printFinalStats(int totalMessages, int totalFailures) 
    {
        String finalStats = "\nTotal messages: " + totalMessages +
                            "\nTotal failures: " + totalFailures;
        
        System.out.println("\n==============================================================================");
        System.out.println(finalStats);
        writer.writeToFile(finalStats);
        logger.log(Level.INFO, () -> "Final stats displayed successfully!" + "\nTotal messages: " + totalMessages + 
                                      "\nTotal failures: " + totalFailures + "\n");
    }
}