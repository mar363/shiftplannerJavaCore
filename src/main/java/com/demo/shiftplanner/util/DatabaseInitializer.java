package com.demo.shiftplanner.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void init() {
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS users (" +
                            "id IDENTITY PRIMARY KEY," +
                            "username VARCHAR(200) UNIQUE NOT NULL," +
                            "password VARCHAR(200) NOT NULL," +
                            "role VARCHAR(20) NOT NULL" +
                            ")"
            );

            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS wishes (" +
                            "id IDENTITY PRIMARY KEY," +
                            "employee_id BIGINT NOT NULL," +
                            "date DATE NOT NULL," +
                            "shift_type VARCHAR(200) NOT NULL," +
                            "CONSTRAINT fk_wish_user FOREIGN KEY(employee_id) REFERENCES users(id) ON DELETE CASCADE," +
                            "CONSTRAINT uc_wish UNIQUE(employee_id, date)" +
                            ")"
            );

            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS assignments (" +
                            "id IDENTITY PRIMARY KEY," +
                            "employee_id BIGINT NOT NULL," +
                            "date DATE NOT NULL," +
                            "shift_type VARCHAR(200) NOT NULL," +
                            "CONSTRAINT fk_assign_user FOREIGN KEY(employee_id) REFERENCES users(id) ON DELETE CASCADE," +
                            "CONSTRAINT uc_assign_employee_date UNIQUE(employee_id, date)," +
                            "CONSTRAINT uc_assign_dat_shift UNIQUE(date, shift_type)" +
                            ")"
            );
            String sql = "INSERT INTO users(username, password, role) " +
                    "SELECT 'admin', 'admin123', 'ADMIN' " +
                    "WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin')";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
