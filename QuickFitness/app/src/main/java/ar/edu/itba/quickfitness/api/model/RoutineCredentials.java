package ar.edu.itba.quickfitness.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoutineCredentials {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("isPublic")
    @Expose
    private Boolean isPublic;
    @SerializedName("difficulty")
    @Expose
    private String difficulty;
    @SerializedName("category")
    @Expose
    private CategoryOrSport category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public CategoryOrSport getCategory() {
        return category;
    }

    public void setCategory(CategoryOrSport category) {
        this.category = category;
    }

}