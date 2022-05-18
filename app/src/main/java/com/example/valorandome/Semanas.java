package com.example.valorandome;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.example.valorandome.databinding.ActivitySemanaBinding;
import com.example.valorandome.model.Contenido;
import com.example.valorandome.model.LocalHostValorandoMe;
import com.example.valorandome.model.Semana;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Semanas extends AppCompatActivity {

    ActivitySemanaBinding binding;
    LocalHostValorandoMe localHostValorandoMe;
    Integer s;

    private Semana currentSemana;
    private String video1 = "";
    private String video2 = "";
    private String video3 = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySemanaBinding.inflate(getLayoutInflater());
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
        getSemana();

        binding.button.setOnClickListener(view -> {
            try {
                URL videoUrl = new URL(Semanas.this.video1);
                String videoId = videoUrl.getQuery().split("=")[1];
                Uri videoUri = Uri.parse("vnd.youtube:" + videoId);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(videoUri);

                startActivity(intent);
            } catch (MalformedURLException | ActivityNotFoundException e) {
                e.printStackTrace();
            }
        });

        binding.button5.setOnClickListener(view -> {
            try {
                URL videoUrl = new URL(Semanas.this.video2);
                String videoId = videoUrl.getQuery().split("=")[1];
                Uri videoUri = Uri.parse("vnd.youtube:" + videoId);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(videoUri);

                startActivity(intent);
            } catch (MalformedURLException | ActivityNotFoundException e) {
                e.printStackTrace();
            }
        });

        binding.button8.setOnClickListener(view -> {
            try {
                URL videoUrl = new URL(Semanas.this.video3);
                String videoId = videoUrl.getQuery().split("=")[1];
                Uri videoUri = Uri.parse("vnd.youtube:" + videoId);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(videoUri);

                startActivity(intent);
            } catch (MalformedURLException | ActivityNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void getSemana() {
        Call<Semana> call = localHostValorandoMe.getSemana(s);
        call.enqueue(new Callback<Semana>() {
            @Override
            public void onResponse(@NonNull Call<Semana> call, @NonNull Response<Semana> response) {
                if(response.isSuccessful()){
                    Log.d("myTag", "si funciona");
                }
                Semana semana = response.body();
                Integer sem = 1;
                String desc = "";
                String val = "";
                assert semana != null;
                Semanas.this.currentSemana = semana;
                desc =  "descripcion: "+ semana.getDescripcion();
                val =  "valor: "+ semana.getValor();
                sem = semana.getContador();

                numSem(sem);
                binding.textView14.setText(val);
                binding.textView15.setText(desc);

                getVideos();
            }

            @Override
            public void onFailure(@NonNull Call<Semana> call, @NonNull Throwable t) {
                Log.d("myTag", t.getLocalizedMessage());
            }
        });
    }

    private void numSem(Integer sem){
        String semana;
        switch (sem){
            case 1:
                semana = "Uno";
                binding.textView13.setText(String.format("Semana %s", semana));
                break;
            case 2:
                semana = "Dos";
                binding.textView13.setText(String.format("Semana %s", semana));
                break;
            case 3:
                semana = "Tres";
                binding.textView13.setText(String.format("Semana %s", semana));
                break;
            case 4:
                semana = "Cuatro";
                binding.textView13.setText(String.format("Semana %s", semana));
                break;
            case 5:
                semana = "Cinco";
                binding.textView13.setText(String.format("Semana %s", semana));
                break;
            case 6:
                semana = "Seis";
                binding.textView13.setText(String.format("Semana %s", semana));
                break;
            case 7:
                semana = "Siete";
                binding.textView13.setText(String.format("Semana %s", semana));
                break;
            case 8:
                semana = "Ocho";
                binding.textView13.setText(String.format("Semana %s", semana));
                break;
            case 9:
                semana = "Nueve";
                binding.textView13.setText(String.format("Semana %s", semana));
                break;
        }


    }

    private void getVideos() {
        if (this.currentSemana != null) {
            Integer numSemana = this.currentSemana.getContador();

            Call<Contenido> contenidoCall = localHostValorandoMe.getContenido(numSemana);

            contenidoCall.enqueue(new Callback<Contenido>() {
                @Override
                public void onResponse(@NonNull Call<Contenido> call, Response<Contenido> response) {
                    Contenido contenido = response.body();

                    assert contenido != null;
                    String[] videos = contenido.getLinks().replaceAll("\\s+", "").split("[|]+");

                    Semanas.this.video1 = videos[0];
                    Semanas.this.video2 = videos[1];
                    Semanas.this.video3 = videos[2];
                }

                @Override
                public void onFailure(@NonNull Call<Contenido> call, Throwable t) {
                    Log.d("Semanas", t.getLocalizedMessage());
                }
            });

        }
    }

    public void SiguienteTres(View view){
        this.onBackPressed();
    }
    public void SiguienteCinco(View view){
        Bundle b = new Bundle();
        b.putInt("semana", s);
        Intent intent = new Intent(this, Lecturas.class);
        intent.putExtras(b);
        startActivity(intent);
    }
    public void SiguienteOcho(View view){
        Bundle b = new Bundle();
        b.putInt("semana", s);
        Intent intent = new Intent(this, examenes.class);
        intent.putExtras(b);
        startActivity(intent);
    }
    public void SiguienteNueve(View view){
        Bundle b = new Bundle();
        b.putInt("semana", s);
        Intent intent = new Intent(this, Ejercicio_Practico.class);
        intent.putExtras(b);
        startActivity(intent);
    }
}