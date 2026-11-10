package storage;


import model.Passenger;
import java.util.Arrays;



public class PassangerStorage {
    private Passenger[] data = new Passenger[10];
    private int size = 0;

    public void add(Passenger passenger) {
        if (size == data.length) {
            data = Arrays.copyOf(data, data.length*2);
        }
        data[size++] = passenger;
    }

    public int getSize() {
        return size;
    }
    public Passenger getByIndex(int index) {
        return data[index];
    }

    public Passenger getByPassport(String passport) {
        for (int i = 0; i < size; i++) {
            if (data[i].getPassportId().equalsIgnoreCase(passport)) return data[i];
        }
        return null;
    }


}
