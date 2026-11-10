package model;

public enum FlightStatus {
    SCHEDULED(0),
    DELAYED(15),
    CANCELLED(100);

    private final int baseDelayPenalty;

    FlightStatus(int baseDelayPenalty) {
        this.baseDelayPenalty = baseDelayPenalty;
    }

    public int getBaseDelayPenalty() {
        return baseDelayPenalty;
    }
}
