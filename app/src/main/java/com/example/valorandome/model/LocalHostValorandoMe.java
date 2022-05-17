package com.example.valorandome.model;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LocalHostValorandoMe {

    @GET("numero_semana.php")
    Call<Semana> getSemana(
            @Query("semana") int contador
    );

    @GET("numero_contenido.php")
    Call<Contenido> getContenido(
            @Query("contenido") int contador
    );

    @GET("valorandome.php")
    Call<Usuario> getNombre(
            @Query("nombre") String nombre
    );

    @POST("valores.php")
    Call<Void> setProfile(@Body Usuario usuario);

    @POST("login.php")
    Call<Usuario> login(@Body Login login);

    @POST("register.php")
    Call<Usuario> register(@Body Register register);
}
