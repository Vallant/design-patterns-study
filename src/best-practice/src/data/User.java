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
    public static enum ROLE
    {
        ADMIN,
        USER
    }
    
    private int hash;
    private int id; 
    private String userName;
    
    private String firstName;
    private String lastName;
    private ROLE role;
    private String email;

    public User(int hash, int id, String userName, String firstName, String lastName, ROLE role, String email)
    {
        this.hash = hash;
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.email = email;
    }

    public User(String userName, String firstName, String lastName, ROLE role, String email)
    {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.email = email;
    }
     
    //Getters, Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ROLE getRole() {
        return role;
    }

    public void setRole(ROLE role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
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
                append(userName).
                append(email).
                hashCode();
    }

    @Override
    public int getRemoteHash()
    {
        return hash;
    }
}
