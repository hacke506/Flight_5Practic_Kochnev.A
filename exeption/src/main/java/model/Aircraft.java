package model;

public class Aircraft {
    private String tailNumber;
    private String model;
    private int capacity;

    public Aircraft(String tailNumber, String model, int capacity) {
        this.tailNumber = tailNumber;
        this.model = model;
        setCapacity(capacity);
    }

    public String getTailNumber() {
        return tailNumber;
    }

    public String getModel() {
        return model;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        if(capacity > 0) this.capacity = capacity;
    }

    @Override
    public String toString() {
        return model + "( " + tailNumber + ", Мест" + capacity + ")";
    }
}
