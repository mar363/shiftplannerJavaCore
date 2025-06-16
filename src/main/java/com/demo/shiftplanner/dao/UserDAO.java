package com.demo.shiftplanner.dao;

import com.demo.shiftplanner.exceptions.DataAccessException;
import com.demo.shiftplanner.model.Role;
import com.demo.shiftplanner.model.User;
import com.demo.shiftplanner.util.DBUtil;

import java.sql.*;

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
            throw new DataAccessException("Error saving user to database");
        }
    }
}
