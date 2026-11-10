package model;

public class Airport {
    private String code;
    private String city;

    public Airport(String code, String city) {
        this.code = code;
        this.city = city;
    }

    public String getCode() {return code;}
    public String getCity() {return city;}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Airport airport = (Airport) obj;
        return code.equals(airport.code);
    }
    @Override
    public String toString() {
        return city + "[" + code + "]";
    }
}
