package edu.curtin.oose2024s1.assignment2.observer;

import java.util.*;

// ***** Observer operations ***** //
public class ObserverOperations<T> 
{
    private List<Observer<T>> observers = new ArrayList<>();

    // ***** Adds observer ***** //
    public void addObserver(Observer<T> observer) 
    {
        observers.add(observer);
    }

    // ***** Notify observers of bike shop events ***** //
    public void notifyObservers(String event) 
    {
        for (Observer<T> observer : observers) 
        {
            observer.update(event);
        }
    }

    // ***** Notify observers of bike shop status ***** //
    public void notifyObservers(T status) 
    {
        for (Observer<T> observer : observers) 
        {
            observer.updateStatus(status);
        }
    }

    // ***** Return list of observers ***** //
    public List<Observer<T>> getObservers() 
    {
        return observers;
    }
}