package com.example.valorandome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.valorandome.databinding.ActivityExamenesBinding;
import com.example.valorandome.model.Contenido;
import com.example.valorandome.model.LocalHostValorandoMe;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class examenes extends AppCompatActivity {

    ActivityExamenesBinding binding;
    LocalHostValorandoMe localHostValorandoMe;
    Integer s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExamenesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String url = "http://192.168.1.70:8080/retrofit/";
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
                String examenes = "";
                assert cont != null;

                examenes = cont.getExamenes();

                String[] parts = examenes.split("\\|\\|");
                TextView[] val = new TextView[]{
                        binding.textView17,
                        binding.textView29,
                        binding.textView33,
                        binding.textView38,
                        binding.textView42,
                        binding.textView44,
                        binding.textView49,
                        binding.textView50,
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

    public void SiguienteSiete(View view){
        this.onBackPressed();
    }
}