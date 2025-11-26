package com.example.pokewatchlist;

public class PokemonSummary {

    private int id;
    private String name;

    public PokemonSummary(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
