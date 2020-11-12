package ar.edu.itba.quickfitness;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder> {

    List<AuxiliarRoutine> myList;

    public RoutineAdapter(List<AuxiliarRoutine> data) {
        myList = data;
    }

    //aca se hace el cableo entre lista y lo que se ve en pantalla
    @Override
    public void onBindViewHolder(@NonNull RoutineViewHolder holder, int position) {
        String name = myList.get(position).getName();
        String creator = myList.get(position).getCreator();
        int rating = myList.get(position).getRating();
        int estimated = myList.get(position).getEstimatedTime();
        holder.routineName.setText(name);
        holder.creatorName.setText(creator);
        holder.rating.setText(String.valueOf(rating));
        holder.estimatedTime.setText(String.valueOf(estimated));
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
        View view = layoutInflater.inflate(R.layout.item_routine_card,parent,false);
        return new RoutineViewHolder(view);
    }

    public static class RoutineViewHolder extends RecyclerView.ViewHolder {

        TextView routineName, creatorName, rating, estimatedTime;
        public RoutineViewHolder(@NonNull View itemView) {
            super(itemView);

            routineName = itemView.findViewById(R.id.routine_name);
            creatorName = itemView.findViewById(R.id.creatorName);
            rating = itemView.findViewById(R.id.rating);
            estimatedTime = itemView.findViewById(R.id.estimatedTime);
        }
    }
}