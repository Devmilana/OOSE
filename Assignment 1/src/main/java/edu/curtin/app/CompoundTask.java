/*****************************************************************************************
 * AUTHOR: PRASHANTHA FERNANDO                                                           *
 *                                                                                       *
 * LAST EDITED: 13/04/24                                                                 *
 *                                                                                       *
 * DESCRIPTION: Class file for modelling a compound task                                 *
 *****************************************************************************************/

package edu.curtin.app;

import java.util.*;

public class CompoundTask extends Task 
{
    private List<Task> children = new ArrayList<>();

    // Constructor
    public CompoundTask(String id, String description) 
    {
        super(id, description);
    }

    // Get children
    public List<Task> getChildren() 
    {
        return children;
    }

    // Add a child task
    @Override
    public void add(Task entry) 
    {
        children.add(entry);
    }

    // Remove a child task
    @Override
    public void remove(Task entry) 
    {
        children.remove(entry);
    }

    // Get a child task
    @Override
    public Task getChild(int i) 
    {
        return children.get(i);
    }
    
    // Get effort
    @Override
    public int getEffort() 
    {
        return children.stream().mapToInt(Task::getEffort).sum();
    }

    // Display task in hierarchy format
    @Override
    public void display(String tabspace) 
    {
        if (isNotEmpty(this.id) || !this.description.equals("WBS Root")) 
        {
            System.out.println(tabspace + id + ": " + description);
        }

        // Determine space based on whether the ID is not empty
        String newTabSpace;

        if (isNotEmpty(this.id)) 
        {
            newTabSpace = tabspace + "  ";
        } 
        else 
        {
            newTabSpace = tabspace;
        }

        // Iterate over children
        for (Task child : children) 
        {
            child.display(newTabSpace);
        }
    }

    // Export task to a string
    @Override
    public String serialize(int depth) 
    {
        StringBuilder builder = new StringBuilder();
        if (depth > 0) 
        { 
            builder.append("; ").append(id).append(" ; ").append(description).append("\n");
        }
        for (Task child : children) 
        {
            String childSerialization = child.serialize(depth + 1);
            if (!childSerialization.isEmpty() && depth > 0) 
            {
                childSerialization = childSerialization.replaceAll("^; ", id + " ; ");
            }
            builder.append(childSerialization);
        }
        return builder.toString();
    }

    // Helper method to check if a string is not empty
    private boolean isNotEmpty(String str) 
    {
        return str != null && !str.isEmpty();
    }
}