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
public interface DBEntity
{
    public boolean isChanged();
    public abstract int getLocalHash();
    public abstract int getRemoteHash();
    
}
