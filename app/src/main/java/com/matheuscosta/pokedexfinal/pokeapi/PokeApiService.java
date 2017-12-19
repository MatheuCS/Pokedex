package com.matheuscosta.pokedexfinal.pokeapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Matheus Costa on 18/12/2017.
 */

public interface PokeApiService {
    @GET("pokemon")
    Call<PokemonResposta> obterListaPokemons(@Query("limit") int limit, @Query("offset") int offset);

    @GET("pokemon/{id}")
    Call<Pokemon> getPokemon(@Path("id") int id);

    @GET("item")
    Call<ItemResposta> obterListaItens(@Query("limit") int limit, @Query("offset") int offset);

}
