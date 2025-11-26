package com.example.pokewatchlist;

import java.util.List;

public class PokemonDetail {

    // Top-level fields from PokeAPI /pokemon
    private int id;
    private String name;
    private int height;
    private int weight;
    private int base_experience;

    private List<PokemonAbilityWrapper> abilities;
    private List<PokemonMoveWrapper> moves;
    private PokemonSprites sprites;

    // Basic getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public int getBaseExperience() {
        return base_experience;
    }

    // Convenience methods used by MainActivity/ProfileFragment

    public String getFirstAbilityName() {
        if (abilities != null && !abilities.isEmpty()) {
            PokemonAbilityWrapper wrapper = abilities.get(0);
            if (wrapper != null && wrapper.getAbility() != null) {
                String abilityName = wrapper.getAbility().getName();
                if (abilityName != null) {
                    return abilityName;
                }
            }
        }
        return "N/A";
    }

    public String getFirstMoveName() {
        if (moves != null && !moves.isEmpty()) {
            PokemonMoveWrapper wrapper = moves.get(0);
            if (wrapper != null && wrapper.getMove() != null) {
                String moveName = wrapper.getMove().getName();
                if (moveName != null) {
                    return moveName;
                }
            }
        }
        return "N/A";
    }

    public String getSpriteUrl() {
        if (sprites != null && sprites.getFrontDefault() != null) {
            return sprites.getFrontDefault();
        }
        return "";
    }

    public static class PokemonSprites {
        private String front_default;

        public String getFrontDefault() {
            return front_default;
        }
    }

    public static class PokemonAbilityWrapper {
        private PokemonAbility ability;

        public PokemonAbility getAbility() {
            return ability;
        }
    }

    public static class PokemonAbility {
        private String name;

        public String getName() {
            return name;
        }
    }

    public static class PokemonMoveWrapper {
        private PokemonMove move;

        public PokemonMove getMove() {
            return move;
        }
    }

    public static class PokemonMove {
        private String name;

        public String getName() {
            return name;
        }
    }
}
