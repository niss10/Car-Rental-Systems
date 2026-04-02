package repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.CarType;
import entity.Reservation;

public class ReservationRepository {
    // private List<Reservation> reservations = new ArrayList<>();
    private Map<CarType, List<Reservation>> reservationMap = new HashMap<>();

    public void save(Reservation reservation) {
        CarType type = reservation.getCarType();

        reservationMap.putIfAbsent(type, new ArrayList<>());
        reservationMap.get(type).add(reservation);
    }

    public List<Reservation> findReservationByCarType(CarType type) {
        return reservationMap.getOrDefault(type, new ArrayList<>());
    }
}
