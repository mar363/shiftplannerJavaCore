package com.demo.shiftplanner.dao;

import com.demo.shiftplanner.exceptions.DataAccessException;
import com.demo.shiftplanner.model.Role;
import com.demo.shiftplanner.model.User;
import com.demo.shiftplanner.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    /* save
     findById
     findByUsername
     getAllEmployees */
    public User save(User user) {
        String sql = "INSERT INTO users(username, password, role) VALUES(?,?,?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole().name());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new DataAccessException("Creating user failed, no rows affected");
            }

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getLong(1));
                } else {
                    throw new DataAccessException("Creating user failed!");
                }
            }
            return user;
        } catch (SQLException e) {
            throw new DataAccessException("Error saving user to database", e);
        }
    }


    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Long id = rs.getLong("id");
                    String name = rs.getString("username");
                    String pass = rs.getString("password");
                    Role role = Role.valueOf(rs.getString("role"));
                    return new User(id, name, pass, role);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error finding username", e);
        }
        return null;
    }
public List<User> getAllEmployees() {
        String sql = "SELECT * FROM users WHERE role=?";
        List<User> users = new ArrayList<>();

        try(Connection conn = DBUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1,Role.EMPLOYEE.name());
           try(ResultSet rs = stmt.executeQuery()) {
               while (rs.next()) {
                   Long id = rs.getLong("id");
                   String name = rs.getString("username");
                   String pass = rs.getString("password");
                   Role role = Role.valueOf(rs.getString("role"));
                   users.add(new User(id, name, pass, role));
               }
           }

        } catch (SQLException e) {
            throw new DataAccessException("Error when try to get all employee", e);
        }
    return  users;
}
}
