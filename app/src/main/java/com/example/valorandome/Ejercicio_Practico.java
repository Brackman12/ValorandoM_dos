package com.example.valorandome;

//Los imports o cabecera o librerias
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.valorandome.databinding.ActivityEjercicioPracticoBinding;
import com.example.valorandome.model.Contenido;
import com.example.valorandome.model.LocalHostValorandoMe;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Ejercicio_Practico extends AppCompatActivity {

   //Se crea la instancia del objeto
    ActivityEjercicioPracticoBinding binding;
    LocalHostValorandoMe localHostValorandoMe;
    Integer s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Se crea una conexion Biding
        binding = ActivityEjercicioPracticoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String url = "http://192.168.14.66:8080/retrofit/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        localHostValorandoMe = retrofit.create(LocalHostValorandoMe.class);
        Intent in = getIntent();
        Bundle b = in.getExtras();
        s = b.getInt("semana", 1);
        getContenido();
    }

    private void getContenido() {
        Call<Contenido> call = localHostValorandoMe.getContenido(s);
        call.enqueue(new Callback<Contenido>() {
            @Override
            public void onResponse(@NonNull Call<Contenido> call, @NonNull Response<Contenido> response) {
                if(response.isSuccessful()){
                    Log.d("myTag", "si funciona");
                }
                Contenido cont = response.body();
                String practicas = "";
                assert cont != null;

                practicas = cont.getPracticas();

                binding.textView35.setText(practicas);

            }

            @Override
            public void onFailure(@NonNull Call<Contenido> call, @NonNull Throwable t) {
                Log.d("myTag", t.getLocalizedMessage());

            }
        });
    }

    public void SiguienteSeis(View view){
        this.onBackPressed();
    }
}