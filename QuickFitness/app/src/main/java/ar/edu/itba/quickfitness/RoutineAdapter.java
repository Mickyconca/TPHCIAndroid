package ar.edu.itba.quickfitness;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.quickfitness.api.model.Routine;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder> {

    ArrayList<Routine> routineList;
    private RecyclerViewClickListener listener;

    public RoutineAdapter(ArrayList<Routine> data, RecyclerViewClickListener listener) {
        routineList = data;
        this.listener = listener;
    }

    //aca se hace el cableo entre lista y lo que se ve en pantalla
    @Override
    public void onBindViewHolder(@NonNull RoutineViewHolder holder, int position) {

        String name = routineList.get(position).getName();
        String creator = routineList.get(position).getCreator().getUsername();
        int rating = routineList.get(position).getAverageRating();
        int estimated = routineList.get(position).getId();

        holder.routineName.setText(name);
        holder.creatorName.setText(creator);
        holder.rating.setText(String.valueOf(rating));
        holder.difficulty.setText(String.valueOf(estimated));
    }

    public interface RecyclerViewClickListener{
        void onClick(View view, int position);
    }

    //OBLIGATORIO PARA QUE EL SO SEPA CUANTOS ITEMS TIENE
    @Override
    public int getItemCount() {
        return routineList.size();
    }


    //ES UN CONTENEDOR DE LA LISTA. MEJORA EL ACCESO A LOS ITEMS DE LA VISTA
    @NonNull
    @Override
    public RoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_routine_card,parent,false);
        return new RoutineViewHolder(view);
    }

    public class RoutineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView routineName, creatorName, rating, difficulty;
        Button startRoutine;
        public RoutineViewHolder(@NonNull View itemView) {
            super(itemView);


            routineName = itemView.findViewById(R.id.routine_name);
            creatorName = itemView.findViewById(R.id.creatorName);
            rating = itemView.findViewById(R.id.rating);
            difficulty = itemView.findViewById(R.id.difficulty);

            startRoutine = itemView.findViewById(R.id.start_routine);

            startRoutine.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }
}