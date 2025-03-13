/*****************************************************************************************
 * AUTHOR: PRASHANTHA FERNANDO                                                           *
 *                                                                                       *
 * LAST EDITED: 13/04/24                                                                 *
 *                                                                                       *
 * DESCRIPTION: Class file for modelling a leaf task                                     *
 *****************************************************************************************/

package edu.curtin.app;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LeafTask extends Task 
{
    private Integer effort;
    private static final Logger logger = Logger.getLogger(LeafTask.class.getName());

    // Constructor
    public LeafTask(String id, String description, Integer effort) 
    {
        super(id, description);
        this.effort = effort;
    }

    // Check if the task has effort
    public boolean hasEffort() 
    {
        return effort != null;
    }

    // Set effort
    public void setEffort(Integer effort) 
    {
        this.effort = effort;
    }

    // Getter for task description
    public String getDescription() 
    {
        return description;
    }

    // Add a task
    @Override
    public void add(Task entry) 
    {
        logger.log(Level.WARNING, "Tried to call 'add' operation on an unsupported task entry\n");
        throw new UnsupportedOperationException();
    }

    // Remove a task
    @Override
    public void remove(Task entry) 
    {
        logger.log(Level.WARNING, "Tried to call 'remove' operation on an unsupported task entry\n");
        throw new UnsupportedOperationException();
    }

    // Get a child task
    @Override
    public Task getChild(int i) 
    {
        logger.log(Level.WARNING, "Tried to call 'getChild' operation on an unsupported task entry\n");
        throw new UnsupportedOperationException();
    }

    // Get effort
    @Override
    public int getEffort() 
    {
        if (effort != null) 
        {
            return effort;
        } 
        else 
        {
            return 0;
        }
    }

    // Display task in hierachy format
    @Override
    public void display(String tabspace) 
    {
        String output = tabspace + id + ": " + description;
    
        // Check if effort is not null and not zero, then append it
        if (effort != null && effort != 0) 
        {
            output += ", effort = " + effort;
        }
    
        System.out.println(output);
    }

    // Export task to a string
    @Override
    public String serialize(int depth) 
    {
        String effortPart;
    
        if (effort != null) 
        {
            effortPart = " ; " + effort;
        } 
        else 
        {
            effortPart = " ;";
        }

        return "; " + id + " ; " + description + effortPart + "\n";
    }
}