package model;

public class CrewMember {
    private String name;
    private CrewRole role;

    public void CrewRole(String name, CrewRole role) {
        this.name = name;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public  CrewRole getRole() {
        return role;
    }
}
