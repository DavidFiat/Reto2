package com.example.reto2.pokemon;

public class PokemonApi {


    private String name;
    private Sprite sprites;
    private Stat[] stats;

    public PokemonApi() {
    }

    public PokemonApi(String name, Sprite sprites, Stat[] stats) {
        this.name = name;
        this.sprites = sprites;
        this.stats = stats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sprite getSprites() {
        return sprites;
    }

    public void setSprites(Sprite sprites) {
        this.sprites = sprites;
    }

    public Stat[] getStats() {
        return stats;
    }

    public void setStats(Stat[] stats) {
        this.stats = stats;
    }
}