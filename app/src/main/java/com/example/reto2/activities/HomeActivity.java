package com.example.reto2.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reto2.R;
import com.example.reto2.model.Pokemon;
import com.example.reto2.model.User;
import com.example.reto2.pokemon.PokemonApi;
import com.example.reto2.util.HTTPSWebUtilDomi;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.Date;
import java.util.UUID;

public class HomeActivity extends AppCompatActivity implements PokemonAdapter.SetPokemonImagen, PokemonAdapter.SetPokemonData {

    private User user;


    private EditText atraparET;
    private Button atraparBtn;

    private EditText buscarET;
    private Button buscarBtn;



    private RecyclerView pokemonsRecycler;
    private LinearLayoutManager manager;
    private PokemonAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        user = (User) getIntent().getExtras().get("user");
        atraparET = findViewById(R.id.atraparET);
        atraparBtn = findViewById(R.id.atraparBtn);
        buscarET = findViewById(R.id.buscarET);
        buscarBtn = findViewById(R.id.buscarBtn);
        adapter = new PokemonAdapter();
        adapter = new PokemonAdapter();
        adapter.setListener(this);
        adapter.setListenerImg(this);
        pokemonsRecycler = findViewById(R.id.pokemonsRecycler);
        manager = new LinearLayoutManager(this);
        pokemonsRecycler.setLayoutManager(manager);
        pokemonsRecycler.setAdapter(adapter);
        pokemonsRecycler.setHasFixedSize(true);
        getMyPokemons();
        atraparBtn.setOnClickListener(this::atraparPokemon);
        buscarBtn.setOnClickListener(this::buscarPokemon);
        buscarET.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }
                    @Override
                    public void afterTextChanged(Editable editable) {
                        if (editable.toString().equals("")) {
                            adapter.removePokemons();
                            getMyPokemons();
                        }
                    }
                }
        );


    }
    @Override
    public void goToPokemon(Pokemon pokemon) {
        Intent i = new Intent(this, PokemonData.class);
        i.putExtra("user",pokemon.getUser());
        i.putExtra("pokemon",pokemon.getName());
        startActivity(i);
    }

    @Override
    public void setPokemonImagen(Bitmap imagen, ImageView foto) {
        runOnUiThread(
                () -> {
                    foto.setImageBitmap(imagen);
                }
        );
    }

    public void getMyPokemons() {
        FirebaseFirestore.getInstance().collection("users").document(user.getId()).collection("pokemones").orderBy("date").get().addOnCompleteListener(
                task -> {
                    for (DocumentSnapshot doc : task.getResult()) {
                        Pokemon pokemon = doc.toObject(Pokemon.class);
                        adapter.addPokemon(pokemon);
                    }
                }
        );
    }

    private void atraparPokemon(View view) {
        String namePoke = atraparET.getText().toString();
        atraparET.setText("");
        new Thread(
                () -> {
                    HTTPSWebUtilDomi httpsWebUtilDomi = new HTTPSWebUtilDomi();
                    String json = httpsWebUtilDomi.GETrequest("https://pokeapi.co/api/v2/pokemon/" + namePoke);
                    if (json.isEmpty() == false) {
                        Gson gson = new Gson();
                        PokemonApi pokemonApi = gson.fromJson(json, PokemonApi.class);
                        String type = pokemonApi.getTypes()[0].getType().getName();
                        int defense = pokemonApi.getStats()[2].getBase_stat();
                        int attack = pokemonApi.getStats()[1].getBase_stat();
                        int velocity = pokemonApi.getStats()[5].getBase_stat();
                        int life = pokemonApi.getStats()[0].getBase_stat();
                        String img = pokemonApi.getSprites().getBack_default();
                        Pokemon pokemon = new Pokemon(UUID.randomUUID().toString(), pokemonApi.getName(), type, defense, attack, velocity, life, new Date().getTime(), img);
                        addPokemon(pokemon);
                    } else {
                        runOnUiThread(
                                () -> {
                                    Toast.makeText(this, "El pokemon: " + namePoke + " no existe", Toast.LENGTH_LONG).show();
                                }
                        );

                    }

                }
        ).start();

    }


    public void addPokemon(Pokemon pokemon) {
        FirebaseFirestore.getInstance().collection("users").document(user.getId()).collection("pokemones").document(pokemon.getId()).set(pokemon);
        runOnUiThread(
                () -> {
                    adapter.addPokemon(pokemon);
                }
        );
    }

    private void buscarPokemon(View view) {
        String namePoke = buscarET.getText().toString();
        String userID = user.getId();
        FirebaseFirestore.getInstance().collection("users").document(userID).collection("pokemones").whereEqualTo("name", namePoke).get().addOnCompleteListener(
                task -> {
                    if (task.getResult().size() == 0) {
                        runOnUiThread(
                                () -> {
                                    Toast.makeText(this, "El pokemon: " + namePoke + " no lo has atrapado!", Toast.LENGTH_LONG).show();
                                }
                        );
                    } else {
                        runOnUiThread(
                                () -> {
                                    adapter.removePokemons();
                                }
                        );
                        for (DocumentSnapshot doc : task.getResult()) {
                            Pokemon pokemon = doc.toObject(Pokemon.class);
                            adapter.addPokemon(pokemon);
                        }
                    }
                }
        );
    }



}






