/*****************************************************************************************
 * AUTHOR: PRASHANTHA FERNANDO                                                           *
 *                                                                                       *
 * LAST EDITED: 15/04/24                                                                 *
 *                                                                                       *
 * DESCRIPTION: Main class file for loading, displaying a menu for WBS effort            *
 *              addition/configuration and saving of a WBS file                          *
 *****************************************************************************************/

package edu.curtin.app;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WBSApp 
{
    private static String filename;
    private static CompoundTask root;
    private static ConfigureEstimate config;
    private static WBSReader reader = new WBSReader();
    private static Map<String, Task> taskMap = new HashMap<>();
    private static final Logger logger = Logger.getLogger(WBSApp.class.getName());
    
    /** =======================  Main Method ========================== **/
    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);

        if (args.length != 1) // Check for correct number of arguments
        {
            System.err.println("\nUsage: java WBSApp <filename>"); 
            logger.log(Level.WARNING, "Incorrect number of arguments given\n");
            sc.close();
            return;
        }

        filename = args[0]; // Get filename from command line arguments
        root = loadWBSFile(); // Load WBS file to tree strucutre
        config = new ConfigureEstimate(taskMap); // Applies configuration to all tasks present within the WBS

        displayMainMenu(sc); // Display main menu

        sc.close();
    }

    /** =======================  Load and populate tasks ========================== **/
    public static CompoundTask loadWBSFile() 
    {
        CompoundTask root = reader.loadFromFile(filename, taskMap); // Load WBS file to tree structure with a root node modelled as a CompoundTask
        return root;
    }

    /** =======================  Main Menu ========================== **/
    public static void displayMainMenu(Scanner sc) 
    {      
        int choice;
        boolean exitMenu = false;

        while (!exitMenu)
        {
            try 
            {
                displayWBSData();

                System.out.println("\n==============================================================================");
                System.out.println("\n\t                  WBS ESTIMATION APP                  ");
                System.out.println("\n==============================================================================");
                System.out.println("\nWelcome to the WBS App!");
                System.out.println("\nPlease select an option:");
                System.out.println("\n\t[ 1 ] Estimate Effort");
                System.out.println("\t[ 2 ] Configure");
                System.out.println("\t[ 0 ] Quit");

                System.out.print("\nEnter your choice: ");

                if (sc.hasNextInt())
                {
                    choice = sc.nextInt();
                    sc.nextLine();
                    
                    switch(choice) 
                    {
                        case 1:
                            logger.log(Level.INFO, "User selected option 1: Estimate Effort\n");
                            config.estimateEffort(sc); // Estimates effort for a task
                            reader.recalculateUnknownTasks(root); // Recalculates unknown tasks after new effort estimation
                            reader.recalculateTotalKnownEffort(root); // Recalculate total known effort after new effort estimation
                            break;
                        
                        case 2:
                            logger.log(Level.INFO, "User selected option 2: Configure\n");
                            config.configure(sc); // Configures effort estimation strategy
                            break;
                        
                        case 0:
                            logger.log(Level.INFO, "User selected option 0: Quit WBS App\n");
                            WBSSaver.saveToFile(filename, root);
                            System.out.println("\n==============================================================================");
                            System.out.println("\n\tThank you for using the WBS Estimation App! Exiting now...");
                            System.out.println("\n==============================================================================\n");
                            exitMenu = true;
                            break;
                       
                        default:
                            System.out.println("\nInvalid choice. Try again.");
                            logger.log(Level.INFO, "User selected an invalid option\n");
                    }
                }
                else
                {
                    System.out.println("\nInvalid input. Please enter a valid number.");
                    sc.nextLine();
                    logger.log(Level.INFO, "User entered invalid input\n");
                }
            }
            // Exception handling for invalid menu inputs 
            catch (NoSuchElementException e) 
            {   
                System.out.println("\nSystem error: " + e.getMessage());
                logger.log(Level.INFO, () ->  "System error: " + e.getMessage() + "\n");
            } 
        }
    }

    /** =======================  Displays WBS Hierachical Structure ========================== **/
    public static void displayWBSData() 
    {
        System.out.println("\n==============================================================================\n");
        
        if (root != null) 
        {
            root.display(" ");
        }

        System.out.println("\nTotal known effort = " + reader.getTotalKnownEffort());
        System.out.println("Unknown tasks = " + reader.getUnknownTasks());
        
        logger.log(Level.INFO, "WBS Data displayed\n");
    }
}