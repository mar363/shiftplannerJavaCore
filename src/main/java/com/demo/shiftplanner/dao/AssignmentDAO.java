package com.demo.shiftplanner.dao;

import com.demo.shiftplanner.exceptions.DataAccessException;
import com.demo.shiftplanner.model.Assignment;
import com.demo.shiftplanner.model.ShiftType;
import com.demo.shiftplanner.util.DBUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AssignmentDAO {
    private AssignmentDAO assignmentDAO = new AssignmentDAO();

    public Assignment save(Assignment a) {
        String sql = "INSERT INTO assignments (employee_id, date, shift_type) VALUES (?,?,?)";
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, a.getEmployeeId());
            stmt.setDate(2, Date.valueOf(a.getDate()));
            stmt.setString(3, a.getShiftType().name());
           int affectedRows= stmt.executeUpdate();

           if(affectedRows == 0) {
               throw new DataAccessException("Assignment cannot be added");
           }
            try(ResultSet rs = stmt.getGeneratedKeys()) {
                if(rs.next()) {
                    a.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error when saving Assignments");
        }
        return a;
    }

    public List<Assignment> findByDate(LocalDate date) {
        String sql = "SELECT * FROM assignments WHERE date=?";
        List<Assignment> assignments = new ArrayList<>();
        try(Connection conn = DBUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1,Date.valueOf("date"));

            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    Long id = rs.getLong("id");
                    Long empId = rs.getLong("employee_id");
                    String username = rs.getString("username");
                    ShiftType shiftType = ShiftType.valueOf(rs.getString("shift_type"));
                    assignments.add(new Assignment(id, empId, date, shiftType));
                }
            }

        }catch (SQLException e) {
            throw new DataAccessException("Error fetching data finding assignmens by date.");
        }
        return assignments;
    }
}
