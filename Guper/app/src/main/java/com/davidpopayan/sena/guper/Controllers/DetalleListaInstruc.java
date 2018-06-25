package com.davidpopayan.sena.guper.Controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.davidpopayan.sena.guper.Fragments.FragmentListarPermisos;
import com.davidpopayan.sena.guper.Fragments.FragmentListarPermisosIn;
import com.davidpopayan.sena.guper.R;
import com.davidpopayan.sena.guper.models.AprendizPermiso;
import com.davidpopayan.sena.guper.models.Constantes;
import com.davidpopayan.sena.guper.models.Ficha;
import com.davidpopayan.sena.guper.models.Permiso;
import com.davidpopayan.sena.guper.models.Persona;

import java.util.HashMap;
import java.util.Map;

public class DetalleListaInstruc extends AppCompatActivity {

    Button btnAceptar, btnRechazar;
    String solicitud;
    Persona personaY;
    Permiso permisoY;
    AprendizPermiso aprendizPermisoY;
    Ficha fichaY;
    TextView txtnombre, txtdocumento, txtfecha, txtmotivo, txthoras, txtdias, txthoraDeSalida, txtficha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_lista_instruc);
        permisoY= FragmentListarPermisosIn.permisoY;
        personaY= FragmentListarPermisosIn.personaY;
        aprendizPermisoY= FragmentListarPermisosIn.aprendizPermisoY;
        fichaY = FragmentListarPermisosIn.fichaY;
        inizialite();
        inputData();

    }

    public void inizialite(){
        btnAceptar = findViewById(R.id.btnAceptar);
        btnRechazar = findViewById(R.id.btnRechazar);
        txtnombre = findViewById(R.id.txtnombreDIn);
        txtdocumento = findViewById(R.id.txtdocumentoDIn);
        txtfecha = findViewById(R.id.txtfechaDIn);
        txtmotivo = findViewById(R.id.txtmotivoDIn);
        txthoras = findViewById(R.id.txtHorasDIn);
        txthoraDeSalida = findViewById(R.id.txtHoraSalidaDIn);
        txtficha = findViewById(R.id.txtCursoFDIn);

    }

    public void inputData(){
        txtnombre.setText(personaY.getNombres()+" "+personaY.getApellidos());
        txtdocumento.setText(personaY.getDocumentoIdentidad());
        txtfecha.setText(permisoY.getFecha());
        txtmotivo.setText(permisoY.getMotivo()+"\n"+permisoY.getSolicitoPermisoPor());
        txthoras.setText(permisoY.getPermisoPorHora());
        txthoraDeSalida.setText(permisoY.getHoraSalida());
        txtficha.setText(fichaY.getNumeroFicha());

    }

    @Override
    protected void onResume() {
        super.onResume();
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicitud = "Aprobado";
                solicitudDePermsio(solicitud);
                finish();
            }
        });

        btnRechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicitud = "Rechazado";
                solicitudDePermsio(solicitud);
                finish();
            }
        });
    }

    public void solicitudDePermsio(final String solicitud){
        RequestQueue requetQueue = Volley.newRequestQueue(this);
        String url = aprendizPermisoY.getUrl();
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(DetalleListaInstruc.this, "Ha terminado la tarea completa", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetalleListaInstruc.this, "Error: "+error.toString(), Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("estado",solicitud);
                parameters.put("instructor",Integer.toString(Login.personaT.getId()));
                parameters.put("permiso",permisoY.getUrl());
                parameters.put("persona",personaY.getUrl());
                return parameters;
            }
        };
        requetQueue.add(stringRequest);

    }







}
