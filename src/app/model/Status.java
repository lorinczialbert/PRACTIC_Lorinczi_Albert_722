package app.model;

public enum Status {
    LEBENDIG, TOT;

    public static Status fromJson(String jsonStatus) {
        if ("ALIVE".equalsIgnoreCase(jsonStatus)) return LEBENDIG;
        if ("DEAD".equalsIgnoreCase(jsonStatus)) return TOT;
        return LEBENDIG;
    }
}