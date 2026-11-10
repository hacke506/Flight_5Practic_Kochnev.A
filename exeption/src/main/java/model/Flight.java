package model;

import java.time.LocalDate;

public class Flight {
    private String flightNumber;
    private Airport origin;
    private Airport destination;
    private LocalDate departureDate;
    private Aircraft aircraft;
    private double basePrice;
    private FlightStatus status;

    public Flight(String flightNumber, Airport origin, Airport destination, LocalDate departureDate, Aircraft aircraft, double basePrice) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.aircraft = aircraft;
        this.basePrice = basePrice;
        this.status = FlightStatus.SCHEDULED;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public Airport getOrigin() {
        return origin;
    }

    public Airport getDestination() {
        return destination;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public void setStatus(FlightStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return  true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Flight flight = (Flight) obj;
        return flightNumber.equals(flight.flightNumber) && departureDate.equals(flight.departureDate);

    }
    @Override
    public String toString() {
        return String.format("Flight %s | %s -> %s | %s | %s | Base Price: %.2f",
                flightNumber, origin.getCode(), destination.getCode(),departureDate,status,basePrice);
    }

}

