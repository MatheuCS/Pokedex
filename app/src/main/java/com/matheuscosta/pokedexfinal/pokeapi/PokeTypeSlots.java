package com.matheuscosta.pokedexfinal.pokeapi;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Matheus Costa on 19/12/2017.
 */

public class PokeTypeSlots {

    @SerializedName("slot")
    private String slot;

    @SerializedName("type")
    private Type Type = new Type();

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type type) {
        Type = type;
    }
}
