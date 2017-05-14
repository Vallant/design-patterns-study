/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import java.util.ArrayList;

/**
 *
 * @author stephan
 */
public interface ConnectionBuilder
{
    void toList(ArrayList<?> list);
    
    PredicateBuilder orWhere(String parameterName);
    PredicateBuilder andWhere(String parameterName);
}
