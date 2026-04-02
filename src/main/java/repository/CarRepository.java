package repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Car;
import entity.CarType;

public class CarRepository {
    private List<Car> cars = new ArrayList<>();
    // Stores count of cars per type
    private Map<CarType, Integer> carCountMap = new HashMap<>();

    public void addCar(Car car) {
        cars.add(car);
        carCountMap.put(car.getType(), carCountMap.getOrDefault(car.getType(), 0) + 1);
    }

    public List<Car> getCarsByType(CarType type) {
        List<Car> result = new ArrayList<>();
        for (Car car : cars) {
            if (car.getType() == type) {
                result.add(car);
            }
        }
        return result;
    }

    public int getCarCountByType(CarType type) {
        return carCountMap.getOrDefault(type, 0);
    }
}