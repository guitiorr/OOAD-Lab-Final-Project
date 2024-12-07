package controller;


import connection.UserDAO;
import models.User;

import java.util.ArrayList;
import java.util.UUID;

public class UserController {

    private UserDAO userDAO;

    public UserController() {
        this.userDAO = new UserDAO();
    }

    // Refresh Table (View All Users)
    public ArrayList<User> getAllUser() {
        return userDAO.getAllUsers();
    }

    // Insert User
    public void insertUser(String id, String username, String phone, String address, String role, String password) {
        User newUser = new User(id, username, password, phone, address, role);
        userDAO.insertUser(newUser);
    }

    // Update User
    public void updateUser(String id, String username, String phone, String address, String role, String password) {
        User updatedUser = new User(id, username, password, phone, address, role);
        userDAO.updateUser(updatedUser);
    }

    // Delete User
    public void deleteUser(String userID) {
        userDAO.deleteUser(userID);
    }
}