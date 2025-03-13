/*****************************************************************************************
 * AUTHOR: PRASHANTHA FERNANDO                                                           *
 *                                                                                       *
 * LAST EDITED: 13/04/24                                                                 *
 *                                                                                       *
 * DESCRIPTION: Class file for estimate configuration                                    *
 *****************************************************************************************/

package edu.curtin.app;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigureEstimate 
{
    private final Map<String, Task> taskMap;
    private int numberOfEstimators = 3; // Sets default number of estimators
    private ReconciliationStrategy reconciliationStrategy = new DiscussAndReviseEstimate(); // Sets default estimattion strategy

    private static final Logger logger = Logger.getLogger(ConfigureEstimate.class.getName());

    // Constructor
    public ConfigureEstimate(Map<String, Task> taskMap) 
    {
        this.taskMap = taskMap;
    }

    // Sets number of estimators
    public void setNumberOfEstimators(int n) 
    {
        this.numberOfEstimators = n;
    }

    // Sets reconciliation strategy
    public void setReconciliationStrategy(ReconciliationStrategy strategy) 
    {
        this.reconciliationStrategy = strategy;
    }  
    
    // Configure estimate
    public void configure(Scanner sc) 
    {
        int choice;

        System.out.print("\nEnter the number of estimators: ");
        setNumberOfEstimators(sc.nextInt()); // Set number of estimators
        sc.nextLine();

        System.out.println("\nSelect reconciliation strategy: \n1. Take the highest estimate\n2. Take the median estimate\n3. Discuss and revise");
        System.out.print("\nStrategy choice: ");
        choice = sc.nextInt();
        sc.nextLine();

        // Set strategy based on user choice
        switch (choice) 
        {
            case 1:
                setReconciliationStrategy(new TakeHighestEstimate()); 
                System.out.println("\nReconcillation strategy set to Take Highest Estimate");
                logger.log(Level.INFO, "User selected Take Highest Estimate strategy\n");
                break;

            case 2:
                setReconciliationStrategy(new TakeMedianEstimate());
                System.out.println("\nReconcillation strategy set to Take Median Estimate");
                logger.log(Level.INFO, "User selected Take Median Estimate strategy\n");
                break;

            case 3:
                setReconciliationStrategy(new DiscussAndReviseEstimate());
                System.out.println("\nReconcillation strategy set to Discuss and Revise Estimate");
                logger.log(Level.INFO, "User selected Discuss and Revise Estimate strategy\n");
                break;

            default:
                System.out.println("\nInvalid choice! Default strategy (3) selected");
                logger.log(Level.INFO, "Invalid reconcillation strategy selected. Default strategy (3) selected");
                break;
        }
    }

    // Estimates effort for a task
    public void estimateEffort(Scanner sc) 
    {
        String taskId;
        Task task;
        int reconciledEffort;

        System.out.print("\nEnter task ID: ");
        taskId = sc.nextLine();
        task = taskMap.get(taskId);

        // Check if task ID exists
        if (task == null) 
        {
            System.out.println("\nTask ID not found!");
            logger.log(Level.INFO, "User entered ID not found\n");
            return;
        }
    
        // Prevent estimation for tasks with subtasks (CompoundTask)
        if (task instanceof CompoundTask && !((CompoundTask)task).getChildren().isEmpty()) 
        {
            System.out.println("\nEffort can only be assigned to leaf tasks! (Tasks without subtasks)");
            logger.log(Level.INFO, "User tried to assign effort to a CompoundTask with subtasks. Effort can only be assigned to leaf tasks.\n");
            return;
        }
    
        List<Integer> estimates = new ArrayList<>(); // Initialize list to store estimates
        System.out.println("\nPlease enter " + numberOfEstimators + " effort estimates");
        for (int i = 0; i < numberOfEstimators; i++) 
        {
            System.out.print("Estimate " + (i + 1) + ": ");
            estimates.add(sc.nextInt());
            sc.nextLine();
        }
    
        // Reconcile the estimates based on the strategy
        reconciledEffort = reconciliationStrategy.reconcile(estimates, sc);

        System.out.println("\nReconciled effort estimate is: " + reconciledEffort);
        logger.log(Level.INFO, () -> "Reconciled effort estimate is: " + reconciledEffort + " for task ID: " + 
                    taskId + " using " + reconciliationStrategy.getClass().getSimpleName() + " strategy.\n");
    
        // Update the task with the reconciled effort
        updateTaskEffort(taskId, reconciledEffort);
    }
    
    // Update task effort
    public void updateTaskEffort(String taskId, int newEffort) 
    {
        Task task = taskMap.get(taskId);

        // Check if task is instance of LeafTask
        if (task instanceof LeafTask) 
        {
            ((LeafTask) task).setEffort(newEffort); // Directly update LeafTask
            logger.log(Level.INFO, () -> "Effort updated for task ID: " + taskId + " to " + newEffort + "\n");
        } 
        else if (task instanceof CompoundTask) // Compound tasks cannot have an effort
        {
            System.out.println("\nEffort can only be assigned to leaf tasks! (Tasks without subtasks)");
            logger.log(Level.INFO, "User tried to assign effort to a CompoundTask with subtasks. Effort can only be assigned to leaf tasks.\n");
        }
    }
}