package com.davidpopayan.sena.guper.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.davidpopayan.sena.guper.Controllers.DetalleListaInstruc;
import com.davidpopayan.sena.guper.Controllers.Login;
import com.davidpopayan.sena.guper.R;
import com.davidpopayan.sena.guper.models.AdapterIn;
import com.davidpopayan.sena.guper.models.AprendizFicha;
import com.davidpopayan.sena.guper.models.AprendizPermiso;
import com.davidpopayan.sena.guper.models.Constantes;
import com.davidpopayan.sena.guper.models.Ficha;
import com.davidpopayan.sena.guper.models.Permiso;
import com.davidpopayan.sena.guper.models.Persona;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentListarPermisosIn#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentListarPermisosIn extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    List<Persona> personaListA= new ArrayList<>();
    List<Ficha> fichaListA = new ArrayList<>();
    List<AprendizPermiso> aprendizPermisoListA = new ArrayList<>();
    List<AprendizFicha> aprendizFichaListA = new ArrayList<>();
    List<Permiso> permisoListA = new ArrayList<>();
    Context mContext;

    public static Persona personaY;
    public static Permiso permisoY;
    public static AprendizPermiso aprendizPermisoY;
    public static Ficha fichaY;

    public FragmentListarPermisosIn() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentListarPermisosIn.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentListarPermisosIn newInstance(String param1, String param2) {
        FragmentListarPermisosIn fragment = new FragmentListarPermisosIn();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_fragment_listar_permisos_in, container, false);
        recyclerView= v.findViewById(R.id.rvPermisoIn);


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        listarAprendizPermiso();
    }


    @Override
    public void onStart() {
        recyclerView.removeAllViewsInLayout();
        personaListA= new ArrayList<>();
        fichaListA = new ArrayList<>();
        aprendizPermisoListA = new ArrayList<>();
        aprendizFichaListA = new ArrayList<>();
        permisoListA = new ArrayList<>();
        super.onStart();

    }

    public void listarAprendizPermiso(){
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        String url= Constantes.urlAprendizPermiso;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<AprendizPermiso>>(){}.getType();
                List<AprendizPermiso> aprendizPermisoList = gson.fromJson(response, type);
                AprendizPermiso aprendizPermiso = new AprendizPermiso();
                for (int i=0; i<aprendizPermisoList.size(); i++){
                    aprendizPermiso = aprendizPermisoList.get(i);
                    try {
                        if (aprendizPermiso.getInstructor().equals(Integer.toString(Login.personaT.getId())) && aprendizPermiso.getEstado().equals("En Espera")){
                            aprendizPermisoListA.add(aprendizPermiso);
                        }
                    }catch (Exception e){

                    }

                }
                listarPermisos();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);
    }

    public void listarPermisos(){
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        String url = Constantes.urlPermiso;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type type = new  TypeToken<List<Permiso>>(){}.getType();
                List<Permiso> permisoList = gson.fromJson(response, type);
                Permiso permiso;
                AprendizPermiso aprendizPermiso;
                for (int i=permisoList.size()-1;i>=0; i--){
                     permiso = permisoList.get(i);
                    for (int j=aprendizPermisoListA.size()-1; j>=0; j--){
                        aprendizPermiso = aprendizPermisoListA.get(j);
                        if (permiso.getUrl().equals(aprendizPermiso.getPermiso())){
                            permisoListA.add(permiso);

                        }
                    }
                }
                listarPersona();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);
    }

    public void listarPersona(){
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        String url = Constantes.urlPersona;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type type = new  TypeToken<List<Persona>>(){}.getType();
                List<Persona> personaList = gson.fromJson(response, type);
                Persona persona;
                AprendizPermiso aprendizPermiso;
                for (int i=0;i<personaList.size(); i++){
                    persona = personaList.get(i);
                    for (int j=0; j<aprendizPermisoListA.size(); j++){
                        aprendizPermiso = aprendizPermisoListA.get(j);
                        if (persona.getUrl().equals(aprendizPermiso.getPersona())){
                            personaListA.add(persona);

                        }
                    }
                }
                listarApredizFicha();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);
    }

    public void listarApredizFicha(){
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        String url = Constantes.urlAprendizFicha;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<AprendizFicha>>(){}.getType();
                List<AprendizFicha> aprendizFichaList = gson.fromJson(response,type);
                AprendizFicha aprendizFicha;
                Persona persona;
                for (int i=0; i<aprendizFichaList.size(); i++){
                    aprendizFicha =aprendizFichaList.get(i);
                    for (int j=0; j<personaListA.size(); j++){
                        persona = personaListA.get(j);

                        if (aprendizFicha.getPersona().equals(persona.getUrl())) {
                            aprendizFichaListA.add(aprendizFicha);

                        }

                    }
                }
                listarFicha();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);
    }

    public void listarFicha(){
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        String url = Constantes.urlFicha;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Ficha>>(){}.getType();
                List<Ficha> fichaList = gson.fromJson(response, type);
                Ficha ficha;
                AprendizFicha aprendizFicha;
                for (int i=0; i<fichaList.size(); i++){
                    ficha = fichaList.get(i);
                    for (int j=0; j<aprendizFichaListA.size(); j++){
                        aprendizFicha = aprendizFichaListA.get(j);
                        if (ficha.getUrl().equals(aprendizFicha.getFicha())){
                            fichaListA.add(ficha);


                        }
                    }
                }

                AdapterIn adapterIn = new AdapterIn(personaListA,fichaListA, getContext());
                recyclerView.setAdapter(adapterIn);
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false));
                recyclerView.setHasFixedSize(true);
                adapterIn.setMlistener(new AdapterIn.OnItemClickListener() {
                    @Override
                    public void itemClick(int position) {
                        personaY = personaListA.get(position);
                        permisoY = permisoListA.get(position);
                        aprendizPermisoY = aprendizPermisoListA.get(position);
                        fichaY = fichaListA.get(position);
                        Intent intent = new Intent(mContext, DetalleListaInstruc.class);
                        startActivity(intent);
                    }
                });


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);

    }






}
