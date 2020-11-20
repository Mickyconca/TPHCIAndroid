package ar.edu.itba.quickfitness.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ar.edu.itba.quickfitness.database.entity.CycleEntity;

@Dao
public abstract class CycleDao {

    @Query("SELECT * FROM Cycle")
    public abstract LiveData<List<CycleEntity>> findAll();

    @Query("SELECT * FROM Cycle LIMIT :limit OFFSET :offset")
    public abstract LiveData<List<CycleEntity>> findAll(int limit, int offset);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(CycleEntity... user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<CycleEntity> users);

    @Update
    public abstract void update(CycleEntity user);

    @Delete
    public abstract void delete(CycleEntity user);

    @Query("DELETE FROM Cycle WHERE id = :id")
    public abstract void delete(int id);

    @Query("DELETE FROM Cycle WHERE id IN (SELECT id FROM Cycle LIMIT :limit OFFSET :offset)")
    public abstract void delete(int limit, int offset);

    @Query("DELETE FROM Cycle")
    public abstract void deleteAll();

    @Query("SELECT * FROM Cycle WHERE id = :id")
    public abstract LiveData<CycleEntity> findById(int id);
}
