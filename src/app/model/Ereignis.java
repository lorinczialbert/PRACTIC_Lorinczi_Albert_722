package app.model;

public class Ereignis {
    private int id;
    private int tributId;
    private EventTyp typ;
    private int points;
    private int day;

    public Ereignis(int id, int tributId, EventTyp typ, int points, int day) {
        this.id = id;
        this.tributId = tributId;
        this.typ = typ;
        this.points = points;
        this.day = day;
    }

    public int getId() { return id; }
    public int getTributId() { return tributId; }
    public EventTyp getTyp() { return typ; }
    public int getPoints() { return points; }
    public int getDay() { return day; }
}