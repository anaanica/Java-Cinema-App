/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

/**
 *
 * @author ana
 */
public class User {

    private int id;
    private String username;
    private String password;
    private int roleID;

    public User(String username, String password, int roleID) {
        this.username = username;
        this.password = password;
        this.roleID = roleID;
    }

    public User(int id, String username, String password, int roleID) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roleID = roleID;
    }

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public int getIdUser() {
        return id;
    }

    public void setIdUser(int id) {
        this.id = id;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    @Override
    public String toString() {
        return username; 
    }
}
