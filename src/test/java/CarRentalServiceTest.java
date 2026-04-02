import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entity.Car;
import entity.CarType;
import entity.Reservation;
import repository.CarRepository;
import repository.ReservationRepository;
import service.CarRentalService;

public class CarRentalServiceTest {

    private CarRepository carRepository;
    private ReservationRepository reservationRepository;
    private CarRentalService service;

    @BeforeEach
    public void setup() {
        carRepository = new CarRepository();
        reservationRepository = new ReservationRepository();
        service = new CarRentalService(carRepository, reservationRepository);

        // Add some cars
        carRepository.addCar(new Car(1, CarType.SEDAN, "AVAILABLE"));
        carRepository.addCar(new Car(2, CarType.SEDAN, "AVAILABLE"));
        carRepository.addCar(new Car(3, CarType.SUV, "AVAILABLE"));
    }

    @Test
    public void testReserveCarSuccess() {
        LocalDateTime startDate = LocalDateTime.now();

        Reservation reservation = service.reserveCar(
                101,
                CarType.SEDAN,
                startDate,
                2
        );

        assertNotNull(reservation);
        assertEquals(CarType.SEDAN, reservation.getCarType());
        assertEquals("ACTIVE", reservation.getStatus());
    }

    @Test
    public void testReserveCarFailsWhenNoCarsAvailable() {
        LocalDateTime startDate = LocalDateTime.now();

        // Book all available SEDAN cars (2 cars)
        service.reserveCar(1, CarType.SEDAN, startDate, 2);
        service.reserveCar(2, CarType.SEDAN, startDate, 2);

        // Third booking should fail
        Exception exception = assertThrows(RuntimeException.class, () -> {
            service.reserveCar(3, CarType.SEDAN, startDate, 2);
        });

        assertEquals("No cars available", exception.getMessage());
    }

    @Test
    public void testReserveCarDifferentType() {
        LocalDateTime startDate = LocalDateTime.now();

        // SUV should still be available
        Reservation reservation = service.reserveCar(
                200,
                CarType.SUV,
                startDate,
                1
        );

        assertNotNull(reservation);
        assertEquals(CarType.SUV, reservation.getCarType());
    }

    @Test
    public void testReservationOverlap() {
        LocalDateTime startDate = LocalDateTime.now();

        // First booking
        service.reserveCar(1, CarType.SEDAN, startDate, 3);

        // Second booking overlapping (should succeed because 2 cars exist)
        Reservation second = service.reserveCar(2, CarType.SEDAN, startDate.plusDays(1), 2);

        assertNotNull(second);

        // Third overlapping booking should fail
        assertThrows(RuntimeException.class, () -> {
            service.reserveCar(3, CarType.SEDAN, startDate.plusDays(1), 2);
        });
    }

    @Test
    public void testNoOverlapReservation() {
        LocalDateTime startDate = LocalDateTime.now();

        // First booking
        service.reserveCar(1, CarType.SEDAN, startDate, 2);

        // Second booking after end date (no overlap)
        Reservation second = service.reserveCar(
                2,
                CarType.SEDAN,
                startDate.plusDays(3),
                2
        );

        assertNotNull(second);
    }
}