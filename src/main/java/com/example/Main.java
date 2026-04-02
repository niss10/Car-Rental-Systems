package com.example;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import entity.Car;
import entity.CarType;
import entity.Reservation;
import repository.CarRepository;
import repository.ReservationRepository;
import service.CarRentalService;

public class Main {
    public static void main(String[] args) {
        CarRepository carRepo = new CarRepository();
        ReservationRepository resRepo = new ReservationRepository();

        // Simple test case to demonstrate functionality
        // Add cars
        // carRepo.addCar(new Car(1, CarType.SEDAN, "AVAILABLE"));
        // carRepo.addCar(new Car(2, CarType.SEDAN, "AVAILABLE"));

        // CarRentalService service = new CarRentalService(carRepo, resRepo);

        // // Make reservation
        // service.reserveCar(1, CarType.SEDAN, LocalDateTime.now(), 2);

        // System.out.println("Reservation successful!");
        // end simpe test case

        // interactive console or more complex test cases
        // Add cars
        carRepo.addCar(new Car(1, CarType.SEDAN, "AVAILABLE"));
        carRepo.addCar(new Car(2, CarType.SEDAN, "AVAILABLE"));
        carRepo.addCar(new Car(3, CarType.SUV, "AVAILABLE"));
        carRepo.addCar(new Car(4, CarType.VAN, "AVAILABLE"));

        CarRentalService service = new CarRentalService(carRepo, resRepo);
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        System.out.println("Welcome to Car Rental System!");

        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Reserve a car");
            System.out.println("2. Exit");

            String option = scanner.nextLine();

            if ("2".equals(option)) {
                System.out.println("Thank you for using Car Rental System. Goodbye!");
                break;
            }

            if ("1".equals(option)) {
                System.out.println("Available car types:");
                for (CarType type : CarType.values()) {
                    int count = carRepo.getCarCountByType(type);
                    System.out.println("- " + type + " (" + count + " total)");
                }

                System.out.print("Enter customer ID: ");
                int customerId = Integer.parseInt(scanner.nextLine());

                System.out.print("Enter car type (SEDAN/SUV/VAN): ");
                CarType type = CarType.valueOf(scanner.nextLine().toUpperCase());

                System.out.print("Enter reservation start date & time (yyyy-MM-dd HH:mm): ");
                LocalDateTime startDate = LocalDateTime.parse(scanner.nextLine(), formatter);

                System.out.print("Enter number of days: ");
                int numberOfDays = Integer.parseInt(scanner.nextLine());

                try {
                    Reservation reservation = service.reserveCar(customerId, type, startDate, numberOfDays);
                    System.out.println("Reservation successful! Details:");
                    System.out.println("Customer ID: " + customerId);
                    System.out.println("Car Type: " + type);
                    System.out.println("Start: " + reservation.getStartDate());
                    System.out.println("End: " + reservation.getEndDate());
                    System.out.println("Status: " + reservation.getStatus());
                } catch (RuntimeException e) {
                    System.out.println("Reservation failed: " + e.getMessage());
                }
            } else {
                System.out.println("Invalid option. Try again.");
            }
        }

        scanner.close();
    }
}