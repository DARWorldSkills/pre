package com.davidpopayan.sena.guper.models;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.davidpopayan.sena.guper.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterListarA extends RecyclerView.Adapter<AdapterListarA.Holder> {
    List<Persona> personaList;
    private OnItemClickListener mlistener;
    public interface OnItemClickListener{
        void itemClick(int position);
    }

    public AdapterListarA(List<Persona> personaList) {
        this.personaList = personaList;
    }

    public void setMlistener(OnItemClickListener mlistener) {
        this.mlistener = mlistener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listar_aprendiz,parent,false);
        Holder myholder = new Holder(view, mlistener);
        return myholder;
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        holder.listarAprendices(personaList.get(position));

        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(personaList.get(position).isSelected());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                personaList.get(holder.getAdapterPosition()).setSelected(isChecked);
            }
        });

    }

    @Override
    public int getItemCount() {
        return personaList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        CheckBox checkBox = itemView.findViewById(R.id.chBAprendiz);
        public Holder(View itemView, OnItemClickListener mlistener) {
            super(itemView);
        }

        public void listarAprendices(Persona persona){
            checkBox.setText(persona.getNombres()+" "+persona.getApellidos());
        }





    }
}
