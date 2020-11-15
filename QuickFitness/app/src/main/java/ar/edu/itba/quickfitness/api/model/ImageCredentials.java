package ar.edu.itba.quickfitness.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageCredentials {

    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("url")
    @Expose
    private String url;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
