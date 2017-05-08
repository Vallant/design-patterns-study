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
    public int getLocalHash();
    public int getRemoteHash();
    public void setRemoteHash(int hash);
    
}
