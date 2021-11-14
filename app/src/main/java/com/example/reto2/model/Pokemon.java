package com.example.reto2.model;

public class Pokemon {

    private String id;
    private String name;
    private String type;
    private int defense;
    private int attack;
    private int velocity;
    private int life;
    private long date;
    private String uri;

    public Pokemon() {
    }

    public Pokemon(String id, String name, String type, int defense, int attack, int velocity, int life, long date, String uri) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.defense = defense;
        this.attack = attack;
        this.velocity = velocity;
        this.life = life;
        this.date = date;
        this.uri = uri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", defense=" + defense +
                ", attack=" + attack +
                ", velocity=" + velocity +
                ", life=" + life +
                ", date=" + date +
                ", uri='" + uri + '\'' +
                '}';
    }
}