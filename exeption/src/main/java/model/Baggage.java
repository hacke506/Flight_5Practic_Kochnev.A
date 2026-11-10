package model;

public class Baggage {
    private double weight;
    private boolean isFragile;

    public Baggage(double weight, boolean isFragile) {
        this.weight = weight;
        this.isFragile = isFragile;
    }

   public double getWeight() {
        return weight;
    }

    public boolean isFragile() {
        return isFragile;
    }
}
