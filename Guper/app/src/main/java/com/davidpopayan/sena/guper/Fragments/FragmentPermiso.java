package com.davidpopayan.sena.guper.Fragments;


import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.davidpopayan.sena.guper.Controllers.Login;
import com.davidpopayan.sena.guper.Controllers.MainActivity;
import com.davidpopayan.sena.guper.R;
import com.davidpopayan.sena.guper.models.AprendizFicha;
import com.davidpopayan.sena.guper.models.Constantes;
import com.davidpopayan.sena.guper.models.Permiso;
import com.davidpopayan.sena.guper.models.Persona;
import com.davidpopayan.sena.guper.models.Rol;
import com.davidpopayan.sena.guper.models.RolPersona;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPermiso extends Fragment implements View.OnClickListener{
    ImageButton btnHora1, btnHora11;
    ImageView imagePer;
    EditText txtHora1,txtHoraT1;
    EditText txtSolicitarP;
    Button btnenviar;
    Spinner spMotivo, spinstructor;
    TextView txtNombrePed, txtDocumento, txtfecha;

    private static  final String Cero = "0";
    private static  final  String DOS_PUNTOS = ":";

    public final Calendar c = Calendar.getInstance();

    private int hora = c.get(Calendar.HOUR_OF_DAY);
    private int minuto = c.get(Calendar.MINUTE);

    public Permiso permisoP = new Permiso();
    Date date = new Date();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public Persona personaP;
    Context mContext;



    public FragmentPermiso() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_permiso, container, false);
        mContext = getContext();

        btnHora1 = view.findViewById(R.id.btnHora1);
        btnHora11 = view.findViewById(R.id.btnHora11);
        txtHora1 = view.findViewById(R.id.txtHota1);
        txtHoraT1 = view.findViewById(R.id.txtHoraT1);
        txtSolicitarP = view.findViewById(R.id.txtdetalleSP);
        btnenviar = view.findViewById(R.id.btnEnviar);
        spMotivo = view.findViewById(R.id.spmotivoP);
        spinstructor = view.findViewById(R.id.spInstructor);
        txtNombrePed = view.findViewById(R.id.txtNombreSP);
        txtDocumento = view.findViewById(R.id.txtdocumentoSP);
        txtfecha = view.findViewById(R.id.txtFechaSP);
        imagePer = view.findViewById(R.id.imgPersonaP);

        personaP = Login.personaT;
        listarMotivos();


        btnHora1.setOnClickListener(this);
        btnHora11.setOnClickListener(this);
        btnenviar.setOnClickListener(this);

        txtNombrePed.setText(Login.personaT.getNombres()+ " " + Login.personaT.getApellidos());
        txtDocumento.setText(Login.personaT.getDocumentoIdentidad());
        txtfecha.setText(dateFormat.format(date).toString());
        if (personaP.getUrl().equals(null)) {
            Picasso.with(getContext()).load(personaP.getImgPerfil()).into(imagePer);
        }else {
            Toast.makeText(mContext, "No tienes una imagen", Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,MainActivity.instructorList);
        spinstructor.setAdapter(adapter);


        return view;
    }


    public void listarMotivos(){
        List<String> datos = new ArrayList<>();
        datos.add("Enfermedad");
        datos.add("Accidente");
        datos.add("Calamidad domestica");
        datos.add("Otro");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item,datos);
        spMotivo.setAdapter(arrayAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        personaP = Login.personaT;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.btnHora1:
                obtenerHora1();

                break;


            case R.id.btnHora11:
                obtenerHora11();

                break;


            case R.id.btnEnviar:
                solicitar_permiso();
                btnenviar.setEnabled(false);

                break;
        }
    }


    private void obtenerHora11() {
        numberpicker1();


    }


    private void numberpicker1(){
        NumberPicker mynumberpicker = new NumberPicker(getActivity());
        mynumberpicker.setMaxValue(6);
        mynumberpicker.setMinValue(0);
        NumberPicker.OnValueChangeListener myvaluechange = new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                txtHoraT1.setText(""+newVal);
            }
        };
        mynumberpicker.setOnValueChangedListener(myvaluechange);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(mynumberpicker);
        builder.setTitle("Hora");
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }


    private void obtenerHora1() {

        TimePickerDialog recoger = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                String hora =(hourOfDay <10) ? String.valueOf(Cero + hourOfDay) : String.valueOf(hourOfDay);
                String minuto = (minute <10) ? String.valueOf(Cero + minute) : String.valueOf(minute);
                String AM_PM;

                if (hourOfDay<12){
                    AM_PM = "a.m.";
                }else {
                    AM_PM = "p.m.";
                }
                txtHora1.setText(hora + DOS_PUNTOS + minuto + "" + AM_PM);

            }
        }, hora , minuto , false);
        recoger.show();

    }


    public void solicitar_permiso(){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = Constantes.urlPermiso;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                permisoP = gson.fromJson(response, permisoP.getClass());
                aprendizPermiso();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "No se ha llenado los datos necesarios", Toast.LENGTH_SHORT).show();
                btnenviar.setEnabled(true);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("motivo",spMotivo.getSelectedItem().toString());
                parameters.put("solicitoPermisoPor",txtSolicitarP.getText().toString());
                parameters.put("permisoPorHora",txtHoraT1.getText().toString());
                parameters.put("permisoPorDias","2");
                parameters.put("horaSalida",txtHora1.getText().toString());
                parameters.put("fecha",dateFormat.format(date));


                return  parameters;
            }
        };

        requestQueue.add(request);

    }

    public void aprendizPermiso(){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url=Constantes.urlAprendizPermiso;
        StringRequest stringRequest  = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "Se ha solicitado el permiso correctamente", Toast.LENGTH_SHORT).show();
                FragmentListarPermisos nextFrag= new FragmentListarPermisos();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contenedor, nextFrag,"FragmentListarPermisos")
                        .commit();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("estado","En Espera");
                String [] tmpId = spinstructor.getSelectedItem().toString().split("-");
                String parte = tmpId[0];
                parameters.put("instructor",parte);
                parameters.put("permiso",permisoP.getUrl());
                parameters.put("persona",personaP.getUrl());

                return parameters;
            }
        };

        requestQueue.add(stringRequest);
    }


}
