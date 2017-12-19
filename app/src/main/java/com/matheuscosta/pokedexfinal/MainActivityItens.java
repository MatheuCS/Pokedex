package com.matheuscosta.pokedexfinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import com.matheuscosta.pokedexfinal.pokeapi.Item;
import com.matheuscosta.pokedexfinal.pokeapi.ItemResposta;
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

public class MainActivityItens extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "ERRO";

    private Retrofit retrofit;

    private RecyclerView recyclerView;
    private ListaItemAdapter listaItensAdapter;

    private int offset;
    private boolean prontoParaCarregar;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_itens);
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
        recyclerView = (RecyclerView) findViewById(R.id.rv_itens);
        listaItensAdapter = new ListaItemAdapter(this);
        recyclerView.setAdapter(listaItensAdapter);
        recyclerView.setHasFixedSize(true);

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
        Call<ItemResposta> itemRespostaCall = service.obterListaItens(20, offset);

        progress = new ProgressDialog(MainActivityItens.this);
        progress.setMessage(getResources().getString(R.string.msg_progress));
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();

        itemRespostaCall.enqueue(new Callback<ItemResposta>() {
            @Override
            public  void onResponse(Call<ItemResposta> call, Response<ItemResposta> response) {
                prontoParaCarregar = true;
                if(response.isSuccessful()){

                    ItemResposta itemResposta = response.body();
                    ArrayList<Item> listaItem = itemResposta.getResults();

                    listaItensAdapter.adicionarListaItem(listaItem);

                    progress.dismiss();
                    progress.cancel();

                } else {
                    Log.e(TAG, "onRespose: " + response.errorBody());
                    progress.dismiss();
                    progress.cancel();
                    erro();
                }
            }

            @Override
            public  void onFailure(Call<ItemResposta> call, Throwable t) {
                prontoParaCarregar = true;
                Log.e(TAG, "onFailure: " + t.getMessage());
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
        getMenuInflater().inflate(R.menu.main_activity_itens, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_itens) {
            startActivity(new Intent(MainActivityItens.this, MainActivityItens.class));
            finish();
        } else if (id == R.id.nav_pokemons) {
            startActivity(new Intent(MainActivityItens.this, MainActivityPokemons.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
