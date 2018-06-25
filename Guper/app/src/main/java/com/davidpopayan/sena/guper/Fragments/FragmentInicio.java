package com.davidpopayan.sena.guper.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.app.TimePickerDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.davidpopayan.sena.guper.Controllers.Login;
import com.davidpopayan.sena.guper.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentInicio extends Fragment{
    TextView txtNombre;





    public FragmentInicio() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_inicio, container, false);

        txtNombre = view.findViewById(R.id.nombreprinci);
        txtNombre.setText(Login.personaN);


         return view;
    }


}





