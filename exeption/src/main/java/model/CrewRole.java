package model;

public enum CrewRole {
    PILOT("Captain, primary flight control"), // Пилот, допуск к управлению судна
    COPILOT("First Officer, flight control support"), // Второй пилот, поддержка управления
    STEWARD("Flight Attendant, passenger service"); // Бортпроводник, обслуживание пассажиров

    private final String description;

    CrewRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
