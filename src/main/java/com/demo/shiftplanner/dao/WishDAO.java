package com.demo.shiftplanner.dao;

import com.demo.shiftplanner.exceptions.BusinessException;
import com.demo.shiftplanner.exceptions.DataAccessException;
import com.demo.shiftplanner.model.Wish;
import com.demo.shiftplanner.util.DBUtil;
import com.demo.shiftplanner.util.DatabaseInitializer;

import java.sql.*;

public class WishDAO {
    public Wish save(Wish wish) {
        String sql = "INSERT INTO wishes VALUES(?,?,?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, wish.getEmployeeId());
            stmt.setDate(2, Date.valueOf(wish.getDate()));
            stmt.setString(3, wish.getShiftType().name());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new BusinessException("Creating user failed, no rows affected");
            }
            try(ResultSet rs = stmt.getGeneratedKeys()) {
                if(rs.next()) {
                    wish.setId(rs.getLong(1));
                }
            }
            return wish;
        } catch (SQLException e) {
            throw new DataAccessException("Error inserting in wishes table");
        }
    }


}
