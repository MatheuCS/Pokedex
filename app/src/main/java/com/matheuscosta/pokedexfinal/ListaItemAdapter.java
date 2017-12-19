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
import com.matheuscosta.pokedexfinal.pokeapi.Item;
import com.matheuscosta.pokedexfinal.pokeapi.Pokemon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matheus Costa on 19/12/2017.
 */

class ListaItemAdapter extends RecyclerView.Adapter<ListaItemAdapter.ViewHolder> {

    public static List<Item> dataset;
    private Context context;

    public ListaItemAdapter(Context context) {
        this.context = context;
        dataset = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = dataset.get(position);
        holder.tv_name.setText(item.getName());
        Glide.with(context).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/" + item.getName() +  ".png")
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.iv_imageItem);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void adicionarListaItem(ArrayList<Item> listaItem) {
        dataset.addAll(listaItem);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_imageItem;
        private TextView tv_name;

        public ViewHolder(View itemView){
            super(itemView);

            iv_imageItem = (ImageView) itemView.findViewById(R.id.iv_imageItem);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
