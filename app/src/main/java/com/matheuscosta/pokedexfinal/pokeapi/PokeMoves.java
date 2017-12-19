package com.matheuscosta.pokedexfinal.pokeapi;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Matheus Costa on 19/12/2017.
 */

public class PokeMoves {

    @SerializedName("move")
    private Move Move = new Move();

    public Move getMove() {
        return Move;
    }

    public void setMove(Move move) {
        Move = move;
    }
}
