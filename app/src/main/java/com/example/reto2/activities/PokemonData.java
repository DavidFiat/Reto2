package com.example.reto2.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reto2.R;
import com.example.reto2.model.Pokemon;
import com.example.reto2.model.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PokemonData extends AppCompatActivity {

    private Button releaseButton;
    private TextView name;
    private TextView type;
    private TextView defensaData;
    private TextView ataqueData;
    private TextView velocidadData;
    private TextView vidaData;
    private User user;
    private Pokemon pokemon;
    private ImageView imageView;
    private Bitmap image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_data);
        user = (User) getIntent().getExtras().get("user");
        pokemon = (Pokemon) getIntent().getExtras().get("pokemon");
        releaseButton = findViewById(R.id.releaseButton);
        imageView = findViewById(R.id.imageView);
        new Thread(
                ()->{
                    setImage();
                }
        ).start();
        name = findViewById(R.id.name);
        type = findViewById(R.id.type);
        defensaData = findViewById(R.id.defensaData);
        ataqueData = findViewById(R.id.ataqueData);
        velocidadData = findViewById(R.id.velocidadData);
        vidaData = findViewById(R.id.vidaData);
        name.setText(pokemon.getName());
        type.setText(pokemon.getType());
        defensaData.setText(pokemon.getDefense()+"");
        ataqueData.setText(pokemon.getAttack()+"");
        velocidadData.setText(pokemon.getVelocity()+"");
        vidaData.setText(pokemon.getLife()+"");

        releaseButton.setOnClickListener(
                v -> {
                    FirebaseFirestore.getInstance().collection("users").document(user.getId()).collection("pokemones").document(pokemon.getId()).set(null);
                }
        );


    }

    private void setImage() {
        try {
            URL imageUrl = new URL(pokemon.getUrl());
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            image = BitmapFactory.decodeStream(conn.getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        runOnUiThread(
                ()->{
                    imageView.setImageBitmap(image);
                }
        );
    }
}