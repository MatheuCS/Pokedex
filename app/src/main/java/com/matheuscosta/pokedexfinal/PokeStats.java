package com.matheuscosta.pokedexfinal;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Matheus Costa on 19/12/2017.
 */

public class PokeStats {

    @SerializedName("effort")
    private String effort;

    @SerializedName("base_stat")
    private String base_stat;

    @SerializedName("ability")
    private Stat Stat = new Stat();

    public String getEffort() {
        return effort;
    }

    public void setEffort(String effort) {
        this.effort = effort;
    }

    public String getBase_stat() {
        return base_stat;
    }

    public void setBase_stat(String base_stat) {
        this.base_stat = base_stat;
    }

    public com.matheuscosta.pokedexfinal.Stat getStat() {
        return Stat;
    }

    public void setStat(com.matheuscosta.pokedexfinal.Stat stat) {
        Stat = stat;
    }
}
