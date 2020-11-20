package ar.edu.itba.quickfitness.domain;

import java.util.Date;
import java.util.Objects;

import ar.edu.itba.quickfitness.api.model.CategoryOrSport;
import ar.edu.itba.quickfitness.api.model.Creator;

public class RoutineDomain {

    private Integer id;
    private String name;
    private String detail;
    private Date dateCreated;
    private Integer averageRating;
    private Boolean isPublic;
    private String difficulty;
    private Creator creator;
    private CategoryOrSport category;

    public RoutineDomain(Integer id, String name, String detail, Date dateCreated, Integer averageRating, Boolean isPublic, String difficulty, Creator creator, CategoryOrSport category) {
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

    public RoutineDomain(String name, String detail, Date dateCreated, Integer averageRating, Boolean isPublic, String difficulty, Creator creator, CategoryOrSport category) {
        this.id = 0;
        this.name = name;
        this.detail = detail;
        this.dateCreated = dateCreated;
        this.averageRating = averageRating;
        this.isPublic = isPublic;
        this.difficulty = difficulty;
        this.creator = creator;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Integer averageRating) {
        this.averageRating = averageRating;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public CategoryOrSport getCategory() {
        return category;
    }

    public void setCategory(CategoryOrSport category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoutineDomain that = (RoutineDomain) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(detail, that.detail) &&
                Objects.equals(dateCreated, that.dateCreated) &&
                Objects.equals(averageRating, that.averageRating) &&
                Objects.equals(isPublic, that.isPublic) &&
                Objects.equals(difficulty, that.difficulty) &&
                Objects.equals(creator, that.creator) &&
                Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, detail, dateCreated, averageRating, isPublic, difficulty, creator, category);
    }
}
