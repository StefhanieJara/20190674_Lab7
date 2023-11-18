package com.example.a20190674_lab7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class salonBelleza extends AppCompatActivity {

    FirebaseFirestore db;
    RecyclerView recyclerView;
    private List<ListaSalones> listaSalones = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon_belleza);

        db = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.listasalon);
        //aqui se debe colocar la logica para lograr la lista







    }
}