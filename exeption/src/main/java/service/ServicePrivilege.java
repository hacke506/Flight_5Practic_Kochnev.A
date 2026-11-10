package service;

import model.Ticket;

public interface ServicePrivilege {
    int calculateAccuredMiles(Ticket ticket, double dynamicModifier);
    String getBoardingPriority();
}
