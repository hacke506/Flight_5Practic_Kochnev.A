package model;

public class Booking {
    private  String id;
    private Ticket ticket;
    private boolean isCheckedIn;

    public Booking(String id, Ticket ticket) {
        this.id = id;
        this.ticket = ticket;
        this.isCheckedIn = false;
    }

    public String getId() {
        return id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public boolean isCheckedIn() {
        return isCheckedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        isCheckedIn = checkedIn;
    }
}
