package edu.curtin.oose2024s1.assignment2.model;

import edu.curtin.oose2024s1.assignment2.state.*;
import edu.curtin.oose2024s1.assignment2.writer.*;
import edu.curtin.oose2024s1.assignment2.observer.*;
import edu.curtin.oose2024s1.assignment2.exceptions.*;
import edu.curtin.oose2024s1.assignment2.BikeShopInput;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

// ***** Bike shop class ***** //
public class BikeShop 
{
    // Constants for simulation
    private static final int INITIAL_CASH = 15000;
    private static final int INITIAL_BIKES = 50;
    private static final int MAX_BIKES = 100;
    private static final int EMPLOYEE_PAY = 1000;
    private static final int BIKE_COST = 1000;
    private static final int SERVICE_COST = 100;
    private static final int DELIVERY_COST = 5000;

    // Variables for bike shop simulation 
    private int dayCount = 0;
    private int cash = INITIAL_CASH;
    private int availableBikes = INITIAL_BIKES;
    private int bikesBeingServiced = 0;
    private int bikesAwaitingPickup = 0;
    private int totalMessages = 0;
    private int totalFailures = 0;

    private BikeShopInput input;
    private FileIO writer;
    private ObserverOperations<BikeShopStatus> operation;
    private Map<String, Bike> bikeMap = new HashMap<>();
    private static final Logger logger = Logger.getLogger(BikeShop.class.getName());

    // ***** BikeShop Constructor ***** //
    public BikeShop(BikeShopInput input, FileIO writer, ObserverOperations<BikeShopStatus> operation) 
    {
        this.input = input;
        this.writer = writer;
        this.operation = operation;
    }

    // ***** Runs services for each day ***** //
    public void simulateDay() 
    {
        dayCount++; // Increment day count
            
        processMessages(); // Read input messages from simulation file
        checkServicingBikes(); // Check if servicing of bikes is complete
        checkIfBikesAvailable(); // Check and print if bikes are available for purchase
        notifyDailyShopStatus(); // Notify observers of bike shop status

        // Pay employee every 7 days
        if (dayCount % 7 == 0) 
        {
            payEmployee();
        }
    }

    // ***** Processes current and next messages ***** //
    private void processMessages() 
    {
        String msg = input.nextMessage();
            
        while (msg != null) 
        {
            totalMessages++; // Increment message count
            operation.notifyObservers(msg); // Notify observers of message
            msg = input.nextMessage(); // Read next message
        }
    }

    // ***** Checks bike service status ***** //
    private void checkServicingBikes() 
    {
        for (Bike bike : bikeMap.values()) 
        {
            // If bike has been serviced for 2 days, change state to awaiting pickup
            if (bike.getState() instanceof ServicingState && (dayCount - bike.getServiceStartDay() >= 2)) 
            {
                bikesBeingServiced--;
                bikesAwaitingPickup++;
                bike.setState(new AwaitingPickupState());
                bike.applyState();
            }
        }   
    }

    // ***** Notify observer ***** //
    public void notifyDailyShopStatus() 
    {
        BikeShopStatus status = new BikeShopStatus(dayCount, cash, availableBikes, bikesBeingServiced, bikesAwaitingPickup);
        operation.notifyObservers(status); // Notify observers of bike shop status
    }

    // ***** Employee payment ***** //
    private void payEmployee() 
    {
        cash -= EMPLOYEE_PAY;

        logSuccess("\nEmployee was paid $" + EMPLOYEE_PAY + " on day " + dayCount);
    }

    // ***** Event handling ***** //
    public void eventHandling(String msg) 
    {
        try
        {
            System.out.println("\n" + msg);

            // Split message into parts to determine event type and email
            String[] parts = msg.split(" ");
            String event = parts[0]; // Get event type
                
            // Check if email is present
            String email = null; 
            if (parts.length > 1) 
            {
                email = parts[1];
            } 

            switch (event) 
            {
                case "DELIVERY": // Handle delivery event
                    delivery();
                    break;

                case "DROP-OFF": // Handle drop-off event
                    dropoff(email);
                    break;

                case "PURCHASE-ONLINE": // Handle online purchase event
                    purchaseOnline(email);
                    break;

                case "PURCHASE-IN-STORE": // Handle in-store purchase event
                    purchaseInstore();
                    break;

                case "PICK-UP": // Handle pick-up event
                    pickup(email);
                    break;

                default:
                    throw new InvalidMessageException();
            }
        }
        catch (BikeShopException e)
        {
            printAndLogFailure(e.getMessage());
        }        
    }

    // ***** Delivery handling ***** //
    public void delivery() throws NotEnoughCashException, NotEnoughSpaceException
    {
        try
        {
            // If there is space for 10 bikes and enough cash for delivery cost
            if (getTotalNumBikes() <= 90 && cash >= DELIVERY_COST) 
            {
                availableBikes += 10;
                cash -= DELIVERY_COST;
                
                logSuccess("Sucessful delivery of 10 bikes to store");
            } 
            else 
            {
                if (cash < DELIVERY_COST) 
                {
                    throw new NotEnoughCashException();
                } 
                else 
                {
                    throw new NotEnoughSpaceException();
                }
            }
        }
        catch (NotEnoughCashException | NotEnoughSpaceException e) 
        {
            printAndLogFailure(e.getMessage());
        }
    }

    // ***** Drop-off handling ***** //
    public void dropoff(String email) throws NotEnoughSpaceException
    {
        try
        {
            // If there is space for bikes
            if (getTotalNumBikes() < MAX_BIKES) 
            {
                bikesBeingServiced++; // Increment bikes being serviced
                
                Bike bike = new Bike(); // Create new bike
                bike.setState(new ServicingState()); // Set state to servicing
                bike.setServiceStartDay(dayCount); // Set service start day
                bike.setEmail(email); // Set email
                bikeMap.put(email, bike); // Add bike to map
                bike.applyState(); // Apply state to bike
                bike.setServiceCharge(true); // Mark the service charge as applied

                logSuccess("Sucessful bike drop-off for " + email);
            }
            else 
            {
                throw new NotEnoughSpaceException();
            }
        }
        catch (NotEnoughSpaceException e) 
        {
            printAndLogFailure(e.getMessage());
        }
    }

    // ***** Online purchase handling ***** //
    public void purchaseOnline(String email) throws NoBikesLeftException
    {
        try
        {
            // If bikes are available
            if (availableBikes > 0) 
            {
                availableBikes--; // Decrement available bikes
                bikesAwaitingPickup++; // Increment bikes awaiting pickup
                cash += BIKE_COST;
                
                Bike bike = new Bike(); // Create new bike
                bike.setState(new AwaitingPickupState()); // Set state to awaiting pickup
                bike.setEmail(email); // Set email
                bikeMap.put(email, bike); // Add bike to map
                bike.applyState(); // Apply state to bike

                logSuccess("Sucessful online bike purchase for " + email);
            } 
            else 
            {
                throw new NoBikesLeftException();
            }
        }
        catch (NoBikesLeftException e) 
        {
            printAndLogFailure(e.getMessage());
        }
    }

    // ***** Instore purchase handling ***** //
    public void purchaseInstore() throws NoBikesLeftException
    {
        try
        {
            // If bikes are available
            if (availableBikes > 0) 
            {
                availableBikes--; // Decrement available bikes
                cash += BIKE_COST;

                logSuccess("Sucessful in store bike purchase");
            } 
            else 
            {
                throw new NoBikesLeftException();
            }
        }
        catch (NoBikesLeftException e) 
        {
            printAndLogFailure(e.getMessage());
        }
    }

    // ***** Pick-up handling ***** //
    public void pickup(String email) throws BikeNotReadyException, NoBikesMatchingEmailException
    {
        try
        {
            Bike bike = bikeMap.get(email); // Identify bike by email

            if (bike != null) 
            {
                // If bike is awaiting pickup
                if (bike.getState() instanceof AwaitingPickupState) 
                {
                    bikesAwaitingPickup--;
                    bikeMap.remove(email);

                    // Check if service charge has been applied to the awaiting pickup bike
                    if (bike.isServiceChargeApplied()) 
                    {
                        cash += SERVICE_COST;

                        logSuccess("Bike for " + email + " picked up successfully!");
                        logSuccess("Charged: $" + SERVICE_COST + " of service cost for " + email);
                    }
                    else
                    {
                        logSuccess("Bike for " + email + " picked up successfully!");
                    }
                }
                // If bike is in service state and has been serviced for 2 days
                else if (bike.getState() instanceof ServicingState && (dayCount - bike.getServiceStartDay() >= 2)) 
                {
                    bikesBeingServiced--;
                    bikesAwaitingPickup++;
                    cash += SERVICE_COST;
                    bikeMap.remove(email);
                    
                    logSuccess("Bike for " + email + " picked up successfully!");
                    logSuccess("Charged: $" + SERVICE_COST + " of service cost for " + email);
                } 
                else 
                {
                    throw new BikeNotReadyException();
                }
            } 
            else 
            {
                throw new NoBikesMatchingEmailException();
            }
        }
        catch (BikeNotReadyException | NoBikesMatchingEmailException e) 
        {
            printAndLogFailure(e.getMessage());
        }
    }

    // ***** Print available bikes state ***** //
    private void checkIfBikesAvailable() 
    {
        if (availableBikes > 0 && dayCount > 1) 
        {
            AvailableState availableState = new AvailableState();
            availableState.applyStateBehaviour(null); // null passed as the email since it's not used
        }
    }

    // ***** Return total number of bikes in store ***** //
    private int getTotalNumBikes() 
    {
        return availableBikes + bikesBeingServiced + bikesAwaitingPickup;
    }

    // ***** Log successful statements ***** //
    private void logSuccess(String msg) 
    {
        System.out.println(msg);
        writer.writeToFile(msg);
        logger.log(Level.INFO, () -> msg + "\n");
    }

    // ***** Log failed statements ***** //
    private void printAndLogFailure(String msg) 
    {
        totalFailures++;
        
        System.out.println(msg);
        writer.writeToFile(msg);
        logger.log(Level.SEVERE, () -> msg + "\n");
    }

    // ***** Print final stats to console ***** //
    public void displayFinalMessages() 
    {
        BikeShopObserver observer = (BikeShopObserver) operation.getObservers().get(0); // Assuming one observer
        observer.printFinalStats(totalMessages, totalFailures);
    }

    // ***** Close writer ***** //
    public void close() 
    {
        writer.close();
    }
}