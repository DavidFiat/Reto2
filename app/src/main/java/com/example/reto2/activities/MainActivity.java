package com.example.reto2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.reto2.R;
import com.example.reto2.model.User;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Calendar;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private EditText usernameET;
    private Button ingresarBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameET = findViewById(R.id.usernameET);
        ingresarBtn = findViewById(R.id.ingresarBtn);

        ingresarBtn.setOnClickListener(this::login);
    }

    private void login(View view) {
        String username = usernameET.getText().toString();
        User user = new User(UUID.randomUUID().toString(), username);

        Query query = FirebaseFirestore.getInstance().collection("users").whereEqualTo("username", username);
        query.get().addOnCompleteListener(
                task->{

                    //Si el usuario no existe crearlo e iniciar sesion con él
                    if(task.getResult().size() == 0){
                        FirebaseFirestore.getInstance().collection("users").document(user.getId()).set(user);
                        Intent intent = new Intent(this, HomeActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                    }

                    //Si ya existe, descargar el usuario e iniciar sesion con el
                    else{
                        User existingUser = null;
                        for(DocumentSnapshot doc : task.getResult()){
                            existingUser = doc.toObject(User.class);
                            break;
                        }
                        Intent intent = new Intent(this, HomeActivity.class);
                        intent.putExtra("user",existingUser);
                        startActivity(intent);
                    }

                }
        );
    }
}