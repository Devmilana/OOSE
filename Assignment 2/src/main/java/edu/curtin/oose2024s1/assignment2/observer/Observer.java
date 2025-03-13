package edu.curtin.oose2024s1.assignment2.observer;

// ***** Observer interface ***** //
public interface Observer<T> 
{
    void update(String event); // Update method
    void updateStatus(T status); // Update status method
}