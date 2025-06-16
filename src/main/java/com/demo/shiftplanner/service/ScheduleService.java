package com.demo.shiftplanner.service;

import com.demo.shiftplanner.dao.AssignmentDAO;
import com.demo.shiftplanner.exceptions.BusinessException;
import com.demo.shiftplanner.model.Assignment;

import java.time.LocalDate;
import java.util.List;

public class ScheduleService {
    private final AssignmentDAO assignmentDAO = new AssignmentDAO();

    public List<Assignment> getScheduleForDate(LocalDate date) {
        if(date==null) {
            throw new BusinessException("Date cannot be null");
        }
        return assignmentDAO.findByDate(date);
    }
}
