package com.demo.shiftplanner.service;

import com.demo.shiftplanner.dao.WishDAO;
import com.demo.shiftplanner.exceptions.BusinessException;
import com.demo.shiftplanner.exceptions.DataAccessException;
import com.demo.shiftplanner.model.ShiftType;
import com.demo.shiftplanner.model.Wish;

import java.time.LocalDate;

public class WishService {
    private WishDAO wishDAO = new WishDAO();

    public Wish addWish(Long empId, LocalDate date, ShiftType shiftType) {
        if (empId == null) {
            throw new BusinessException("Employee ID cannot be null");
        }
        if (date == null) {
            throw new BusinessException("Date cannot be null");
        }

        if (shiftType == null) {
            throw new BusinessException("Shift Type cannot be null");
        }
        if (date.isBefore(LocalDate.now())) {
            throw new BusinessException("Cannot set a schedule preference in the past");
        }
        Wish existing = wishDAO.fidByEmployeeAndDate(empId, date);
        if (existing != null) {
            throw new BusinessException("Your preference already exists for the selected date" + date);
        }
        Wish wish = new Wish(empId, date, shiftType);
        try {
            return wishDAO.save(wish);
        } catch (DataAccessException e) {
            throw new BusinessException("Error on saving your shift-date preference");
        }

    }

}
