package com.matheuscosta.pokedexfinal;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Matheus Costa on 19/12/2017.
 */

public class Sprites {

    @SerializedName("front_default")
    private String front_default;

    public String getFront_default() {
        return front_default;
    }

    public void setFront_default(String front_default) {
        this.front_default = front_default;
    }
}
