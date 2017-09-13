/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

/**
 * @author stephan
 */
public interface DBEntity
{
  boolean isChanged();

  int getLocalHash();

  int getRemoteHash();

  void setRemoteHash(int hash);

}
