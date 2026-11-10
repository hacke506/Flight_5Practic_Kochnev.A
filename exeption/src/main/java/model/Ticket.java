package model;

public abstract class Ticket {
    private String ticketNumber;
    private Flight flight;
    private Passenger passenger;
    private  Baggage baggage;

    public Ticket(String ticketNumber, Flight flight, Passenger passenger) {
        this.ticketNumber = ticketNumber;
        this.flight = flight;
        this.passenger = passenger;

    }

    public abstract double calculateFinalPrice(double dynamicModifier);

    public abstract double getMaxBaggageWeight();
    public abstract double getOverweightChargePerKg();

    public String getTicketNumber() {
        return ticketNumber;
    }

    public Flight getFlight() {
        return flight;
    }
    public Passenger getPassenger() {
        return passenger;
    }
    public Baggage getBaggage() {
        return baggage;
    }
    public void SetBaggage(Baggage baggage) {
        this.baggage = baggage;
    }
}


