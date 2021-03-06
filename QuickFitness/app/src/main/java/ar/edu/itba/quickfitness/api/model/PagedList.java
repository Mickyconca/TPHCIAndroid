package ar.edu.itba.quickfitness.api.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PagedList<T> {

    @SerializedName("totalCount")
    @Expose
    private Integer totalCount;
    @SerializedName("orderBy")
    @Expose
    private String orderBy;
    @SerializedName("direction")
    @Expose
    private String direction;
    @SerializedName("results")
    @Expose
    private List<T> results;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("isLastPage")
    @Expose
    private Boolean isLastPage;

    public PagedList(Integer totalCount, String orderBy, String direction, List<T> results, Integer size, Integer page, Boolean isLastPage) {
        this.totalCount = totalCount;
        this.orderBy = orderBy;
        this.direction = direction;
        this.results = results;
        this.size = size;
        this.page = page;
        this.isLastPage = isLastPage;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Boolean getIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(Boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

}