package model;

public class EconomyTicket extends Ticket {
    public EconomyTicket(String ticketNumber, Flight flight, Passenger passenger) {
        super(ticketNumber, flight, passenger);
    }

    @Override
    public double calculateFinalPrice(double dynamicModifier) {
        return getFlight().getBasePrice() * dynamicModifier;
    }

    @Override
    public double getMaxBaggageWeight() {
        return 23.0;
    }

    @Override
    public double getOverweightChargePerKg() {
        return 150.0;
    }

    @Override
    public String toString() {
        return "[Economy] Ticket #" + getTicketNumber() + " for " + getPassenger().getFullName();
    }
}
