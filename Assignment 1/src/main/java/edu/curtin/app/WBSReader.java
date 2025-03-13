/*****************************************************************************************
 * AUTHOR: PRASHANTHA FERNANDO                                                           *
 *                                                                                       *
 * LAST EDITED: 13/04/24                                                                 *
 *                                                                                       *
 * DESCRIPTION: Class file for parsing WBS data file                                     *
 *****************************************************************************************/

package edu.curtin.app;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WBSReader 
{
    private static final Logger logger = Logger.getLogger(WBSReader.class.getName());

    private int totalKnownEffort = 0;
    private int unknownTasks = 0;

    // Load and parse the WBS file, returning the root CompoundTask
    public CompoundTask loadFromFile(String filename, Map<String, Task> taskMap) 
    {
        CompoundTask root = new CompoundTask("", "WBS Root");// Initialize root node with empty ID and description
    
        try (Scanner sc = new Scanner(new File(filename))) 
        {
            while (sc.hasNextLine()) 
            {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) // Ignores empty lines
                {
                    continue; 
                }
    
                String[] parts = line.split(";", -1); // -1 to include trailing empty strings
    
                if (parts.length < 3) // Ensure there are at least ID and description
                {
                    continue; 
                }
    
                 // Parse parent ID, task ID, description, and effort (if present)
                 String parentId;
                 if (parts[0].trim().isEmpty()) 
                 {
                     parentId = null;
                 } 
                 else 
                 {
                     parentId = parts[0].trim();
                 }

                String taskId = parts[1].trim();

                String description = parts[2].trim();

                Integer effort;
                if (parts.length > 3 && !parts[3].trim().isEmpty()) 
                {
                    effort = Integer.parseInt(parts[3].trim());
                } 
                else 
                {
                    effort = null;
                }

                // Create task based on number of fields
                Task task;
                if (parts.length == 4 && !parts[3].trim().isEmpty()) 
                {
                    // Effort field is present and not empty, treat as LeafTask
                    task = new LeafTask(taskId, description, effort);
                    if (effort != null) 
                    {
                        totalKnownEffort += effort;
                    } 
                    else 
                    {
                        unknownTasks++;
                    }
                } 
                else if (parts.length == 4 && parts[3].trim().isEmpty()) 
                {
                    // Effort field is present but empty, make it a Leaf Task without specified effort
                    task = new LeafTask(taskId, description, null);
                    unknownTasks++;
                } 
                else 
                {
                    // If task has no fourth field, make it a a Compound Task
                    task = new CompoundTask(taskId, description);
                }
    
                taskMap.put(taskId, task); // Add task to map
    
                if (parentId == null) 
                {
                    root.add(task); // Add task to root if no parent ID
                } 
                else 
                {
                    Task parentTask = taskMap.get(parentId);
                    if (parentTask instanceof CompoundTask) 
                    {
                        ((CompoundTask) parentTask).add(task); // Add task to parent if parent is a Compound Task
                    } 
                    else 
                    {
                        logger.log(Level.WARNING, () -> "Attempt to add a child to a non-compound task: " + parentTask.getId());
                    }
                }
            }
            
            logger.log(Level.INFO, () -> "File: " + filename + " read successfully");
        } 
        catch (FileNotFoundException e) 
        {
            System.err.println("File not found: " + filename);
            logger.log(Level.SEVERE, () -> "File not found: " + filename + e);
        }
    
        return root;
    }

    // Getter for total known effort
    public int getTotalKnownEffort() 
    {
        return totalKnownEffort;
    }

    // Getter for unknown tasks
    public int getUnknownTasks() 
    {
        return unknownTasks;
    }

    // Recalculate total known effort
    public void recalculateTotalKnownEffort(CompoundTask root) 
    {
        totalKnownEffort = 0; // Reset total effort before recalculation
        calculateTotalKnownEffort(root);
    }

    // Recalculate unknown tasks
    public void recalculateUnknownTasks(CompoundTask root) 
    {
        unknownTasks = 0;
        calculateUnknownTasks(root); // Start recalculation from the root
    }

    // Helper method for calculating total known effort
    private void calculateTotalKnownEffort(Task task) 
    {
        if (task instanceof CompoundTask) 
        {
            CompoundTask compoundTask = (CompoundTask) task;
            for (Task child : compoundTask.getChildren()) 
            {
                calculateTotalKnownEffort(child); // Recursively calculate for each child
            }
        } 
        else if (task instanceof LeafTask) 
        {
            LeafTask leafTask = (LeafTask) task;
            if (leafTask.hasEffort()) 
            {
                totalKnownEffort += leafTask.getEffort();
            }
        }
    }

    // Helper method for calculating unknown tasks
    private void calculateUnknownTasks(Task task) 
    {
        if (task instanceof CompoundTask) 
        {
            CompoundTask compoundTask = (CompoundTask) task;
            if (compoundTask.getChildren().isEmpty()) 
            {
                unknownTasks++;
            } 
            else 
            {
                for (Task child : compoundTask.getChildren()) 
                {
                    calculateUnknownTasks(child); // Recursively calculate for each child
                }
            }
        } 
        else if (task instanceof LeafTask) 
        {
            LeafTask leafTask = (LeafTask) task;
            if (leafTask.getEffort() == 0) 
            {
                unknownTasks++;
            }
        }
    }
}