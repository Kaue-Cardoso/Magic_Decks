package model;

import java.util.List;

public class Carta {
    private String name;
    private String manaCost;
    private double cmc;
    private String[] colors;
    private String type;
    private String description;
    private String power;
    private String toughness;

    public Carta() {
        // Construtor vazio
    }

    public Carta(String name, String manaCost, double cmc, String[] colors, String type, String description, String power, String toughness) {
        this.name = name;
        this.manaCost = manaCost;
        this.cmc = cmc;
        this.colors = colors;
        this.type = type;
        this.description = description;
        this.power = power;
        this.toughness = toughness;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManaCost() {
        return manaCost;
    }

    public void setManaCost(String manaCost) {
        this.manaCost = manaCost;
    }

    public double getCmc() {
        return cmc;
    }

    public void setCmc(double cmc) {
        this.cmc = cmc;
    }

    public String[] getColors() {
        return colors;
    }

    public void setColors(String[] colors) {
        this.colors = colors;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getToughness() {
        return toughness;
    }

    public void setToughness(String toughness) {
        this.toughness = toughness;
    }
}
