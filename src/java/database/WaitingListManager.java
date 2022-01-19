
package database;

import common.WaitingList;
import java.util.Collection;

/**
 * A <code>interface</code> that specifies the allowable operations on waiting 
 * list entries in the system.
 * 
 * @author Drew Wagner (2021)
 */
public interface WaitingListManager {
    public WaitingList addWaitingList(WaitingList waitingList);
    public boolean deleteWaitingList(WaitingList waitingList);
    public WaitingList updateWaitingList(WaitingList waitingList);
    public WaitingList getWaitingListById(int waitingListId);
    public Collection<WaitingList> getWaitingListsByCustomerId(int waitingListId);
    public Collection<WaitingList> getAllWaitingLists();
    public boolean deleteWaitingListById(int waitingListId);
}
