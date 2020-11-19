package ar.edu.itba.quickfitness.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

import java.util.Date;

import ar.edu.itba.quickfitness.api.model.CategoryOrSport;
import ar.edu.itba.quickfitness.api.model.Creator;

@Entity(tableName = "Routine", indices = {@Index("id")}, primaryKeys = {"id"})
public class RoutineEntity {

    @NonNull
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "detail")
    public String detail;
    @ColumnInfo(name = "dateCreated")
    public Date dateCreated;
    @ColumnInfo(name = "averageRating")
    public Integer averageRating;
    @ColumnInfo(name = "isPublic")
    public Boolean isPublic;
    @ColumnInfo(name = "difficulty")
    public String difficulty;
    @ColumnInfo(name = "creator")
    public Creator creator;
    @ColumnInfo(name = "category")
    public CategoryOrSport category;

    public RoutineEntity(int id, String name, String detail, Date dateCreated, Integer averageRating, Boolean isPublic, String difficulty, Creator creator, CategoryOrSport category) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.dateCreated = dateCreated;
        this.averageRating = averageRating;
        this.isPublic = isPublic;
        this.difficulty = difficulty;
        this.creator = creator;
        this.category = category;
    }
}