package app.model;

public class Tribut {
    private int id;
    private String name;
    private int distrikt;
    private Status status;
    private int skillLevel;

    public Tribut(int id, String name, int distrikt, Status status, int skillLevel) {
        this.id = id;
        this.name = name;
        this.distrikt = distrikt;
        this.status = status;
        this.skillLevel = skillLevel;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getDistrikt() { return distrikt; }
    public Status getStatus() { return status; }
    public int getSkillLevel() { return skillLevel; }

    @Override
    public String toString() {
        String displayStatus = (status == Status.LEBENDIG) ? "ALIVE" : "DEAD";
        return String.format("%d %s | D%d | %s | skill=%d",
                id, name, distrikt, displayStatus, skillLevel);
    }
}