package com.demo.shiftplanner.service;

import com.demo.shiftplanner.dao.AssignmentDAO;
import com.demo.shiftplanner.exceptions.BusinessException;
import com.demo.shiftplanner.model.Assignment;
import com.demo.shiftplanner.model.Role;
import com.demo.shiftplanner.model.ShiftType;
import com.demo.shiftplanner.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Plans the work schedule for a specific date by assigning two shifts(EARLY or LATE)
 * to employees, taking into account their specific wishes if available
 * Only ADMIN users are allowed to use this feature
 * At least 2 employee are required per day
 * The first employee who wishes for EARLY shift will be assigned to EARLY
 * The LATE shift will be assigned to the first available employee(not already on EARLY)
 */
public class PlanningService {

    WishService wishService = new WishService();
    AssignmentDAO assignmentDAO = new AssignmentDAO();
    UserService userService = new UserService();

    public List<Assignment> planForDate(User user, LocalDate date) {
        if (date == null || user.getRole() != Role.ADMIN) {
            throw new BusinessException("Only ADMINs can plan the schedule");
        }
        if (date.isBefore(LocalDate.now())) {
            throw new BusinessException("Cannot planning for a past date");
        }

        List<User> employees = userService.getAllEmployees();
        if (employees.size() < 2) {
            throw new BusinessException("Need at least 2 employees for planing a day");
        }

        List<Long> allIds = employees.stream()
                .map(User::getId)
                .collect(Collectors.toList());

        List<Long> wishEarlyShift = wishService.getWishesByDateAndShift(date, ShiftType.EARLY)
                .stream()
                .map(wish -> wish.getEmployeeId())
                .collect(Collectors.toList());

        List<Long> wishLateShift = wishService.getWishesByDateAndShift(date, ShiftType.LATE)
                .stream()
                .map(wish -> wish.getEmployeeId())
                .collect(Collectors.toList());


        Long chosenEarly = !wishEarlyShift.isEmpty() ? wishEarlyShift.get(0) : allIds.get(0);

//Looking for the first employee available that are not already in EARLY
        List<Long> availableForLate = allIds.stream()
                .filter(id -> !id.equals(chosenEarly))
                .collect(Collectors.toList());

        if (availableForLate.isEmpty()) {
            throw new BusinessException("There are not another employee available for LATE shift");
        }

        Long chosenLate;
        if (!wishLateShift.isEmpty()) {
            Long emp = null;
            for (Long id : wishLateShift) {
                if (!id.equals(chosenEarly)) {
                    emp = id;
                    break;
                }
            }
            chosenLate = (emp != null) ? emp : availableForLate.get(0);
        } else {
            chosenLate = availableForLate.get(0);
        }

        Assignment a1 = new Assignment(chosenEarly, date, ShiftType.EARLY);
        assignmentDAO.save(a1);
        Assignment a2 = new Assignment(chosenLate, date, ShiftType.LATE);
        assignmentDAO.save(a2);
        return List.of(a1, a2);
    }

}
