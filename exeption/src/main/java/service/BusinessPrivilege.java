package service;

import model.Ticket;

public class BusinessPrivilege implements ServicePrivilege{
    @Override
    public int calculateAccuredMiles(Ticket ticket, double dynamicModifier) {
        return (int) (ticket.getFlight().getBasePrice() * 0.3 * dynamicModifier);
    }

    @Override
    public String getBoardingPriority() {
        return "Priority Boarding (Zone A)"; //         return "Приоритетная посадка (Зона А)";
    }
}
