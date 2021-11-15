package com.example.reto2.pokemon;

public class TypePokemon {

    private TypePoke type;

    public TypePokemon() {
    }

    public TypePokemon(TypePoke type) {
        this.type = type;
    }

    public TypePoke getType() {
        return type;
    }

    public void setType(TypePoke type) {
        this.type = type;
    }
}
