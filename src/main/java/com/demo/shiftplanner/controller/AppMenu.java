package com.demo.shiftplanner.controller;

import com.demo.shiftplanner.exceptions.BusinessException;
import com.demo.shiftplanner.model.Role;
import com.demo.shiftplanner.model.User;
import com.demo.shiftplanner.service.UserService;
import com.demo.shiftplanner.util.DatabaseInitializer;

import java.util.Scanner;

public class AppMenu {
    private final UserService userService = new UserService();

    public AppMenu() {

    }

    public void start() {
        DatabaseInitializer.init();
        System.out.println("Database initialized");

        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("\n===PLANNING SCHEDULE MENU===");
            System.out.println("1. Add new user");
            System.out.println("0. Exit");
            System.out.println("Chose your option: ");
            int opt = scanner.nextInt();
            scanner.nextLine();

            if(opt==1) {
                addUser(scanner);
            } else if(opt==0) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid option");
            }
        }
scanner.close();

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
}
