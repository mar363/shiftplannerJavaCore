package com.demo.shiftplanner.controller;

import com.demo.shiftplanner.exceptions.BusinessException;
import com.demo.shiftplanner.model.Role;
import com.demo.shiftplanner.model.ShiftType;
import com.demo.shiftplanner.model.User;
import com.demo.shiftplanner.service.UserService;
import com.demo.shiftplanner.service.WishService;
import com.demo.shiftplanner.util.DatabaseInitializer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class AppMenu {
    private final UserService userService = new UserService();
    private final WishService wishService = new WishService();

    private final Scanner scanner = new Scanner(System.in);

    public AppMenu() {

    }

    public void start() {
        DatabaseInitializer.init();
        System.out.println("Database initialized");

        while (true) {
            System.out.println("\n===PLANNING SCHEDULE MENU===");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Chose your option: ");
            String opt = scanner.nextLine().trim();

            if (opt.equals("1")) {
               handleLogin();
            } else if(opt.equals("2")) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid option");
            }
        }
        scanner.close();

    }
private void handleLogin(){
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        try {
            User user = userService.login(username, password);
            System.out.println("Welcome, " + user.getUsername());

            if(user.getRole() == Role.ADMIN) {

            } else {

            }
        } catch (BusinessException e) {
            System.out.println("Access denied: " + e.getMessage());
        }
}


    private void addUser(Scanner scanner) {
        System.out.println("Username: ");
        String username = scanner.nextLine();
        System.out.println("Password: ");
        String password = scanner.nextLine();

        try {
            User user = userService.createEmployee(username, password);
            System.out.println("Employee added");
        } catch (BusinessException e) {
            System.out.println("Error");
        }
    }

    private void addWish(User user) {
        System.out.println("Date (DD-MM-YYYY)");
        String dateLine = scanner.nextLine().trim();

        try {
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate date = LocalDate.parse(dateLine);
            System.out.println("Shift EARLY/LATE: ");
            ShiftType shiftType = ShiftType.valueOf(scanner.nextLine().trim().toUpperCase());
            wishService.addWish(user.getId(), date, shiftType);
        } catch (DateTimeParseException dte) {
            System.out.println("Invalid format date. Use: dd-MM-yyyy");
        } catch (IllegalArgumentException iae) {
            System.out.println("Invalid Shift. Chose between EARLY and LATE.");
        } catch (BusinessException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
