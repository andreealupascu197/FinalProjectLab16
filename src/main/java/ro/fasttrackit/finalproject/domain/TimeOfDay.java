package ro.fasttrackit.finalproject.domain;

public enum TimeOfDay {
    MORNING(7, 12),
    AFTERNOON(12, 17),
    EVENING(17, 23);

    private final int startHour;
    private final int endHour;

    private TimeOfDay(int startHour, int endHour) {
        this.startHour = startHour;
        this.endHour = endHour;

    }

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }
}