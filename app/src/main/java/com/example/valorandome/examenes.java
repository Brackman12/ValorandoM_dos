package com.example.valorandome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    Integer s, a=0, b=0, c=0, d=0;
    TextView resultado;
    Button uno, dos, tres, cuatro, cinco, seis, siete, ocho, nueve, diez, once, doce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExamenesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        resultado = (TextView) findViewById(R.id.resultado);
        uno = findViewById(R.id.opcion1);
        dos = findViewById(R.id.opcion2);
        tres = findViewById(R.id.opcion3);
        cuatro = findViewById(R.id.opcion4);
        cinco = findViewById(R.id.opcion5);
        seis = findViewById(R.id.opcion6);
        siete = findViewById(R.id.opcion7);
        ocho = findViewById(R.id.opcion8);
        nueve = findViewById(R.id.opcion9);
        diez = findViewById(R.id.opcion10);
        once = findViewById(R.id.opcion11);
        doce = findViewById(R.id.opcion12);

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

    public void PrimeraPregunta(View view){ a=1; }
    public void SegundaPregunta(View view){ a=2; }
    public void TerceraPregunta(View view){ a=3; }
    public void CuartaPregunta(View view){ b=1; }
    public void QuintaPregunta(View view){ b=2; }
    public void SextaPregunta(View view){ b=3; }
    public void SeptimaPregunta(View view){ c=1; }
    public void OctavaPregunta(View view){ c=2; }
    public void NovenaPregunta(View view){ c=3; }
    public void DecimaPregunta(View view){ d=1; }
    public void OnceavaPregunta(View view){ d=2; }
    public void DoceavaPregunta(View view){ d=3; }



    public void Respuesta(View view){
        Integer suma=6;
        switch (s){
            case 1:
                if(a==3){ suma=suma+1; }
                if(b==1){ suma=suma+1; }
                if(c==2){ suma=suma+1; }
                if(d==3){ suma=suma+1; }
                resultado.setText("Tu calificacion es: "+suma);
                suma=0;
                break;
            case 2:
                if(a==1){ suma=suma+1; }
                if(b==3){ suma=suma+1; }
                if(c==3){ suma=suma+1; }
                if(d==1){ suma=suma+1; }
                resultado.setText("Tu calificacion es: "+suma);
                suma=0;
                break;
            case 3:
                if(a==2){ suma=suma+1; }
                if(b==3){ suma=suma+1; }
                if(c==2){ suma=suma+1; }
                if(d==1){ suma=suma+1; }
                resultado.setText("Tu calificacion es: "+suma);
                suma=0;
                break;
            case 4:
                if(a==1){ suma=suma+1; }
                if(b==3){ suma=suma+1; }
                if(c==2){ suma=suma+1; }
                if(d==3){ suma=suma+1; }
                resultado.setText("Tu calificacion es: "+suma);
                suma=0;
                break;
            case 5:
                if(a==2){ suma=suma+1; }
                if(b==1){ suma=suma+1; }
                if(c==2){ suma=suma+1; }
                if(d==1){ suma=suma+1; }
                resultado.setText("Tu calificacion es: "+suma);
                suma=0;
                break;
            case 6:
                if(a==3){ suma=suma+1; }
                if(b==2){ suma=suma+1; }
                if(c==3){ suma=suma+1; }
                if(d==1){ suma=suma+1; }
                resultado.setText("Tu calificacion es: "+suma);
                suma=0;
                break;
            case 7:
                if(a==1){ suma=suma+1; }
                if(b==1){ suma=suma+1; }
                if(c==3){ suma=suma+1; }
                if(d==2){ suma=suma+1; }
                resultado.setText("Tu calificacion es: "+suma);
                suma=0;
                break;
            case 8:
                if(a==2){ suma=suma+1; }
                if(b==2){ suma=suma+1; }
                if(c==1){ suma=suma+1; }
                if(d==3){ suma=suma+1; }
                resultado.setText("Tu calificacion es: "+suma);
                suma=0;
                break;
            case 9:
                if(a==1){ suma=suma+1; }
                if(b==3){ suma=suma+1; }
                if(c==1){ suma=suma+1; }
                if(d==1){ suma=suma+1; }
                resultado.setText("Tu calificacion es: "+suma);
                suma=0;
                break;
        }
    }

    public void SiguienteSiete(View view){
        this.onBackPressed();
    }
}