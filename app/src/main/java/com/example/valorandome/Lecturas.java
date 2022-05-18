package com.example.valorandome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.valorandome.databinding.ActivityLecturasBinding;
import com.example.valorandome.model.Contenido;
import com.example.valorandome.model.LocalHostValorandoMe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Lecturas extends AppCompatActivity {

    ActivityLecturasBinding binding;
    LocalHostValorandoMe localHostValorandoMe;
    Integer s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLecturasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String url = "http://192.168.43.79:8080/retrofit/";
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
                String lecturas = "";
                assert cont != null;

                lecturas = cont.getLecturas();

                String[] parts = lecturas.split("\\|\\|");
                TextView[] val = new TextView[]{
                        binding.textView18,
                        binding.textView34,
                        binding.textView19,
                        binding.textView20,
                };
                int a = parts.length;
                for(int i = 0; i < a; i++){
                    String part = parts[i];
                    val[i].setText(part);
                }

            }

            @Override
            public void onFailure(@NonNull Call<Contenido> call, @NonNull Throwable t) {
                Log.d("myTag", "no funciona");
            }
        });
    }

    public void SiguienteCuatro(View view){
        this.onBackPressed();
    }
}