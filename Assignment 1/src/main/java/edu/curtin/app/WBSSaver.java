/*****************************************************************************************
 * AUTHOR: PRASHANTHA FERNANDO                                                           *
 *                                                                                       *
 * LAST EDITED: 15/04/24                                                                 *
 *                                                                                       *
 * DESCRIPTION: Class file for saving WBS to file                                        *
 *****************************************************************************************/

package edu.curtin.app;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

// Saves the WBS to file
public class WBSSaver 
{
    private static final Logger logger = Logger.getLogger(WBSSaver.class.getName());

    public static void saveToFile(String filename, CompoundTask root) 
    {
        String data;
        
        data = root.serialize(0);

        try (PrintWriter out = new PrintWriter(filename)) 
        {
            out.print(data);
            logger.log(Level.INFO, () -> "Successfully saved WBS to file: " + filename);

        } 
        catch (IOException e) 
        {
            System.err.println("\nFailed to save WBS: " + e.getMessage());
            logger.log(Level.SEVERE, () -> "Failed to save WBS: " + e.getMessage());
        }
    }
}