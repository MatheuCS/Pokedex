package com.matheuscosta.pokedexfinal.pokeapi;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matheus Costa on 18/12/2017.
 */

public class Pokemon {

    private int number;
    private String name;
    private String url;
    private String weight;
    private String height;

    @SerializedName("id")
    private Integer id;

    @SerializedName("types")
    private List<PokeTypeSlots> pokeTypes = new ArrayList<>();

    @SerializedName("abilities")
    private List<PokeAbilities> pokeAbilities = new ArrayList<>();

    @SerializedName("stats")
    private List<PokeStats> PokeStats = new ArrayList<>();

    @SerializedName("moves")
    private List<PokeMoves> PokeMoves = new ArrayList<>();

    @SerializedName("sprites")
    private Sprites sprites = new Sprites();

    public Sprites getSprites() {
        return sprites;
    }

    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNumber() {
        String[] urlPartes = url.split("/");
        return Integer.parseInt(urlPartes[urlPartes.length - 1]);
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<PokeTypeSlots> getPokeTypes() {
        return pokeTypes;
    }

    public void setPokeTypes(List<PokeTypeSlots> pokeTypes) {
        this.pokeTypes = pokeTypes;
    }

    public String pokeTypesToString() {
        String types = "";
        for (int i = 0; i < pokeTypes.size(); i++) {
            if(i > 0)
                types += ", ";
            types += pokeTypes.get(i).getType().getName();
        }

        return types;
    }

    public String pokeAbilitiesToString() {
        String abilities = "";
        for (int i = 0; i < pokeAbilities.size(); i++) {
            if(i > 0)
                abilities += ", ";
            abilities += pokeAbilities.get(i).getAbility().getName();
        }

        return abilities;
    }

    public String[] pokeStatesToString() {
        String states[] = new String[6]; //{speed, special-defense, special-attack, defense, attack, hp}
        for (int i = 0; i < PokeStats.size(); i++) {
            states[i] = PokeStats.get(i).getBase_stat();
        }
        return states;
    }

    public String pokeMovesToString() {
        String moves = "";
        for (int i = 0; i < PokeMoves.size(); i++) {
            if(i > 0)
                moves += ", ";
            moves += PokeMoves.get(i).getMove().getName();
        }

        return moves;
    }

}
