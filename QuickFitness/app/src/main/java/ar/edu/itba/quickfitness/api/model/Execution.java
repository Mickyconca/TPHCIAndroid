package ar.edu.itba.quickfitness.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Execution {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("date")
    @Expose
    private Date date;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("wasModified")
    @Expose
    private Boolean wasModified;
    @SerializedName("routine")
    @Expose
    private Routine routine;

    public Execution(Integer id, Date date, Integer duration, Boolean wasModified, Routine routine) {
        this.id = id;
        this.date = date;
        this.duration = duration;
        this.wasModified = wasModified;
        this.routine = routine;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Boolean getWasModified() {
        return wasModified;
    }

    public void setWasModified(Boolean wasModified) {
        this.wasModified = wasModified;
    }

    public Routine getRoutine() {
        return routine;
    }

    public void setRoutine(Routine routine) {
        this.routine = routine;
    }

}
