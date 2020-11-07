package ar.edu.itba.quickfitness;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder> {

    List<String> myList;

    public RoutineAdapter(List<String> data) {
        myList = data;
    }

    //aca se hace el cableo entre lista y lo que se ve en pantalla
    @Override
    public void onBindViewHolder(@NonNull RoutineViewHolder holder, int position) {
        String name = myList.get(position);
        holder.textView.setText(name);
    }

    //OBLIGATORIO PARA QUE EL SO SEPA CUANTOS ITEMS TIENE
    @Override
    public int getItemCount() {
        return myList.size();
    }


    //ES UN CONTENEDOR DE LA LISTA. MEJORA EL ACCESO A LOS ITEMS DE LA VISTA
    @NonNull
    @Override
    public RoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.routine_card,parent,false);
        return new RoutineViewHolder(view);
    }

    public static class RoutineViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        public RoutineViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.routine_card);
        }
    }
}