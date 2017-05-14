/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

/**
 *
 * @author stephan
 */
public interface PredicateBuilder<T>
{
    ConnectionBuilder Equals(T value);
    ConnectionBuilder Contains(T value);
    ConnectionBuilder Less(T value);
    ConnectionBuilder Greater(T value);
    ConnectionBuilder GreaterLess(T value);
    
    PredicateBuilder not();
    
}
