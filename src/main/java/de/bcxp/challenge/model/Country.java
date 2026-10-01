package de.bcxp.challenge.model;

public class Country {
    private final String name;
    private final double population;
    private final double area;

    public Country(String name, double population, double area) {
        this.name = name;
        this.population = population;
        this.area = area;
    }

    public String getName() { return name; }
    public double getPopulation() { return population; }
    public double getArea() { return area; }

    public double getDensity() {
        return population / area;
    }
}