package com.example.a20190674_lab7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a20190674_lab7.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword;
    HashMap<String,String> credencial= new HashMap<>();

    ActivityMainBinding binding;

    Button buttonLogin;
    FirebaseAuth mAuth;

    FirebaseFirestore db;

    private List<usuario> usuarioLista = new ArrayList<>();
    usuario usuario = new usuario();


    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.editTextContrasena);

        binding.iniciarSesion.setOnClickListener(v -> {
            String email = binding.email.getEditableText().toString();
            String pass= binding.editTextContrasena.getEditableText().toString();
            Log.d("msg-test", "se recibio los parametros de sesion");
            Log.d("msg-test", email+" "+pass);

            if (isValidEmail(email)) {
                Log.d("msg-test", "email valido");
                mAuth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d("msg-test", "completado");

                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("msg-test", "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                    String email = user.getEmail();
                                    Log.d("msg-test", "El correo es: "+email);

                                    db.collection("usuarios")
                                            .get()
                                            .addOnCompleteListener(task2 -> {
                                                if (task2.isSuccessful()) {
                                                    QuerySnapshot usuariosCollection = task2.getResult();
                                                    Log.d("msg-test", "task2 ha sido valido");
                                                    for (QueryDocumentSnapshot document : usuariosCollection) {
                                                        String codigo = document.getId();
                                                        String condicion = (String) document.get("condicion");
                                                        String pass = (String) document.get("contrasenha");
                                                        String correo = (String) document.get("correo");

                                                        Log.d("msg-test", "El condicion es: "+condicion);
                                                        Log.d("msg-test", "El correo es: "+pass);
                                                        Log.d("msg-test", "El contraseña es: "+correo);


                                                        if(correo.equals(email)){
                                                            usuario.setCondicion(condicion);
                                                            usuario.setContrasenha(pass);
                                                            usuario.setCorreo(correo);
                                                            usuario.setId(codigo);
                                                            Log.d("msg-test", "| codigo: " + usuario.getId() + "| correo: "+ usuario.getCorreo() +" | condicion: " + usuario.getCondicion() );

                                                            break;
                                                        }
                                                    }

                                                    if(usuario.getCondicion().equals("Gestor")){
                                                            Log.d("msg-test", "Entra rol gestor");
                                                            Intent intent = new Intent(MainActivity.this, salonBelleza.class);
                                                            intent.putExtra("usuario", usuario);
                                                            startActivity(intent);
                                                    }else if(usuario.getCondicion().equals("Usuario")){
                                                            Intent intent = new Intent(MainActivity.this, RolCliente.class);
                                                            Log.d("msg-test", "Entra rol delegado actividad");
                                                            //intent.putExtra("usuario", usuario);
                                                            startActivity(intent);
                                                    }

                                                }
                                            })
                                            .addOnFailureListener(e -> {
                                                // Maneja la excepción que ocurra al intentar obtener los documentos
                                                Log.e("msg-test", "Excepción al obtener documentos de la colección usuarios: ", e);
                                                Toast.makeText(MainActivity.this, "Error al obtener datos del usuario.", Toast.LENGTH_SHORT).show();
                                            });
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("msg-test", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }
                            }
                        })
                        .addOnFailureListener(this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Manejar la excepción
                                Log.e("msg-test", "Exception: " + e.getMessage());
                            }
                        });
            } else {
                Toast.makeText(this, "Correo electrónico incorrecto", Toast.LENGTH_SHORT).show();
            }
        });




    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.matches(emailRegex, email);
    }
    private void updateUI(FirebaseUser user) {}
}