package com.matheuscosta.pokedexfinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.matheuscosta.pokedexfinal.pokeapi.PokeApiService;
import com.matheuscosta.pokedexfinal.pokeapi.Pokemon;
import com.matheuscosta.pokedexfinal.pokeapi.PokemonResposta;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityPokemons extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "POKEDEX";

    private Retrofit retrofit;

    private RecyclerView recyclerView;
    private ListaPokemonAdapter listaPokemonAdapter;

    private int offset;
    private boolean prontoParaCarregar;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pokemons);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    // --------------------------------------------------------------------------------------------------------------------------
        recyclerView = (RecyclerView) findViewById(R.id.rv_pokemons);
        listaPokemonAdapter = new ListaPokemonAdapter(this);
        recyclerView.setAdapter(listaPokemonAdapter);
        recyclerView.setHasFixedSize(true);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Pokemon pokemon = ListaPokemonAdapter.dataset.get(position);
                Intent i = new Intent(MainActivityPokemons.this, DetailPokemonActivity.class);
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

        progress = new ProgressDialog(MainActivityPokemons.this);
        progress.setMessage(getResources().getString(R.string.msg_progress));
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();

        pokemonRespostaCall.enqueue(new Callback<PokemonResposta>() {
            @Override
            public  void onResponse(Call<PokemonResposta> call, Response<PokemonResposta> response) {
                prontoParaCarregar = true;
                if(response.isSuccessful()){

                    PokemonResposta pokemonResposta = response.body();
                    ArrayList<Pokemon> listaPokemon = pokemonResposta.getResults();
                    listaPokemonAdapter.adicionarListaPokemon(listaPokemon);
                    progress.dismiss();
                    progress.cancel();

                } else {
                    progress.dismiss();
                    progress.cancel();
                    erro();
                    Log.e(TAG, "onRespose: " + response.errorBody());
                }
            }

            @Override
            public  void onFailure(Call<PokemonResposta> call, Throwable t) {
                prontoParaCarregar = true;
                progress.dismiss();
                progress.cancel();
                erro();
            }
        });
    }

    public void erro (){
        Toast.makeText(this, "Houve algum erro ao solocitar os dados! Verifique sua conexão e tente novamente ",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_pokemons, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_itens) {
            startActivity(new Intent(MainActivityPokemons.this, MainActivityItens.class));
            finish();
        } else if (id == R.id.nav_pokemons) {
            startActivity(new Intent(MainActivityPokemons.this, MainActivityPokemons.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
