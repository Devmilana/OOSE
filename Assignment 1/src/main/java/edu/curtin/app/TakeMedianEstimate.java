/*****************************************************************************************
 * AUTHOR: PRASHANTHA FERNANDO                                                           *
 *                                                                                       *
 * LAST EDITED: 15/04/24                                                                 *
 *                                                                                       *
 * DESCRIPTION: Class file for implementing median estimate strategy                     *
 *****************************************************************************************/

package edu.curtin.app;

import java.util.*;

public class TakeMedianEstimate implements ReconciliationStrategy 
{
    @Override
    public int reconcile(List<Integer> estimates, Scanner sc) 
    {
        int middle;
        long leftSide, rightSide;

        Collections.sort(estimates); // Sort the estimates
        
        middle = estimates.size() / 2;
        
        if (estimates.size() % 2 == 1) 
        {
            return estimates.get(middle);
        } 
        else 
        {
            leftSide = estimates.get(middle - 1);
            rightSide = estimates.get(middle);
            return (int) ((leftSide + rightSide) / 2);
        }
    }
}