package Stations;

public class StationMin {
    String date;
    String time;
    String place;
    String station;
    String value;
    int id;

    public StationMin(){}
    public StationMin(String date, String time, String place, String station, String value, int id) {
        this.date = date;
        this.time = time;
        this.place = place;
        this.station = station;
        this.value = value;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getPlace() {
        return place;
    }

    public String getStation() {
        return station;
    }

    public String getValue() {
        return value;
    }
}
