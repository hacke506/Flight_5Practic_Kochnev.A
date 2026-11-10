package service;
import com.example.exception.BaggageLimitException;
import com.example.exception.FlightValidationExpection;
import com.example.exception.OverbookingException;

import model.*;
import storage.*;


public class FlightManager {
    private FlightStorage flightStorage;
    private BookingStorage bookingStorage;

    public FlightManager(FlightStorage flightStorage, BookingStorage bookingStorage) {
        this.flightStorage = flightStorage;
        this.bookingStorage = bookingStorage;
    }

public  double calculateDynamicModifier(Flight flight) {
        int targetBooking = 0;
        for ( int i = 0; i < bookingStorage.getSize(); i++) {
            Booking b = bookingStorage.getByIndex(i);
            if (b.getTicket().getFlight().equals(flight)) {
                targetBooking++;

            }

        }
        double capacity = flight.getAircraft().getCapacity();
        double loadFactor = targetBooking / capacity;

        if (loadFactor > 0.8) return  1.5;
        if (loadFactor > 0.5) return 1.2;
        return 1.0;



    }

    public Booking createBooking(Flight flight,Passenger passenger, String classType) {
        int currentBookings = 0;
        for( int i = 0; i < bookingStorage.getSize(); i++) {
            if (bookingStorage.getByIndex(i).getTicket().getFlight().equals(flight)) {
                currentBookings++;
            }
        }

        if(currentBookings >= flight.getAircraft().getCapacity() * 1.05) {
            throw new OverbookingException("Rejected: Critical overbooking limit (105%) exceeded for flight " + flight.getFlightNumber()); //
            // throw new OverbookingException("Отказ: Лимит критического овербукинга (105%) превышен для рейса " + flight.getFlightNumber());
        }

        String tId = "ТКТ-" + System.currentTimeMillis() % 100000;
        Ticket ticket;
        ServicePrivilege privilege;

        if (classType.equalsIgnoreCase("Business")) {
       ticket = new BusinessClassTicket(tId, flight, passenger);
       privilege = new BusinessPrivilege();
        } else if (classType.equalsIgnoreCase("First")) {
            ticket = new FirstClassTicket(tId, flight, passenger);
            privilege = new BusinessPrivilege();
        } else {
            ticket = new EconomyTicket(tId, flight, passenger);
            privilege = new EconomyPrivilage();
        }

        double modifier = calculateDynamicModifier(flight);
        int addMiles = privilege.calculateAccuredMiles(ticket, modifier);
        passenger.addBonusMiles(addMiles);

        Booking booking = new Booking("BKG-" + (bookingStorage.getSize() + 1001), ticket);

        Logger.log("Booking created: " + booking.getId() + " (" + classType + " ). Miles accrued: " + addMiles); // Создано бронирование Miles accrued: ----> Начислено миль
        return booking;


    }

    public double registerBaggage(String bookingId, double weight, boolean isFragile) {
        Booking booking = bookingStorage.getById(bookingId);
        if (booking == null) return 0.0;

        Ticket ticket = booking.getTicket();

        if (weight > ticket.getMaxBaggageWeight() + 15.0) {
            throw new BaggageLimitException(String.format("Critical baggage limit exceeded! Max allowed with extra fee: %.1f kg, presented: %.1f kg", // "Превышение критического лимита багажа! Макс с доплатой: %.1f кг, предъявлено: %.1f кг"
                    ticket.getMaxBaggageWeight() + 15.0, weight));

        }
        ticket.SetBaggage(new Baggage(weight, isFragile));
        booking.setCheckedIn(true);

        double excess = weight - ticket.getMaxBaggageWeight();
        double penalty = excess > 0? excess * ticket.getOverweightChargePerKg() : 0.0;

        Logger.log("Passenger" + ticket.getPassenger().getFullName() + "checked in. Extra baggage fee: $" + penalty); // "Пассажир",  "Зарегестрирован. Доплата"
        return penalty;


    }

    public void changeFlightStatus(String flightNumber, FlightStatus newStatus) throws FlightValidationExpection {
        Flight flight = flightStorage.getByNumber(flightNumber);
        if (flight == null) throw new FlightValidationExpection("Flight number " + flightNumber + " is not registered in the system."); //"Рейс номер ", " не зарегистрирован в системе."

        flight.setStatus(newStatus);
        Logger.log("Flight status" + flightNumber + " changed to " + newStatus); // "Статус рейса", // " изменен на "

        System.out.println("\n [Sending Passenger Notifications for Flight" + flightNumber + "]"); // "\n [Рассылка уведомлений пассажирам рейса"
        for (int i = 0; i < bookingStorage.getSize(); i++) {
            Booking b = bookingStorage.getByIndex(i);
            if ( b.getTicket().getFlight().getFlightNumber().equals(flightNumber)) {
             System.out.printf("-> Dear %s, your flight status has been updated to: %s\\n", // "-> Уважаемый(ая) %s, ваш статус рейса обновлен на: %s\n"
                    b.getTicket().getPassenger().getFullName(),newStatus );
            }
        }
    }

}
