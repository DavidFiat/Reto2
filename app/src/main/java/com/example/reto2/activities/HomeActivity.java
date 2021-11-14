package com.example.reto2.activities;

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

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.UUID;

public class HomeActivity extends AppCompatActivity {

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


        pokemonsRecycler = findViewById(R.id.pokemonsRecycler);
        manager = new LinearLayoutManager(this);
        pokemonsRecycler.setLayoutManager(manager);
        pokemonsRecycler.setAdapter(adapter);
        pokemonsRecycler.setHasFixedSize(true);
        getMyPokemons();
        atraparBtn.setOnClickListener(this::atraparPokemon);


    }


    public void getMyPokemons(){
        FirebaseFirestore.getInstance().collection("users").document(user.getId()).collection("pokemons").orderBy("date").get().addOnCompleteListener(
                task ->{
                    for(DocumentSnapshot doc : task.getResult()){
                        Pokemon pokemon = doc.toObject(Pokemon.class);
                        adapter.addPokemon(pokemon);
                    }
                }
        );
    }


    private void atraparPokemon(View view) {
        String namePoke = atraparET.getText().toString();
                                new Thread(
                                        ()->{
                                            HTTPSWebUtilDomi httpsWebUtilDomi = new HTTPSWebUtilDomi();
                                            String json =  httpsWebUtilDomi.GETrequest("https://pokeapi.co/api/v2/pokemon/"+namePoke);
                                            Gson gson = new Gson();
                                            PokemonApi pokemonApi= gson.fromJson(json, PokemonApi.class);
                                            int defense =pokemonApi.getStats()[2].getBase_stat();
                                            int attack= pokemonApi.getStats()[1].getBase_stat();
                                            int velocity = pokemonApi.getStats()[5].getBase_stat();
                                            int life = pokemonApi.getStats()[0].getBase_stat();
                                            String img = pokemonApi.getSprites().getBack_default();
                                            Pokemon pokemon = new Pokemon(UUID.randomUUID().toString(),pokemonApi.getName()," ",defense,attack,velocity,life,new Date().getTime(),img);
                                            addPokemon(pokemon);
                                            Log.e(">>> ",pokemon.toString());

                                        }
                                ).start();

        atraparET.setText("");

                            }



        public void addPokemon (Pokemon pokemon  ){

            //FirebaseFirestore.getInstance().collection("users").document(user.getId()).collection("pokemones").document(pokemon.getId()).set(pokemon);

            runOnUiThread(

                    ()->{
                        adapter.addPokemon(pokemon);
                    }
            );




        }

}


