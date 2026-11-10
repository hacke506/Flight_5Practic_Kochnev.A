package service;

import model.Ticket;
public class EconomyPrivilage implements ServicePrivilege{
    @Override
    public int calculateAccuredMiles(Ticket ticket, double dynamicModifer) {
        return (int) (ticket.getFlight().getBasePrice() * 0.1);

    }

    @Override
    public String getBoardingPriority() {
        return  "Regular Queue (Zone C)";  //  return  "Обычная очередь (Зона С)";
    }
}
