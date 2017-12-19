package com.matheuscosta.pokedexfinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.matheuscosta.pokedexfinal.pokeapi.PokeApiService;
import com.matheuscosta.pokedexfinal.pokeapi.Pokemon;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {

    private TextView tvName, tvTypes, tvWeight, tvHeight, tvAbilities,tvHp, tvAttack, tcDefense, tvSpeed, tvSpecialAttack, tvSpecialDefense, tvMoves;
    private ImageView ivPokemon;

    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvName = (TextView) findViewById(R.id.tv_detail_name);
        tvTypes = (TextView) findViewById(R.id.tv_detail_types);
        tvWeight = (TextView) findViewById(R.id.tv_detail_weight);
        tvHeight = (TextView) findViewById(R.id.tv_detail_height);
        tvAbilities = (TextView) findViewById(R.id.tv_detail_abilities);
        tvHp = (TextView) findViewById(R.id.tv_detail_hp);
        tvAttack = (TextView) findViewById(R.id.tv_detail_attack);
        tcDefense = (TextView) findViewById(R.id.tv_detail_defense);
        tvSpeed = (TextView) findViewById(R.id.tv_detail_speed);
        tvSpecialAttack = (TextView) findViewById(R.id.tv_detail_special_attack);
        tvSpecialDefense = (TextView) findViewById(R.id.tv_detail_special_defense);
        tvMoves = (TextView) findViewById(R.id.tv_detail_moves);

        ivPokemon = (ImageView) findViewById(R.id.iv_detail_pokemon);

        int id = getIntent().getIntExtra("ID", 1);

        //System.out.println("TESTE: " + id);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        requestData(id);


    }

    private void requestData(final int id) {
        PokeApiService service = retrofit.create(PokeApiService.class);
        Call<Pokemon> pokemonRespostaCall = service.getPokemon(id);

        pokemonRespostaCall.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                Pokemon pokemon;
                if(response.isSuccessful()){

                    pokemon = response.body();
                    String[] stats = pokemon.pokeStatesToString();

                    tvName.setText(pokemon.getName());
                    tvTypes.setText("Type(s): " + pokemon.pokeTypesToString());
                    tvWeight.setText("Weight: " + pokemon.getWeight());
                    tvHeight.setText("Height: " + pokemon.getHeight());
                    tvAbilities.setText("Abilities: " + pokemon.pokeAbilitiesToString());

                    //{speed, special-defense, special-attack, defense, attack, hp}

                    tvHp.setText("HP: " + stats[5]);
                    tvAttack.setText("Attack: " + stats[4]);
                    tcDefense.setText("Defense: " + stats[3]);
                    tvSpeed.setText("Speed: " + stats[0]);
                    tvSpecialAttack.setText("Special Attack: " + stats[2]);
                    tvSpecialDefense.setText("Special Defense: " + stats[1]);

                    tvMoves.setText("Moves: " + pokemon.pokeMovesToString());

                    //System.out.println("TESTE: " + pokemon.getSprites().getFront_default());

                    Glide.with(ivPokemon.getContext()).load(pokemon.getSprites().getFront_default())
                            .centerCrop()
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(ivPokemon);


                } else {
                    Log.e("POKEDEX", "onRespose: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                Log.e("POKEDEX", "onFailure: " + t.getMessage());
            }

        });
    }

}
