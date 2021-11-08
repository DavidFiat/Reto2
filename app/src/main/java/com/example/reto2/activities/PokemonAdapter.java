package com.example.reto2.activities;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reto2.R;
import com.example.reto2.model.Pokemon;

import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonView>{

    private ArrayList<Pokemon> pokemons;

    public PokemonAdapter(){
        pokemons = new ArrayList<>();
    }

    @NonNull
    @Override
    public PokemonView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.pokemonrow, parent, false);
        PokemonView skeleton = new PokemonView(row);
        return skeleton;
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonView holder, int position) {
        Pokemon pokemon = pokemons.get(position);
        holder.setPokemon(pokemon);
        holder.getImagePokemon().setImageURI(Uri.parse(pokemon.getUri()));
        holder.getNamePokemon().setText(pokemon.getName());
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    public void addPokemon(Pokemon pokemon){
        pokemons.add(pokemon);
        notifyItemInserted(pokemons.size()-1);
    }
}
