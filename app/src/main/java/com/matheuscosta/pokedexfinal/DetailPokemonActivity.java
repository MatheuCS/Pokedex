package com.matheuscosta.pokedexfinal;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.matheuscosta.pokedexfinal.pokeapi.PokeApiService;
import com.matheuscosta.pokedexfinal.pokeapi.Pokemon;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailPokemonActivity extends AppCompatActivity {

    private TextView tvName, tvTypes, tvWeight, tvHeight, tvAbilities,tvHp, tvAttack, tcDefense, tvSpeed, tvSpecialAttack, tvSpecialDefense, tvMoves;
    private ImageView ivPokemon;

    private Retrofit retrofit;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pokemon);

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

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        requestData(id);

    }

    private void requestData(final int id) {
        PokeApiService service = retrofit.create(PokeApiService.class);
        Call<Pokemon> pokemonRespostaCall = service.getPokemon(id);

        progress = new ProgressDialog(DetailPokemonActivity.this);
        progress.setMessage(getResources().getString(R.string.msg_progress));
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();

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

                    progress.dismiss();
                    progress.cancel();


                } else {
                    Log.e("ERRO", "onRespose: " + response.errorBody());
                    progress.dismiss();
                    progress.cancel();
                    erro();
                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                Log.e("ERRO", "onFailure: " + t.getMessage());
                progress.dismiss();
                progress.cancel();
                erro();
            }

        });
    }

    public void erro (){
        Toast.makeText(this, "There was an error trying to download the data! Check your connection and try again.", Toast.LENGTH_LONG).show();
    }

}
