package model;

public class Passenger {
    private String passportId;
    private  String fullName;
    private int bonusMiles;

    public Passenger(String passportId, String fullName, int bonusMiles) {
        this.passportId = passportId;
        this.fullName = fullName;
        this.bonusMiles = bonusMiles;
    }

public String getPassportId() {
        return passportId;
}

public String getFullName() {
        return fullName;
}
public int getBonusMiles() {
        return bonusMiles;
}

public void addBonusMiles(int miles) {
        if(miles > 0) this.bonusMiles += miles;
}

@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass())  return false;
        Passenger p = (Passenger) obj;
        return passportId.equals(p.passportId);
}

@Override
    public String toString() {
        return fullName + "(Passport:" + passportId + ", Miles: " + bonusMiles + ")"; //         return fullName + "(Паспорт" + passportId + ", Мили: " + bonusMiles + ")";
}
}
