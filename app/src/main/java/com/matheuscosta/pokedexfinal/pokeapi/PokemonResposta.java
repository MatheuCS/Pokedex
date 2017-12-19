package com.matheuscosta.pokedexfinal.pokeapi;

import java.util.ArrayList;

/**
 * Created by Matheus Costa on 18/12/2017.
 */

public class PokemonResposta {
    ArrayList<Pokemon> results;

    public ArrayList<Pokemon> getResults() {
        return results;
    }

    public void setResults(ArrayList<Pokemon> results) {
        this.results = results;
    }
}
