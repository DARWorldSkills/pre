package com.davidpopayan.sena.guper.Controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.davidpopayan.sena.guper.R;
import com.davidpopayan.sena.guper.models.AprendizFicha;
import com.davidpopayan.sena.guper.models.Constantes;
import com.davidpopayan.sena.guper.models.Ficha;
import com.davidpopayan.sena.guper.models.Persona;
import com.davidpopayan.sena.guper.models.Rol;
import com.davidpopayan.sena.guper.models.RolPersona;
import com.davidpopayan.sena.guper.models.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class splash extends AppCompatActivity {


    List<Rol> rolList;
    AprendizFicha aprendizFichaA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        SharedPreferences usuarioI = getSharedPreferences("iniciarSesion",Context.MODE_PRIVATE);
        String user = usuarioI.getString("user","No existe");
        String pass = usuarioI.getString("password","No existe");
        if (user.equals("No existe") || pass.equals("No existe")) {
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(splash.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            };

            Timer timer = new Timer();
            timer.schedule(timerTask, 1000);
        }else {

            Roles(user,pass);
        }


    }

    public void logeo(final String username, final String email , final String password){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Constantes.urlLogin;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                obtenerUrlUsuario(username);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                SharedPreferences sharedPreferences = getSharedPreferences("iniciarSesion", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("user","No existe");
                editor.putString("password","No existe");
                editor.commit();
                Toast.makeText(splash.this, "El usuario no existe", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(splash.this, Login.class);
                startActivity(intent);
                finish();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("username", username);
                parameters.put("email", email);
                parameters.put("password", password);
                return parameters;
            }
        };

        requestQueue.add(stringRequest);

    }

    public void obtenerUrlUsuario(final String username){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Constantes.urlUser;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type tipodelista= new TypeToken<List<User>>(){}.getType();
                List<User> userList = gson.fromJson(response,tipodelista);

                for (int i=0; i<userList.size();i++){
                    if (userList.get(i).getUsername().equals(username)){
                        Login.userUrl=userList.get(i).getUrl();

                    }
                }

                obtenerPersona();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(splash.this, "Falta algo :( ", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);


    }


    public void obtenerPersona(){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url=Constantes.urlPersona;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type tipodelista = new TypeToken<List<Persona>>(){}.getType();
                List<Persona> personaList = gson.fromJson(response,tipodelista);
                for (int i=0; i<personaList.size();i++){
                    if (personaList.get(i).getUsuario().equals(Login.userUrl)){
                        Login.personaN = personaList.get(i).getNombres();
                        Login.personaUrl = personaList.get(i).getUrl();
                        Login.personaT = personaList.get(i);


                    }
                }
                iniciarComo();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(splash.this, "Error de inicio 2", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);

    }

    public void Roles(final String user, final String pass){
        RequestQueue requestQueue = new Volley().newRequestQueue(this);
        String url = Constantes.urlRol;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Rol>>(){}.getType();
                rolList = new ArrayList<>();
                rolList = gson.fromJson(response, type);
                logeo(user, "", pass);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(splash.this, "Por favor conectarse a una red ", Toast.LENGTH_LONG).show();

            }
        });

        requestQueue.add(stringRequest);
    }

    public void iniciarComo(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Constantes.urlRolPersona;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<RolPersona>>(){}.getType();
                List<RolPersona> rolPersonaList = gson.fromJson(response,type);
                for (int i=0; i<rolPersonaList.size();i++){
                    for (int j=0; j<rolList.size(); j++){
                        if (rolList.get(j).getRol().equals("APRENDIZ") && rolPersonaList.get(i).getRol().equals(rolList.get(j).getUrl())
                                && rolPersonaList.get(i).getPersona().equals(Login.personaUrl)) {
                            Login.iniciarSesion=1;
                            guardarCambiar();
                            return;
                        }
                        else{


                            if (rolList.get(j).getRol().equals("INSTRUCTOR") && rolPersonaList.get(i).getRol().equals(rolList.get(j).getUrl())
                                    && rolPersonaList.get(i).getPersona().equals(Login.personaUrl)) {
                                Login.iniciarSesion=2;
                                guardarCambiar();
                                return;
                            }

                        }


                    }

                }

                Toast.makeText(splash.this, "Error de inicio automático", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(splash.this, Login.class);
                startActivity(intent);
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        requestQueue.add(stringRequest);
    }

    public void guardarCambiar(){
        obtenerPersonaFicha();
        Intent intent = new Intent(splash.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    public void obtenerPersonaFicha(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Constantes.urlAprendizFicha;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<AprendizFicha>>(){}.getType();
                List<AprendizFicha> aprendizFichaList = gson.fromJson(response, type);
                for (int i=0; i<aprendizFichaList.size(); i++){
                    AprendizFicha aprendizFicha = aprendizFichaList.get(i);
                    if (aprendizFicha.getPersona().equals(Login.personaUrl)){
                        aprendizFichaA =aprendizFicha;
                    }
                }
                obtenerFicha();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);
    }

    public void obtenerFicha(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Constantes.urlFicha;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Ficha>>(){}.getType();
                List<Ficha> fichaList = gson.fromJson(response,type);
                for (int i=0; i<fichaList.size(); i++){
                    Ficha ficha = fichaList.get(i);
                    if (ficha.getUrl().equals(aprendizFichaA.getFicha())){
                        Login.fichaA=ficha;

                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        requestQueue.add(stringRequest);

    }


}
