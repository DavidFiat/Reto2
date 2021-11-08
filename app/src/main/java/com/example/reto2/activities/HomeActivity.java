package com.example.reto2.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reto2.R;
import com.example.reto2.model.Pokemon;
import com.example.reto2.model.User;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import android.os.Bundle;

public class HomeActivity extends AppCompatActivity {

    private User user;

    private RecyclerView pokemonsRecycler;
    private LinearLayoutManager manager;
    private PokemonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        user = (User) getIntent().getExtras().get("user");

        pokemonsRecycler = findViewById(R.id.pokemonsRecycler);
        manager = new LinearLayoutManager(this);
        pokemonsRecycler.setLayoutManager(manager);
        pokemonsRecycler.setAdapter(adapter);
        pokemonsRecycler.setHasFixedSize(true);

        FirebaseFirestore.getInstance().collection("users").document(user.getId()).collection("pokemons").orderBy("date").get().addOnCompleteListener(
                task ->{
                    for(DocumentSnapshot doc : task.getResult()){
                        Pokemon pokemon = doc.toObject(Pokemon.class);
                        adapter.addPokemon(pokemon);
                    }
                }
        );
    }
}