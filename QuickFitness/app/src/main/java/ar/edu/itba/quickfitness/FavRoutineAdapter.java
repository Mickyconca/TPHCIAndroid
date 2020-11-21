package ar.edu.itba.quickfitness;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ar.edu.itba.quickfitness.api.model.Routine;

public class FavRoutineAdapter extends RecyclerView.Adapter<FavRoutineAdapter.FavRoutineViewHolder> {


    ArrayList<Routine> routineList;
    public FavRoutineAdapter.MyAdapterListener onClickListener;

    public FavRoutineAdapter(ArrayList<Routine> data, FavRoutineAdapter.MyAdapterListener listener) {
        routineList = data;
        this.onClickListener = listener;
    }

    //aca se hace el cableo entre lista y lo que se ve en pantalla
    @Override
    public void onBindViewHolder(@NonNull FavRoutineAdapter.FavRoutineViewHolder holder, int position) {

        String name = routineList.get(position).getName();
        String creator = routineList.get(position).getCreator().getUsername();
        int rating = routineList.get(position).getAverageRating();
        String estimated = routineList.get(position).getDifficulty();
        int id = routineList.get(position).getId();

        holder.routineName.setText(name);
        holder.creatorName.setText(creator);
        holder.rating.setText(String.valueOf(rating));
        holder.difficulty.setText(String.valueOf(estimated));
        holder.routineId = id;
    }

    //OBLIGATORIO PARA QUE EL SO SEPA CUANTOS ITEMS TIENE
    @Override
    public int getItemCount() {
        return routineList.size();
    }


    //ES UN CONTENEDOR DE LA LISTA. MEJORA EL ACCESO A LOS ITEMS DE LA VISTA
    @NonNull
    @Override
    public FavRoutineAdapter.FavRoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_fav_routine_card,parent,false);
        return new FavRoutineAdapter.FavRoutineViewHolder(view);
    }

    public class FavRoutineViewHolder extends RecyclerView.ViewHolder{

        TextView routineName, creatorName, rating, difficulty;
        int routineId;

        public FavRoutineViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.findViewById(R.id.startRoutineButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClickStartRoutine(v, getAdapterPosition(), routineId);
                }
            });

            itemView.findViewById(R.id.favButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClickAddToFav(v,getAdapterPosition(), routineId);
                }
            });

            itemView.findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClickShareRoutine(v,getAdapterPosition(), routineId);
                }
            });

            routineName = itemView.findViewById(R.id.routine_name);
            creatorName = itemView.findViewById(R.id.creatorName);
            rating = itemView.findViewById(R.id.rating);
            difficulty = itemView.findViewById(R.id.difficulty);

        }

    }



    public interface MyAdapterListener {

        void onClickAddToFav(View v, int position, int routineId);

        void onClickStartRoutine(View v, int position, int routineId);

        void onClickShareRoutine(View v, int position, int routineId);

    }
}
