package com.davidpopayan.sena.guper.Controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText txtUser, txtPass;
    Button btnLogin;
    public static String userUrl;
    public static String personaN;
    public static String personaUrl;
    public static Persona personaT;
    public static User userT;
    public static Ficha fichaA;
    AprendizFicha aprendizFichaA;
    List<Rol> rolList;
    public static int iniciarSesion=0;
    boolean rolB = true;
    int contador;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inicializar();
        btnLogin.setEnabled(true);



        //consultaRol();
    }



    private void inicializar() {

        txtUser = findViewById(R.id.txtUser);
        txtPass = findViewById(R.id.txtPass);
        btnLogin = findViewById(R.id.btnLogin);
        Date date = new Date();

    }

    public void enviar(View view) {

        btnLogin.setEnabled(false);
        String user = txtUser.getText().toString();
        String pass = txtPass.getText().toString();


        if (user.isEmpty()){
            txtUser.setError("Campo obligatorio");
            txtUser.requestFocus();
            btnLogin.setEnabled(true);
        }else if (pass.isEmpty()){
            txtPass.setError("Campo obligatorio");
            txtPass.requestFocus();
            btnLogin.setEnabled(true);
        }

        Roles(user, pass);




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
                Toast.makeText(Login.this, "El usuario o la contraseña son incorrectos", Toast.LENGTH_SHORT).show();
                btnLogin.setEnabled(true);
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
                        userUrl=userList.get(i).getUrl();

                    }
                }

                obtenerPersona();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, "Falta algo :( ", Toast.LENGTH_SHORT).show();
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
                    if (personaList.get(i).getUsuario().equals(userUrl)){
                        personaN = personaList.get(i).getNombres();
                        personaUrl = personaList.get(i).getUrl();
                        personaT = personaList.get(i);


                    }
                }
                iniciarComo();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, "Por favor conectarse a una red", Toast.LENGTH_SHORT).show();
                btnLogin.setEnabled(true);
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
                Toast.makeText(Login.this, "Error", Toast.LENGTH_SHORT).show();
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
                                && rolPersonaList.get(i).getPersona().equals(personaUrl)) {
                            iniciarSesion=1;
                            guardarCambiar();
                            return;
                        }
                        else{


                            if (rolList.get(j).getRol().equals("INSTRUCTOR") && rolPersonaList.get(i).getRol().equals(rolList.get(j).getUrl())
                                    && rolPersonaList.get(i).getPersona().equals(personaUrl)) {
                                iniciarSesion=2;
                                guardarCambiar();
                                return;
                            }

                        }


                    }

                }

                Toast.makeText(Login.this, "Usuario no autorizado", Toast.LENGTH_SHORT).show();
                btnLogin.setEnabled(true);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, "Error 4", Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(stringRequest);
    }

    public void guardarCambiar(){
        guardarSesion();
        obtenerPersonaFicha();
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void guardarSesion(){
        SharedPreferences usuarioI= getSharedPreferences("iniciarSesion", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = usuarioI.edit();
        editor.putString("user",txtUser.getText().toString());
        editor.putString("password",txtPass.getText().toString());
        editor.commit();
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
                    if (aprendizFicha.getPersona().equals(personaUrl)){
                        aprendizFichaA =aprendizFicha;
                    }
                }
                obtenerFicha();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, "Error 5", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    public void obtenerFicha(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Constantes.urlFicha;;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Ficha>>(){}.getType();
                List<Ficha> fichaList = gson.fromJson(response,type);
                for (int i=0; i<fichaList.size(); i++){
                    Ficha ficha = fichaList.get(i);
                    if (ficha.getUrl().equals(aprendizFichaA.getFicha())){
                        fichaA=ficha;
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, "Error 6", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);

    }




}

