/*****************************************************************************************
 * AUTHOR: PRASHANTHA FERNANDO                                                           *
 *                                                                                       *
 * LAST EDITED: 10/04/24                                                                 *
 *                                                                                       *
 * DESCRIPTION: Class file for implementing highest estimate strategy                    *
 *****************************************************************************************/

package edu.curtin.app;

import java.util.*;

public class TakeHighestEstimate implements ReconciliationStrategy 
{
    @Override
    public int reconcile(List<Integer> estimates, Scanner sc) 
    {
        return Collections.max(estimates); // Return the max estimate
    }
}