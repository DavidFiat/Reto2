package com.example.reto2.activities;

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

    private ImageView imagePokemon;
    private TextView namePokemon;
    private ImageButton stadisticsBtn;

    public PokemonView(@NonNull View itemView) {
        super(itemView);
        imagePokemon = itemView.findViewById(R.id.imagePokemon);
        namePokemon = itemView.findViewById(R.id.namePokemon);
        stadisticsBtn = itemView.findViewById(R.id.stadisticsBtn);
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public ImageView getImagePokemon() {
        return imagePokemon;
    }

    public void setImagePokemon(ImageView imagePokemon) {
        this.imagePokemon = imagePokemon;
    }

    public TextView getNamePokemon() {
        return namePokemon;
    }

    public void setNamePokemon(TextView namePokemon) {
        this.namePokemon = namePokemon;
    }

    public ImageButton getStadisticsBtn() {
        return stadisticsBtn;
    }

    public void setStadisticsBtn(ImageButton stadisticsBtn) {
        this.stadisticsBtn = stadisticsBtn;
    }
}
