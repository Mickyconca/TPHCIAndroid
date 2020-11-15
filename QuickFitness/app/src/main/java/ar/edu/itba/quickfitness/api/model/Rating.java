package ar.edu.itba.quickfitness.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rating {

    @SerializedName("score")
    @Expose
    private String score;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("routine")
    @Expose
    private Routine routine;

    public Rating(String score, String review, Routine routine) {
        this.score = score;
        this.review = review;
        this.routine = routine;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Routine getRoutine() {
        return routine;
    }

    public void setRoutine(Routine routine) {
        this.routine = routine;
    }
}
