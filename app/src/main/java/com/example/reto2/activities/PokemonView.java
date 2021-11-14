package com.example.reto2.activities;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reto2.R;
import com.example.reto2.model.Pokemon;

public class PokemonView extends RecyclerView.ViewHolder{

    private Pokemon pokemon;

    private ImageButton imagePokemon;
    private TextView namePokemon;
    private View itemView;

    public PokemonView(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        imagePokemon = itemView.findViewById(R.id.imagePokemon);
        namePokemon = itemView.findViewById(R.id.namePokemon);
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public ImageButton getImagePokemon() {
        return imagePokemon;
    }

    public void setImagePokemon(ImageButton imagePokemon) {
        this.imagePokemon = imagePokemon;
    }

    public TextView getNamePokemon() {
        return namePokemon;
    }

    public void setNamePokemon(TextView namePokemon) {
        this.namePokemon = namePokemon;
    }
}
