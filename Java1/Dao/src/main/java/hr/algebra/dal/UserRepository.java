/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hr.algebra.dal;

import hr.algebra.model.User;
import java.util.Optional;

/**
 *
 * @author ana
 */
public interface UserRepository {
    int userExists(String userName) throws Exception;
    int register(String username, String password, int role) throws Exception;
    Optional<User> login(String username, String password) throws Exception;
}
