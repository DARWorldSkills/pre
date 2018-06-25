package com.davidpopayan.sena.guper.Controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.davidpopayan.sena.guper.Fragments.FragmentListarPermisos;
import com.davidpopayan.sena.guper.R;
import com.davidpopayan.sena.guper.models.AprendizPermiso;
import com.davidpopayan.sena.guper.models.Constantes;
import com.davidpopayan.sena.guper.models.Permiso;
import com.davidpopayan.sena.guper.models.Persona;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class DetallePermisoAprendiz extends AppCompatActivity {
    TextView txtnombre, txtdocumento, txtfecha, txtmotivo, txthoras, txtdias, txthorasalida, txtficha;
    Permiso permiso = new Permiso();
    AprendizPermiso aprendizPermiso = new AprendizPermiso();
    Persona persona = new Persona();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_permiso_aprendiz);
        inizialite();
        obtenerPersona();
    }

    private void inizialite(){
        txtnombre = findViewById(R.id.txtnombreDA);
        txtdocumento = findViewById(R.id.txtdocumentoDA);
        txtfecha = findViewById(R.id.txtfechaDA);
        txtmotivo = findViewById(R.id.txtmotivoDA);
        txthoras = findViewById(R.id.txtHorasDA);
        txtdias = findViewById(R.id.txtdiasDA);
        txthorasalida = findViewById(R.id.txtdiasDA);
        txtficha = findViewById(R.id.txtCursoFDA);

    }


    public void obtenerPersona(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url= Constantes.urlPersona;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Persona>>(){}.getType();
                List<Persona> personaList = gson.fromJson(response, type);
                for (int i=0; i<personaList.size();i++ ){
                    if (personaList.get(i).getUrl().equals(Login.personaUrl)){
                        persona = personaList.get(i);
                    }
                }

                permiso = FragmentListarPermisos.permisoA;
                aprendizPermiso = FragmentListarPermisos.aprendizPermiso;
                inputData();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);
    }

    public void inputData(){
        txtnombre.setText(persona.getNombres()+" " +persona.getApellidos());
        txtdocumento.setText(persona.getDocumentoIdentidad());
        txtfecha.setText(permiso.getFecha());
        txtmotivo.setText(permiso.getMotivo()+"\n"+permiso.getSolicitoPermisoPor());
        txthoras.setText(permiso.getPermisoPorHora());
        txtdias.setText(permiso.getPermisoPorDias());
        txthorasalida.setText(permiso.getHoraSalida());
        txtficha.setText(Login.fichaA.getNumeroFicha());

    }


}
