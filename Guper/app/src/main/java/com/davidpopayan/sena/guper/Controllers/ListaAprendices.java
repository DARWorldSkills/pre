package com.davidpopayan.sena.guper.Controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.davidpopayan.sena.guper.Fragments.FragmentPermiso;
import com.davidpopayan.sena.guper.Fragments.FragmentPermisoIn;
import com.davidpopayan.sena.guper.R;
import com.davidpopayan.sena.guper.models.AdapterListarA;
import com.davidpopayan.sena.guper.models.AprendizPermiso;
import com.davidpopayan.sena.guper.models.Constantes;
import com.davidpopayan.sena.guper.models.Permiso;
import com.davidpopayan.sena.guper.models.Persona;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListaAprendices extends AppCompatActivity {
    RecyclerView recyclerView;
    Button btnEnviar;
    List<Persona> losQueSeVanlist = new ArrayList<>();
    List<Persona> personaList = FragmentPermisoIn.aprendizListA;
    RequestQueue requestQueue;
    String motivo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_aprendices);
        personaList = FragmentPermisoIn.aprendizListA;
        motivo = FragmentPermisoIn.motivo;
        inizializar();
        listarRecycler();
        requestQueue = Volley.newRequestQueue(this);


        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                losQueSeVan();

            }
        });

    }

    public void inizializar(){

        recyclerView = findViewById(R.id.recyclerViewLA);
        btnEnviar = findViewById(R.id.btnEnviarListaA);
    }

    public void listarRecycler(){
        AdapterListarA adapterListarA = new AdapterListarA(personaList);
        recyclerView.setAdapter(adapterListarA);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);


    }

    public void losQueSeVan(){
        for (int i=0 ; i<personaList.size(); i++){
            Persona persona = personaList.get(i);
            if (persona.isSelected()){
                losQueSeVanlist.add(persona);
            }
        }
        envioDeLosPermisos();

    }

    public void envioDeLosPermisos(){
        for (int i=0; i<losQueSeVanlist.size(); i++){
            Persona persona = losQueSeVanlist.get(i);
            enviarPermiso(persona);
        }

        Toast.makeText(this, "Se ha enviado los permisos correctamente", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void enviarPermiso(final Persona persona) {
        String url = Constantes.urlPermiso;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Permiso permiso = gson.fromJson(response, Permiso.class);
                enviarAprendizPermiso(persona, permiso);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();

                parameters.put("motivo","Otro");
                parameters.put("solicitoPermisoPor", motivo);
                parameters.put("permisoPorHora",FragmentPermisoIn.horas);
                parameters.put("permisoPorDias","");
                parameters.put("horaSalida", FragmentPermisoIn.horasalida);
                parameters.put("fecha",FragmentPermisoIn.fecha);



                return parameters;
            }
        };

        requestQueue.add(stringRequest);
    }


    private void enviarAprendizPermiso(final Persona persona, final Permiso permiso) {
        String url = Constantes.urlAprendizPermiso;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("estado","Aprobado");
                parameters.put("instructor",Integer.toString(Login.personaT.getId()));
                parameters.put("permiso",permiso.getUrl());
                parameters.put("persona",persona.getUrl());


                return parameters;
            }
        };

        requestQueue.add(stringRequest);
    }


}
