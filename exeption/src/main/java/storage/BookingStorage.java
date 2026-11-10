package storage;

import java.util.Arrays;

import model.Booking;

public class BookingStorage {
    private Booking[] data = new Booking[10];
    private int size = 0;

    public void add(Booking b) {
        if (size == data.length) {
            data = Arrays.copyOf(data, data.length * 2);

        }
        data[size++] = b;
    }

    public int getSize() {
        return size;
    }
    public Booking getByIndex (int index) {
        return data[index];
    }

    public Booking getById(String id) {
        for (int i = 0; i < size; i++) {
            if (data[i].getId().equalsIgnoreCase(id)) return data[i];
        }
        return null;
    }
}
