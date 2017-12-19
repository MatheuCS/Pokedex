package com.matheuscosta.pokedexfinal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.matheuscosta.pokedexfinal.pokeapi.Pokemon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matheus Costa on 18/12/2017.
 */

public class ListaPokemonAdapter extends RecyclerView.Adapter<ListaPokemonAdapter.ViewHolder> {

    public static List<Pokemon> dataset;
    private Context context;

    public ListaPokemonAdapter(Context context) {
        this.context = context;
        dataset = new ArrayList<>();
    }

    public ListaPokemonAdapter(Context context, List<Pokemon> pokemonList) {
        this.context = context;
        this.dataset = pokemonList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pokemon pokemon = dataset.get(position);
        holder.tv_name.setText(pokemon.getName());
        Glide.with(context).load("http://pokeapi.co/media/sprites/pokemon/" + pokemon.getNumber() +  ".png")
        .centerCrop()
        .crossFade()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(holder.iv_imagePokemon);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void adicionarListaPokemon(ArrayList<Pokemon> listaPokemon) {
        dataset.addAll(listaPokemon);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_imagePokemon;
        private TextView tv_name;

        public ViewHolder(View itemView){
            super(itemView);

            iv_imagePokemon = (ImageView) itemView.findViewById(R.id.iv_imagePokemon);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }

}
