package ar.edu.itba.quickfitness.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Objects;

public class UserDomain {
    private Integer id;
    private String username;
    private String fullName;
    private String gender;
    private Date birthdate;
    private String email;
    private String phone;
    private String avatarUrl;
    private Date dateCreated;
    private Date dateLastActive;
    private Boolean deleted;
    private Boolean verified;

    public UserDomain(Integer id, String username, String fullName, String gender, Date birthdate, String email, String phone, String avatarUrl, Date dateCreated, Date dateLastActive, Boolean deleted, Boolean verified) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.email = email;
        this.phone = phone;

        if (avatarUrl == null)
            this.avatarUrl = "https://flic.kr/p/3ntH2u";
        else
            this.avatarUrl = avatarUrl;

        this.dateCreated = dateCreated;
        this.dateLastActive = dateLastActive;
        this.deleted = deleted;
        this.verified = verified;
    }

    public UserDomain(String username, String fullName, String gender, Date birthdate, String email, String phone, String avatarUrl, Date dateCreated, Date dateLastActive, Boolean deleted, Boolean verified) {
        this.id = 0;
        this.username = username;
        this.fullName = fullName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.email = email;
        this.phone = phone;

        if (avatarUrl == null)
            this.avatarUrl = "https://flic.kr/p/3ntH2u";
        else
            this.avatarUrl = avatarUrl;

        this.dateCreated = dateCreated;
        this.dateLastActive = dateLastActive;
        this.deleted = deleted;
        this.verified = verified;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateLastActive() {
        return dateLastActive;
    }

    public void setDateLastActive(Date dateLastActive) {
        this.dateLastActive = dateLastActive;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDomain that = (UserDomain) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(username, that.username) &&
                Objects.equals(fullName, that.fullName) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(birthdate, that.birthdate) &&
                Objects.equals(email, that.email) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(avatarUrl, that.avatarUrl) &&
                Objects.equals(dateCreated, that.dateCreated) &&
                Objects.equals(dateLastActive, that.dateLastActive) &&
                Objects.equals(deleted, that.deleted) &&
                Objects.equals(verified, that.verified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, fullName, gender, birthdate, email, phone, avatarUrl, dateCreated, dateLastActive, deleted, verified);
    }
}
