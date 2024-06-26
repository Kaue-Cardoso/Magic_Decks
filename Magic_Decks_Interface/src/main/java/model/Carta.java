package model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Carta")
public class Carta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carta_id")
    private Long id;

    @Column(name = "nome_carta")
    private String name;

    @Column(name = "manaCost")
    private String manaCost;

    @Column(name = "cmc")
    private double cmc;

    @ElementCollection
    @CollectionTable(name = "carta_colors", joinColumns = @JoinColumn(name = "carta_id"))
    @Column(name = "color")
    private List<String> colors;

    @ElementCollection
    @CollectionTable(name = "carta_types", joinColumns = @JoinColumn(name = "carta_id"))
    @Column(name = "type")
    private List<String> types;

    @Column(name = "description")
    private String description;

    @Column(name = "power")
    private String power;

    @Column(name = "toughness")
    private String toughness;

    @Column(name = "imageUrl")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "fk_deck_id")
    private Deck deck;

    // Construtor vazio
    public Carta() {
    }

    // Construtor com os campos relevantes para criação
    public Carta(Long id, String name, String manaCost, double cmc, List<String> colors, List<String> types, String description, String power, String toughness, String imageUrl, Deck deck) {
        this.id = id;
        this.name = name;
        this.manaCost = manaCost;
        this.cmc = cmc;
        this.colors = colors;
        this.types = types;
        this.description = description;
        this.power = power;
        this.toughness = toughness;
        this.imageUrl = imageUrl;
        this.deck = deck;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }
    // getters e setters...
}
