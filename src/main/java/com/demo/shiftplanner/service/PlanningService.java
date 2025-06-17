package com.demo.shiftplanner.service;

import com.demo.shiftplanner.exceptions.BusinessException;
import com.demo.shiftplanner.model.Assignment;
import com.demo.shiftplanner.model.ShiftType;
import com.demo.shiftplanner.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Plans the work schedule for a specific date by assigning two shifts(EARLY or LATE)
 * to employees, taking into account their specific wishes if available
 */
public class PlanningService {

WishService wishService = new WishService();

    public List<Assignment> planForDate(User user, LocalDate date) {
        if (date == null) {
            throw new BusinessException("Date cannot be null");
        }
        if (date.isBefore(LocalDate.now())) {
            throw new BusinessException("Cannot planning for a past date");
        }

        List<User> employees = new ArrayList<>();
        if (employees.size() < 2) {
            throw new BusinessException("Need at least 2 employees for planing a day");
        }

        List<Long> allIds = employees.stream()
                .map(User::getId)
                .collect(Collectors.toList());

        List<Long> wishEarlyShift = wishService.getWishesByDateAndShift(date, ShiftType.EARLY)
                .stream()
                .map(wish->wish.getEmployeeId())
                .collect(Collectors.toList());

        List<Long> wishLateShift = wishService.getWishesByDateAndShift(date, ShiftType.LATE)
                .stream()
                .map(wish -> wish.getEmployeeId())
                .collect(Collectors.toList());

        List<Assignment> result = new ArrayList<>();
        return result;
    }

}
