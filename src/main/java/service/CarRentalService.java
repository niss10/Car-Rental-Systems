package service;

import java.time.LocalDateTime;
import java.util.List;

import entity.CarType;
import entity.Reservation;
import repository.CarRepository;
import repository.ReservationRepository;

public class CarRentalService {

    private CarRepository carRepository;
    private ReservationRepository reservationRepository;
    private int reservationIdCounter = 1;

    public CarRentalService(CarRepository carRepo, ReservationRepository resRepo) {
        this.carRepository = carRepo;
        this.reservationRepository = resRepo;
    }

    public Reservation reserveCar(int customerId, CarType type,
                                  LocalDateTime startDate, int numberOfDays) {

        if (!isAvailable(type, startDate, numberOfDays)) {
            throw new RuntimeException("No cars available");
        }

        Reservation reservation = new Reservation(
                reservationIdCounter++,
                customerId,
                type,
                startDate,
                numberOfDays
        );

        reservationRepository.save(reservation);
        return reservation;
    }

    private boolean isAvailable(CarType type, LocalDateTime startDate, int days) {
        LocalDateTime endDate = startDate.plusDays(days);

        // List<Car> cars = carRepository.getCarsByType(type);
        // O(1)
        int totalCars = carRepository.getCarCountByType(type);

        // O(k) instead of O(n)
        List<Reservation> reservations = reservationRepository.findReservationByCarType(type);

        int bookedCars = 0;

        for (Reservation r : reservations) {
            if (r.getCarType() != type || !"ACTIVE".equals(r.getStatus())) continue;

            boolean noOverlap =
                    endDate.isBefore(r.getStartDate()) ||
                    startDate.isAfter(r.getEndDate());

            if (!noOverlap) {
                bookedCars++;
            }
        }

        return bookedCars < totalCars;
    }
}