package com.matheuscosta.pokedexfinal;

import com.google.gson.annotations.SerializedName;
import com.matheuscosta.pokedexfinal.Move;

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
