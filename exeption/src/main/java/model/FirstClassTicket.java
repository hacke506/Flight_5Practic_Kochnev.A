package model;

public class FirstClassTicket extends Ticket{
    public FirstClassTicket(String ticketNumber, Flight flight, Passenger passenger) {
        super(ticketNumber,flight,passenger);
    }

    @Override
    public double calculateFinalPrice(double dynamicModifier) {
        return getFlight().getBasePrice() * 3.5 * dynamicModifier;
    }



    @Override
    public double getMaxBaggageWeight() {
        return 50.0;
    }

    @Override
    public double getOverweightChargePerKg() {
        return 500.0;
    }

    @Override
    public String toString () {
        return "[First Class] Ticket №" + getTicketNumber() + " for " + getPassenger().getFullName(); // return "[Первый Класс] Билет №" + getTicketNumber() + " на имя " + getPassenger().getFullName(); for = на имя;
    }
}
