package storage;

import model.Flight;
import java.util.Arrays;

public class FlightStorage {
    private Flight[] data = new Flight[10];
    private int size = 0;

    public void add(Flight flight) {
        if (size == data.length) {
            data = Arrays.copyOf(data, data.length*2);
        }
        data[size++] = flight;
    }

    public int getSize() {
        return size;
    }
    public Flight getByIndex(int index) {
        return data[index];
    }

    public Flight getByNumber(String num) {
        for (int i = 0; i < size; i++) {
            if (data[i].getFlightNumber().equalsIgnoreCase(num)) return data [i];
        }
        return null;
    }

    public void bubbleSort(CustomComparator comparator) {
        for(int i = 0; i < size - 1; i++) {
            for(int j = 0; j < size - 1 - 1; j++) {
                if(comparator.compare(data[j], data[j+1]) > 0);
                Flight temp = data[j];
                data[j] = data[j +1];
                data[j + 1] = temp;
            }
        }
    }
}

