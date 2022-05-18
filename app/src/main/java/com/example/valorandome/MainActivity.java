package com.example.valorandome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.valorandome.model.LocalHostValorandoMe;
import com.example.valorandome.model.Login;
import com.example.valorandome.model.Register;
import com.example.valorandome.model.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView crear;
    private EditText usuario;
    private EditText password;
    private EditText usuario_dos;
    private Button iniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);

        if (preferences.contains("nombre") && preferences.contains("correo")) {
            Intent intent = new Intent(MainActivity.this, Menu_Principal.class);
            startActivity(intent);

            finish();
        }
        else {
            setContentView(R.layout.activity_main);

            usuario = findViewById(R.id.et_user);
            password = findViewById(R.id.et_pass);
            iniciarSesion = findViewById(R.id.bt1);
            usuario_dos = findViewById(R.id.et_user);

            crear = (TextView) findViewById(R.id.crear_cuenta);
            crear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialog();
                }
            });

                iniciarSesion.setOnClickListener(view -> {
                    String c="";
                    if((usuario.getText().toString().equals(c))||(password.getText().toString().equals(c))){
                        Toast.makeText(this, "Debes insertar ambos datos para iniciar sesion", Toast.LENGTH_LONG).show();
                        usuario.setHintTextColor(Color.RED);
                        password.setHintTextColor(Color.RED);
                    }else {
                        String url = "http://192.168.14.66:8080/retrofit/";
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(url)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        LocalHostValorandoMe localHostValorandoMe = retrofit.create(LocalHostValorandoMe.class);

                        Login loginUsuario = new Login(usuario.getText().toString(), password.getText().toString());

                        Call<Usuario> usuarioCall = localHostValorandoMe.login(loginUsuario);

                        usuarioCall.enqueue(new Callback<Usuario>() {
                            @Override
                            public void onResponse(@NonNull Call<Usuario> call, @NonNull Response<Usuario> response) {
                                Usuario usuario = response.body();
                                if (usuario != null && usuario.getNombre() != null) {
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("nombre", usuario.getNombre());
                                    editor.putString("correo", usuario.getCorreo());
                                    editor.apply();
                                    Intent intent = new Intent(MainActivity.this, Menu_Principal.class);
                                    startActivity(intent);

                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Usuario y/o contraseña incorrectos", Toast.LENGTH_LONG).show();
                                    usuario_dos.setTextColor(Color.RED);
                                    password.setTextColor(Color.RED);
                                }

                            }

                            @Override
                            public void onFailure(@NonNull Call<Usuario> call, @NonNull Throwable t) {
                                Log.d("MainActivity", t.getLocalizedMessage());
                            }
                        });
                    }
                });
            }

    }


    //Metodo para cambiar de activity
    public void Siguiente(View view) {

        Intent intent = new Intent(this, Menu_Principal.class);
        startActivity(intent);

    }
    public void SiguienteDoce(View view){


        Intent intent = new Intent(this, Ayuda.class);
        startActivity(intent);
    }

    private void showDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.crear_cuenta);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.show();

        EditText nombre = dialog.findViewById(R.id.editTextTextPersonName);
        EditText correo = dialog.findViewById(R.id.editTextTextPersonName3);
        EditText password = dialog.findViewById(R.id.editTextNumberPassword);
        Button crearCuenta = dialog.findViewById(R.id.button11);


        crearCuenta.setOnClickListener(view -> {
            String c="";
            if((nombre.getText().toString().equals(c))||(correo.getText().toString().equals(c))||(password.getText().toString().equals(c))){
                Toast.makeText(this, "Debes insertar todos los datos", Toast.LENGTH_LONG).show();
                nombre.setHintTextColor(Color.RED);
                correo.setHintTextColor(Color.RED);
                password.setHintTextColor(Color.RED);
            }else {
                String email = correo.getText().toString();
                int cont = 0;
                for (int i = 0; i < email.length(); i++) {
                    char actual = email.charAt(i);
                    if (actual == '.') {
                        correo.setHintTextColor(Color.argb(0, 86, 7, 12));
                        Toast.makeText(this, "El correo debe tener un '.' despues del '@'", Toast.LENGTH_LONG).show();
                        cont = 1;
                    } else if (actual == '@') {
                        correo.setHintTextColor(Color.argb(0, 86, 7, 12));
                        Toast.makeText(this, "El correo debe tener un '@'", Toast.LENGTH_LONG).show();
                        cont = 1;
                    }
                }
                if (cont != 1){
                    String url = "http://192.168.14.66:8080/retrofit/";
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                LocalHostValorandoMe localHostValorandoMe = retrofit.create(LocalHostValorandoMe.class);

                Register registro = new Register(nombre.getText().toString(), correo.getText().toString(), password.getText().toString());

                Call<Usuario> registerCall = localHostValorandoMe.register(registro);

                registerCall.enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(@NonNull Call<Usuario> call, @NonNull Response<Usuario> response) {
                        Usuario usuario = response.body();
                        if (usuario != null && usuario.getNombre() != null) {
                            dialog.dismiss();
                            SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("nombre", usuario.getNombre());
                            editor.putString("correo", usuario.getCorreo());
                            editor.apply();
                            Intent intent = new Intent(MainActivity.this, Menu_Principal.class);
                            startActivity(intent);

                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "El usuario ya existe", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Usuario> call, @NonNull Throwable t) {
                        Log.d("MainActivity", t.getLocalizedMessage());
                    }
                });
              }
            }
        });

    }
}