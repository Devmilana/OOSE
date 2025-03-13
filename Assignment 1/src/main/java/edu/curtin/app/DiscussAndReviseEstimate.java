/*****************************************************************************************
 * AUTHOR: PRASHANTHA FERNANDO                                                           *
 *                                                                                       *
 * LAST EDITED: 13/04/24                                                                 *
 *                                                                                       *
 * DESCRIPTION: Class file for implementing discuss and revise estimate strategy         *
 *****************************************************************************************/

package edu.curtin.app;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiscussAndReviseEstimate implements ReconciliationStrategy 
{
    private static final Logger logger = Logger.getLogger(DiscussAndReviseEstimate.class.getName());

    // Reconcile the estimates by discussing and entering a single estimate
    @Override
    public int reconcile(List<Integer> estimates, Scanner sc) 
    {
        boolean allEqual;
        Integer firstEstimate;
        int revisedEstimate;

        if (estimates.isEmpty()) 
        {
            logger.log(Level.WARNING, "User entered no estimates for reconcilliation\n");
            throw new IllegalArgumentException("\nNo estimates given!");
        }

        allEqual = true;
        firstEstimate = estimates.get(0); // Get the first estimate
        
        for (int estimate : estimates) 
        {
            if (!firstEstimate.equals(estimate)) // Check if all estimates are equal
            {
                allEqual = false;
                break;
            }
        }
        
        if (allEqual) 
        {
            return firstEstimate; // Return estimate if all estimates are equal
        } 
        else 
        {
            System.out.print("\nDifferent estimates given! Discuss and enter a single estimate: ");
            revisedEstimate = sc.nextInt();
            sc.nextLine();
            return revisedEstimate; // Return revised estimate
        }
    }
}