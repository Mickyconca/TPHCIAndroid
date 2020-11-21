package ar.edu.itba.quickfitness;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ar.edu.itba.quickfitness.domain.CycleDomain;

public class CycleAdapter extends RecyclerView.Adapter<CycleAdapter.CycleViewHolder>{

    private List<CycleDomain> cycleList;

    public CycleAdapter(List<CycleDomain> cycleList) {
        this.cycleList = cycleList;
    }

    @NonNull
    @Override
    public CycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_exercise_card,parent,false);
        return new CycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CycleViewHolder holder, int position) {
        String repetitions = String.valueOf(cycleList.get(position).getRepetitions());

        holder.repetitions.setText(repetitions);
    }

    @Override
    public int getItemCount() {
        return cycleList.size();
    }

    public class CycleViewHolder extends RecyclerView.ViewHolder{

        TextView repetitions;
        View exercises;

        public CycleViewHolder(@NonNull View itemView) {
            super(itemView);

            repetitions = itemView.findViewById(R.id.repetitions);
            exercises = itemView.findViewById(R.id.fragmentExercises);

        }

    }
}
