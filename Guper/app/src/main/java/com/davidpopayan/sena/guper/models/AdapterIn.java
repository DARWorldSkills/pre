package com.davidpopayan.sena.guper.models;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.davidpopayan.sena.guper.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterIn extends RecyclerView.Adapter<AdapterIn.Holder> {
    List<Persona> personaList = new ArrayList<>();
    List<Ficha> fichaList = new ArrayList<>();
    Context context;

    private OnItemClickListener mlistener;
    public interface  OnItemClickListener{
        void itemClick(int position);
    }


    public AdapterIn(List<Persona> personaList, List<Ficha> fichaList, Context context) {
        this.personaList = personaList;
        this.fichaList = fichaList;
        this.context = context;
    }

    public void setMlistener(OnItemClickListener mlistener) {
        this.mlistener = mlistener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listar_permiso_instructor,parent, false);
        Holder myHolder = new Holder(view,mlistener);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.connectData(personaList.get(position),fichaList.get(position),context);
    }

    @Override
    public int getItemCount() {
        return personaList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView txtnombre = itemView.findViewById(R.id.txtnombreIt);
        TextView txtdocumento = itemView.findViewById(R.id.txtdocumentoIn);
        TextView txtficha = itemView.findViewById(R.id.txtfichaIn);
        ImageView imgAprendiz = itemView.findViewById(R.id.imgAprendizLI);

        public Holder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        int position= getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION){
                            listener.itemClick(position);

                        }
                    }
                }
            });


        }
        public void connectData(Persona persona, Ficha ficha, Context context){
            txtnombre.setText(persona.getNombres() +"\n"+ persona.getApellidos() );
            txtdocumento.setText(persona.getDocumentoIdentidad());
            txtficha.setText(ficha.getNumeroFicha());
            Picasso.with(context).load(persona.getImgPerfil()).into(imgAprendiz);
        }



    }


}
