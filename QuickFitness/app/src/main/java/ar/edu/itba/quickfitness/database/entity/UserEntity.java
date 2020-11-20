package ar.edu.itba.quickfitness.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

import java.util.Date;

@Entity(tableName = "User", indices = {@Index("id")}, primaryKeys = {"id"})
public class UserEntity {
    @NonNull
    @ColumnInfo(name = "id")
    public Integer id;
    @NonNull
    @ColumnInfo(name = "username")
    public String username;
    @NonNull
    @ColumnInfo(name = "fullName")
    public String fullName;
    @NonNull
    @ColumnInfo(name = "gender")
    public String gender;
    @NonNull
    @ColumnInfo(name = "birthdate")
    public Date birthdate;
    @NonNull
    @ColumnInfo(name = "email")
    public String email;
    @NonNull
    @ColumnInfo(name = "phone")
    public String phone;
    @NonNull
    @ColumnInfo(name = "avatarUrl")
    public String avatarUrl;
    @NonNull
    @ColumnInfo(name = "dateCreated")
    public Date dateCreated;
    @NonNull
    @ColumnInfo(name = "dateLastActive")
    public Date dateLastActive;
    @NonNull
    @ColumnInfo(name = "deleted")
    public Boolean deleted;
    @NonNull
    @ColumnInfo(name = "verified")
    public Boolean verified;

    public UserEntity(Integer id, String username, String fullName, String gender, Date birthdate, String email, String phone, String avatarUrl, Date dateCreated, Date dateLastActive, Boolean deleted, Boolean verified) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.email = email;
        this.phone = phone;
        this.avatarUrl = avatarUrl;
        this.dateCreated = dateCreated;
        this.dateLastActive = dateLastActive;
        this.deleted = deleted;
        this.verified = verified;
    }
}
