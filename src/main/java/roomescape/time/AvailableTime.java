package roomescape.time;

public class AvailableTime {
    private final Long timeId;
    private final String time;
    private final boolean booked;

    public AvailableTime(Long timeId, String time, boolean booked) {
        this.timeId = timeId;
        this.time = time;
        this.booked = booked;
    }

    public Long getTimeId() {
        return timeId;
    }

    public String getTime() {
        return time;
    }

    public boolean isBooked() {
        return booked;
    }
}
