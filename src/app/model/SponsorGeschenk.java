package app.model;

public class SponsorGeschenk {
    private int id;
    private int tributId;
    private String itemName;
    private int value;
    private int day;

    public SponsorGeschenk(int id, int tributId, String itemName, int value, int day) {
        this.id = id;
        this.tributId = tributId;
        this.itemName = itemName;
        this.value = value;
        this.day = day;
    }

    public int getTributId() { return tributId; }
    public int getValue() { return value; }
}