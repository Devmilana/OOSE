package edu.curtin.oose2024s1.assignment2.model;

// ***** Bike shop status ***** //
public class BikeShopStatus 
{
    // Variables for bike shop status
    private final int dayCount;
    private final int cash;
    private final int availableBikes;
    private final int bikesBeingServiced;
    private final int bikesAwaitingPickup;

    // ***** Constructor ***** //
    public BikeShopStatus(int dayCount, int cash, int availableBikes, int bikesBeingServiced, int bikesAwaitingPickup) 
    {
        this.dayCount = dayCount;
        this.cash = cash;
        this.availableBikes = availableBikes;
        this.bikesBeingServiced = bikesBeingServiced;
        this.bikesAwaitingPickup = bikesAwaitingPickup;
    }

    // ***** Return days elapsed ***** //
    public int getdayCount() 
    {
        return dayCount;
    }

    // ***** Return bike shop cash amount ***** //
    public int getCash() 
    {
        return cash;
    }

    // ***** Return number of available bikes in shop ***** //
    public int getAvailableBikes() 
    {
        return availableBikes;
    }

    // ***** Return number of bikes in shop being serviced ***** //
    public int getBikesBeingServiced() 
    {
        return bikesBeingServiced;
    }

    // ***** Return number of bikes in shop awaiting pickup ***** //
    public int getBikesAwaitingPickup() 
    {
        return bikesAwaitingPickup;
    }
}