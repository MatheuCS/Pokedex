package com.matheuscosta.pokedexfinal.pokeapi;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matheus Costa on 19/12/2017.
 */

public class PokeType {

    @SerializedName("types")
    private List<PokeTypeSlots> pokeTypesSlots = new ArrayList<>();

}
