package com.example.a20190674_lab7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdaptParticipantes extends RecyclerView.Adapter<ListAdaptParticipantes.EventViewHolder>{

    private List<ListaSalones> listaSalones;
    private Context context;


    public ListAdaptParticipantes() {
        this.context = context;
    }

    public void setParticipanteList(List<ListaSalones> participanteList) {
        this.listaSalones = participanteList;
        notifyDataSetChanged();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        ListaSalones part = listaSalones.get(position);

        // Asigna los datos a los elementos de la vista
        holder.nameParticipante.setText(part.getNombre());
        // Aquí puedes asignar otros datos como la descripción, estado, actividad, etc.

    }

    @Override
    public int getItemCount() {
        return listaSalones != null ? listaSalones.size() : 0;
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView nameParticipante;
        Button verEvento;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            nameParticipante = itemView.findViewById(R.id.asignacionPart);
            verEvento = itemView.findViewById(R.id.verEvento);
        }
    }
}
