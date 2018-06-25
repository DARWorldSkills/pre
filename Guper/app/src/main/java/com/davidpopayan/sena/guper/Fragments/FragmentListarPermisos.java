package com.davidpopayan.sena.guper.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.davidpopayan.sena.guper.Controllers.DetallePermisoAprendiz;
import com.davidpopayan.sena.guper.Controllers.Login;
import com.davidpopayan.sena.guper.R;
import com.davidpopayan.sena.guper.models.AdapterS;
import com.davidpopayan.sena.guper.models.AprendizPermiso;
import com.davidpopayan.sena.guper.models.Constantes;
import com.davidpopayan.sena.guper.models.Permiso;
import com.davidpopayan.sena.guper.models.Persona;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentListarPermisos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentListarPermisos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentListarPermisos extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    public Persona persona = new Persona();
    public static AprendizPermiso aprendizPermiso = new AprendizPermiso();
    public static Permiso permisoA = new Permiso();

    List<Permiso> permisoAList = new ArrayList<>();
    List<AprendizPermiso> aprendizAPermisoList = new ArrayList<>();
    List<Persona> personaAList = new ArrayList<>();
    Context mContext;
    private OnFragmentInteractionListener mListener;

    public FragmentListarPermisos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentListarPermisos.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentListarPermisos newInstance(String param1, String param2) {
        FragmentListarPermisos fragment = new FragmentListarPermisos();
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
        View v = inflater.inflate(R.layout.fragment_fragment_listar_permisos, container, false);
        recyclerView = v.findViewById(R.id.recyclerListaA);
        listarAprendizPermiso();
        return v;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void listarAprendizPermiso(){
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        String url = Constantes.urlAprendizPermiso;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson  = new Gson();
                Type type = new TypeToken<List<AprendizPermiso>>(){}.getType();
                List<AprendizPermiso> aprendizPermisoList = gson.fromJson(response, type);
                for (int i=aprendizPermisoList.size()-1; i>=0; i--){
                    if (Login.personaUrl.equals(aprendizPermisoList.get(i).getPersona())){

                        aprendizAPermisoList.add(aprendizPermisoList.get(i));
                    }
                }
                listarPermiso();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);




    }


    public void listarPermiso(){
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        String url= Constantes.urlPermiso;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Permiso>>(){}.getType();
                List<Permiso> permisoList = gson.fromJson(response, type);
                for (int i=permisoList.size()-1; i>=0; i--){
                    for (int j=aprendizAPermisoList.size()-1; j>=0; j--) {
                        if (permisoList.get(i).getUrl().equals(aprendizAPermisoList.get(j).getPermiso())){
                            permisoAList.add(permisoList.get(i));

                        }

                    }
                }

                AdapterS adapterS = new AdapterS(permisoAList,aprendizAPermisoList);

                recyclerView.setAdapter(adapterS);
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
                recyclerView.setHasFixedSize(true);
                adapterS.setMlistener(new AdapterS.OnItemClickListener() {
                    @Override
                    public void itemClick(int position) {
                        aprendizPermiso = aprendizAPermisoList.get(position);
                        permisoA = permisoAList.get(position);
                        Intent intent =new Intent(mContext , DetallePermisoAprendiz.class);
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
