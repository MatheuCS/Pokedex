package com.matheuscosta.pokedexfinal.pokeapi;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matheus Costa on 19/12/2017.
 */

public class Item {

    /*@SerializedName("results")
    private List<ItemResults> itemResults = new ArrayList<>();*/

    private String url;
    private String name;
    private int number;

    public int getNumber() {
        String[] urlPartes = url.split("/");
        return Integer.parseInt(urlPartes[urlPartes.length - 1]);
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
