/*****************************************************************************************
 * AUTHOR: PRASHANTHA FERNANDO                                                           *
 *                                                                                       *
 * LAST EDITED: 10/04/24                                                                 *
 *                                                                                       *
 * DESCRIPTION: Interface for estimation strategies                                      *
 *****************************************************************************************/

package edu.curtin.app;

import java.util.*;

public interface ReconciliationStrategy 
{
    int reconcile(List<Integer> estimates, Scanner sc);
}