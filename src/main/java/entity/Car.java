package entity;

public class Car {
    private int id;
    private CarType type;
    private String status;

    public Car(int id, CarType type, String status) {
        this.id = id;
        this.type = type;
        this.status = status;
    }

    public int getId() { return id; }
    public CarType getType() { return type; }
    public String getStatus() { return status; }
}