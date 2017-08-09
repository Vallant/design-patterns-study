/*
 * Copyright (C) 2017 stephan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package data;

import db.interfaces.DBEntity;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author stephan
 */

public class User implements DBEntity
{


  private int    remoteHash;
  private String loginName;
  private String firstName;
  private String lastName;
  private ROLE   role;
  private String email;
  private byte[] password;
  private byte[] salt;
  private char[] newPassword;
  private char[] newPasswordAgain;
  private char[] oldPassword;
  public User(int remoteHash, String loginName, String firstName, String lastName, ROLE role, String email,
              byte[] password, byte[] salt)
  {
    this.remoteHash = remoteHash;
    this.loginName = loginName;
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = role;
    this.email = email;
    this.password = password;
    this.salt = salt;
  }

  public User(String loginName, String firstName, String lastName, ROLE role, String email, byte[] password,
              byte[] salt)
  {
    this.loginName = loginName;
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = role;
    this.email = email;
    this.password = password;
    this.salt = salt;
  }

  public User(String loginName, String firstName, String lastName, ROLE role, String email, char[] password,
              char[] passwordAgain)
  {
    this.loginName = loginName;
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = role;
    this.email = email;
    this.newPassword = password;
    this.newPasswordAgain = passwordAgain;
  }

  public char[] getOldPassword()
  {
    return oldPassword;
  }

  public void setOldPassword(char[] oldPassword)
  {
    this.oldPassword = oldPassword;
  }

  public char[] getNewPasswordAgain()
  {
    return newPasswordAgain;
  }

  public byte[] getPassword()
  {
    return password;
  }

  public void setPassword(byte[] password)
  {
    this.password = password;
  }

  public byte[] getSalt()
  {
    return salt;
  }

  public void setSalt(byte[] salt)
  {
    this.salt = salt;
  }

  public String getLoginName()
  {
    return loginName;
  }

  public void setLoginName(String userName)
  {
    this.loginName = userName;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }

  public ROLE getRole()
  {
    return role;
  }

  public void setRole(ROLE role)
  {
    this.role = role;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  @Override
  public boolean isChanged()
  {
    return getLocalHash() != getRemoteHash();
  }

  @Override
  public int getLocalHash()
  {
    return new HashCodeBuilder().
      append(firstName).
      append(lastName).
      append(role).
      append(loginName).
      append(email).
      hashCode();
  }

  @Override
  public int getRemoteHash()
  {
    return remoteHash;
  }

  @Override
  public void setRemoteHash(int hash)
  {
    this.remoteHash = hash;
  }

  public char[] getNewPassword()
  {
    return newPassword;
  }

  public void setNewPassword(char[] newPassword)
  {
    this.newPassword = newPassword;
  }

  public enum ROLE
  {
    ADMIN,
    USER
  }
}
