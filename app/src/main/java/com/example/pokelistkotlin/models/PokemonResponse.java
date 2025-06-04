package com.example.pokelistkotlin.models;

import java.util.List;
public class PokemonResponse {

    private List<PokemonResult> results;

    public List<PokemonResult> getResults() {
        return results;
    }

    public void setResults(List<PokemonResult> results) {
        this.results = results;
    }
}
