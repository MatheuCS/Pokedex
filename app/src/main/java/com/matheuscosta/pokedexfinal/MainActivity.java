package com.matheuscosta.pokedexfinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.matheuscosta.pokedexfinal.pokeapi.PokeApiService;
import com.matheuscosta.pokedexfinal.pokeapi.Pokemon;
import com.matheuscosta.pokedexfinal.pokeapi.PokemonResposta;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "POKEDEX";

    private Retrofit retrofit;

    private RecyclerView recyclerView;
    private ListaPokemonAdapter listaPokemonAdapter;
    List<Pokemon> pokemonList = new ArrayList<>();

    private int offset;
    private boolean prontoParaCarregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rv_pokemons);
        listaPokemonAdapter = new ListaPokemonAdapter(this, pokemonList);
        recyclerView.setAdapter(listaPokemonAdapter);
        recyclerView.setHasFixedSize(true);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Pokemon pokemon = ListaPokemonAdapter.dataset.get(position);
                Intent i = new Intent(MainActivity.this, DetailActivity.class);
                i.putExtra("ID",  pokemon.getNumber());
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0){
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItens = layoutManager.findFirstCompletelyVisibleItemPosition();

                    if(prontoParaCarregar){
                        if((visibleItemCount + pastVisibleItens) >= totalItemCount){
                            System.out.println("CHEGAMOS AO FINAL");
                            prontoParaCarregar = false;
                            offset += 20;
                            obterDados(offset);
                        }
                    }

                }
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        prontoParaCarregar = true;
        offset = 0;

        obterDados(offset);
    }

    private void obterDados(int offset){
        PokeApiService service = retrofit.create(PokeApiService.class);
        Call<PokemonResposta> pokemonRespostaCall = service.obterListaPokemons(20, offset);

        pokemonRespostaCall.enqueue(new Callback<PokemonResposta>() {
            @Override
            public  void onResponse(Call<PokemonResposta> call, Response<PokemonResposta> response) {
                prontoParaCarregar = true;
                if(response.isSuccessful()){

                    PokemonResposta pokemonResposta = response.body();
                    ArrayList<Pokemon> listaPokemon = pokemonResposta.getResults();

                    listaPokemonAdapter.adicionarListaPokemon(listaPokemon);

                } else {
                    Log.e(TAG, "onRespose: " + response.errorBody());
                }
            }

            @Override
            public  void onFailure(Call<PokemonResposta> call, Throwable t) {
                prontoParaCarregar = true;
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
