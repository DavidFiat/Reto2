package com.example.reto2.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reto2.R;
import com.example.reto2.model.Pokemon;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonView>{

    private ArrayList<Pokemon> pokemons;
    private SetPokemonImagen listener;
    private SetPokemonData listenerImg;

    public PokemonAdapter(){
        pokemons = new ArrayList<>();
    }

    public void setListener(SetPokemonImagen listener) {
        this.listener = listener;
    }

    public void setListenerImg(SetPokemonData listenerImg){
        this.listenerImg = listenerImg;
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
        new Thread(
                ()->{
                    listener.setPokemonImagen(descargarImagen(pokemon.getUrl()), holder.getImagePokemon());
                }
        ).start();
        holder.getNamePokemon().setText(pokemon.getName());
        holder.getImagePokemon().setOnClickListener(
                v->{
                    listenerImg.goToPokemon(pokemon);
                }
        );
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    public void addPokemon(Pokemon pokemon){
        pokemons.add(pokemon);
        notifyItemInserted(pokemons.size()-1);
    }

    public void removePokemons(){
        notifyItemRangeRemoved(0,pokemons.size());
        pokemons.clear();
    }




    private Bitmap descargarImagen (String imageHttpAddress){
        URL imageUrl = null;
        Bitmap imagen = null;
        try{
            imageUrl = new URL(imageHttpAddress);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            imagen = BitmapFactory.decodeStream(conn.getInputStream());
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return imagen;
    }

    public interface SetPokemonImagen{
        public void setPokemonImagen(Bitmap imagen, ImageView foto);
    }

    public interface SetPokemonData{
        public void goToPokemon(Pokemon pokemon);
    }

    public ArrayList<Pokemon> getPokemons() {
        return pokemons;
    }
}
