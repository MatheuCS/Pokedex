package com.matheuscosta.pokedexfinal;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Matheus Costa on 19/12/2017.
 */

public class PokeAbilities {

    @SerializedName("slot")
    private String slot;

    @SerializedName("ability")
    private Ability Ability = new Ability();

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public Ability getAbility() {
        return Ability;
    }

    public void setAbility(Ability ability) {
        Ability = ability;
    }
}
