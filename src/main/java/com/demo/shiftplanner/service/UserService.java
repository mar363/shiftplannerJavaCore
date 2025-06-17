package com.demo.shiftplanner.service;

import com.demo.shiftplanner.dao.UserDAO;
import com.demo.shiftplanner.exceptions.BusinessException;
import com.demo.shiftplanner.model.Role;
import com.demo.shiftplanner.model.User;

import java.util.List;

public class UserService {
    private final UserDAO userDAO = new UserDAO();


    public User login(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new BusinessException("Invalid username.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new BusinessException("Invalid password");
        }
        User user = userDAO.findByUsername(username);
        if (user == null) {
            throw new BusinessException("Invalid username/password");
        }
        return user;
    }

    public User createEmployee(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new BusinessException("Invalid Username!");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new BusinessException("Invalid password!");
        }
        if (userDAO.findByUsername(username) != null) {
            throw new BusinessException("User already exists in database.");
        }

        User user = new User(null, username, password, Role.EMPLOYEE);
        try {
            return userDAO.save(user);
        } catch (Exception e) {
            throw new BusinessException("Error when trying to create employee");
        }
    }

    public List<User> getAllEmployees() {
        try {
            return userDAO.getAllEmployees();
        } catch (Exception e) {
            throw new BusinessException("Error loading list of employees");
        }
    }
}
