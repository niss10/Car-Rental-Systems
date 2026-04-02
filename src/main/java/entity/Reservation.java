package entity;

import java.time.LocalDateTime;

public class Reservation {
    private int id;
    private int customerId;
    private Integer carId;
    private CarType carType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;

    public Reservation(int id, int customerId, CarType carType,
                       LocalDateTime startDate, int numberOfDays) {
        this.id = id;
        this.customerId = customerId;
        this.carType = carType;
        this.startDate = startDate;
        this.endDate = startDate.plusDays(numberOfDays);
        this.status = "ACTIVE";
    }

    public CarType getCarType() { return carType; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public String getStatus() { return status; }
}