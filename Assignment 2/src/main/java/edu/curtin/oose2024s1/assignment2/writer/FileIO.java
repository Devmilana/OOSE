package edu.curtin.oose2024s1.assignment2.writer;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

// ***** File writer ***** //
public class FileIO 
{
    private static final Logger logger = Logger.getLogger(FileIO.class.getName());   
    private Writer writer; // Initalise Writer object

    // ***** Create file for writing ***** //
    public FileIO(String filename) 
    {
        try 
        {
            writer = new FileWriter(filename, false);
        } 
        catch (IOException e) 
        {
            System.out.println("Error creating file for writing");
            logger.log(Level.SEVERE, () -> "CRTICAL ERROR: Unable to create file for writing\n");
        }
    }

    // ***** Write to file ***** //
    public void writeToFile(String text) 
    {
        try 
        {
            writer.write(text + "\n");
            writer.flush();
        } 
        catch (IOException e) 
        {
            System.out.println("Error writing to file");
            logger.log(Level.SEVERE, () -> "CRTICAL ERROR: Unable to write to file\n");
        }
    }

    // ***** Close file writer ***** //
    public void close() 
    {
        try 
        {
            writer.close();
        } 
        catch (IOException e) 
        {
            System.out.println("Error closing file writer");
            logger.log(Level.SEVERE, () -> "CRTICAL ERROR: Unable to close file writer\n");
        }
    }
}