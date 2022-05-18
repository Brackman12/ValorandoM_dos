package com.example.valorandome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.valorandome.databinding.ActivityLecturasBinding;
import com.example.valorandome.databinding.FragmentProfileBinding;
import com.example.valorandome.model.Contenido;
import com.example.valorandome.model.LocalHostValorandoMe;
import com.example.valorandome.model.Login;
import com.example.valorandome.model.Usuario;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private EditText usuario;
    private EditText password;
    FragmentProfileBinding binding;
    LocalHostValorandoMe localHostValorandoMe;
    private String name = "";
    private String valores = "";

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences preferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        binding = FragmentProfileBinding.inflate(getLayoutInflater(), container, false);

        binding.button2.setOnClickListener(onclick -> {
                    editor.remove("correo");
                    editor.remove("nombre");
                    editor.apply();
                    getActivity().finish();
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);

                }
        );
        String url = "http://192.168.14.66:8080/retrofit/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        localHostValorandoMe = retrofit.create(LocalHostValorandoMe.class);
        name = preferences.getString("nombre", "");
        binding.textView8.setText(name);
        getNombre(name);
        binding.button17.setOnClickListener(view -> {
            getValores();
        });
        return binding.getRoot();
    }

    private void getNombre(String name) {
        Call<Usuario> call = localHostValorandoMe.getNombre(name);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(@NonNull Call<Usuario> call, @NonNull Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Log.d("myTag", "si funciona");
                }
                Usuario cont = response.body();
                String quienSoy = "";
                String valores = "";
                assert cont != null;

                quienSoy = cont.getQuienSoy();
                valores = cont.getValores();

                binding.editTextTextMultiLine.setText(quienSoy);

                String[] parts = valores.split(",");
                CheckBox[] val = new CheckBox[]{
                        binding.checkBox,
                        binding.checkBox2,
                        binding.checkBox3,
                        binding.checkBox4,
                        binding.checkBox5,
                        binding.checkBox6,
                        binding.checkBox7,
                        binding.checkBox8,
                        binding.checkBox9,
                };
                if(!valores.isEmpty()){
                    for (String s : parts) {
                        int part = Integer.parseInt(s);
                        Log.d("split", s);
                        val[part].setChecked(true);
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<Usuario> call, @NonNull Throwable t) {

                Log.d("myTag", t.getLocalizedMessage());
            }
        });
    }

    private void getValores() {
        String quiensoy = binding.editTextTextMultiLine.getText().toString();
        valores();
        Usuario usuario = new Usuario(name, "", quiensoy, valores);
        Call<Void> call = localHostValorandoMe.setProfile(usuario);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {

                    Toast.makeText(getContext(), "Se guardo correctamente", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.d("myTag", "no funciona");
            }
        });
    }

    private void valores(){
        ArrayList<String> aux = new  ArrayList<>();
        CheckBox[] val = new CheckBox[]{
                binding.checkBox,
                binding.checkBox2,
                binding.checkBox3,
                binding.checkBox4,
                binding.checkBox5,
                binding.checkBox6,
                binding.checkBox7,
                binding.checkBox8,
                binding.checkBox9,
        };
        for(int i = 0; i < val.length; i++){
            if(val[i].isChecked()){
                aux.add(String.valueOf(i));
            }
        }
        valores = TextUtils.join(",", aux);
    }
}
