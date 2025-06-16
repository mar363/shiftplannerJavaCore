package com.demo.shiftplanner.model;

import java.time.LocalDate;

public class Wish {
    private Long id;
    private Long employeeId;
    private LocalDate date;
    private String shiftType;

    public Wish() {
    }

    public Wish(Long id, Long employeeId, LocalDate date, String shiftType) {
        this.id = id;
        this.employeeId = employeeId;
        this.date = date;
        this.shiftType = shiftType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getShiftType() {
        return shiftType;
    }

    public void setShiftType(String shiftType) {
        this.shiftType = shiftType;
    }

    @Override
    public String toString() {
        return "Wish{" +
                "id=" + id +
                ", employeeId=" + employeeId +
                ", date=" + date +
                ", shiftType='" + shiftType + '\'' +
                '}';
    }
}
