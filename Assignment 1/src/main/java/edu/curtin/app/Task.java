/*****************************************************************************************
 * AUTHOR: PRASHANTHA FERNANDO                                                           *
 *                                                                                       *
 * LAST EDITED: 13/04/24                                                                 *
 *                                                                                       *
 * DESCRIPTION: Abstract class file for modelling tasks                                  *
 *****************************************************************************************/

package edu.curtin.app;

public abstract class Task 
{
    protected String id;
    protected String description;

    // Constructor
    public Task(String id, String description) 
    {
        this.id = id;
        this.description = description;
    }

    // Getter for ID
    public String getId() 
    {
        return id;
    }
    
    // Add a task
    public abstract void add(Task entry);
    
    // Remove a task
    public abstract void remove(Task entry);
    
    // Get a child task
    public abstract Task getChild(int i);

    // Get effort
    public abstract int getEffort();

    // Display task
    public abstract void display(String indent);

    // Serialize task
    public abstract String serialize(int depth);
}