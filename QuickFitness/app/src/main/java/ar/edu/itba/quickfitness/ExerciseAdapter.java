package ar.edu.itba.quickfitness;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ar.edu.itba.quickfitness.api.model.Exercise;
import ar.edu.itba.quickfitness.domain.ExerciseDomain;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    private List<ExerciseDomain> exerciseList;

    public ExerciseAdapter(List<ExerciseDomain> exercises) {
        this.exerciseList = exercises;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_exercise,parent,false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        String name = exerciseList.get(position).getName();
        String time = String.valueOf(exerciseList.get(position).getDuration());

        holder.exerciseName.setText(name);
        holder.time.setText(time);
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public class ExerciseViewHolder extends RecyclerView.ViewHolder{

        TextView exerciseName, time;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);

            exerciseName = itemView.findViewById(R.id.exercise_name);
            time = itemView.findViewById(R.id.time);

        }

    }
}
