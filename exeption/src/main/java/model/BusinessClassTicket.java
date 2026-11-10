package model;

public class BusinessClassTicket extends Ticket{
    public BusinessClassTicket(String ticketNumber, Flight flight, Passenger passenger) {
       super(ticketNumber, flight, passenger);
    }
    @Override
    public double calculateFinalPrice(double dynamicModifier) {
        return getFlight().getBasePrice() * 2.0 * dynamicModifier;
    }

    @Override
    public double getMaxBaggageWeight() {
        return 32.0;
    }

    @Override
    public double getOverweightChargePerKg() {
        return 300.0;
    }

    @Override
    public String toString() {
        return "[Business] Ticket №" + getTicketNumber() + "  for  " + getPassenger().getFullName();
    }
}
