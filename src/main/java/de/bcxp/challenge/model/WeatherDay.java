package de.bcxp.challenge.model;

public class WeatherDay {
    private final String day;
    private final int maxTemp;
    private final int minTemp;

    public WeatherDay(String day, int maxTemp, int minTemp) {
        this.day = day;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
    }

    public String getDay() { return day; }
    public int getMaxTemp() { return maxTemp; }
    public int getMinTemp() { return minTemp; }

    @Override
    public String toString() {
        return "WeatherDay{day='" + day + "', maxTemp=" + maxTemp + ", minTemp=" + minTemp + "}";
    }
}