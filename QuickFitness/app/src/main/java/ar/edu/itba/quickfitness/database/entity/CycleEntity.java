package ar.edu.itba.quickfitness.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

@Entity(tableName = "Cycle", indices = {@Index("id")}, primaryKeys = {"id"})
public class CycleEntity {

    @NonNull
    @ColumnInfo(name = "id")
    public Integer id;
    @NonNull
    @ColumnInfo(name = "name")
    public String name;
    @NonNull
    @ColumnInfo(name = "detail")
    public String detail;
    @NonNull
    @ColumnInfo(name = "type")
    public String type;
    @NonNull
    @ColumnInfo(name = "order")
    public Integer order;
    @NonNull
    @ColumnInfo(name = "repetitionsd")
    public Integer repetitions;

    public CycleEntity(Integer id, String name, String detail, String type, Integer order, Integer repetitions) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.type = type;
        this.order = order;
        this.repetitions = repetitions;
    }
}
