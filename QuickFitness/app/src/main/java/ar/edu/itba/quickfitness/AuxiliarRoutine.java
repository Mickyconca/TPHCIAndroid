package ar.edu.itba.quickfitness;

public class AuxiliarRoutine {
    private String name, creator;
    private int rating, estimatedTime;

    public AuxiliarRoutine(String name, String creator, int rating, int estimatedTime) {
        this.name = name;
        this.creator = creator;
        this.rating = rating;
        this.estimatedTime = estimatedTime;
    }

    public String getName() {
        return name;
    }

    public String getCreator() {
        return creator;
    }

    public int getRating() {
        return rating;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }
}
