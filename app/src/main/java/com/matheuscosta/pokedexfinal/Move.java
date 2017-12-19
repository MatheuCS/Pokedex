package com.matheuscosta.pokedexfinal;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Matheus Costa on 19/12/2017.
 */

public class Move {

    @SerializedName("name")
    private String name;

    @SerializedName("url")
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
