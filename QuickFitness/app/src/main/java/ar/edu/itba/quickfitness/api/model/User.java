package ar.edu.itba.quickfitness.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("fullName")
        @Expose
        private String fullName;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("birthdate")
        @Expose
        private Integer birthdate;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("avatarUrl")
        @Expose
        private String avatarUrl;
        @SerializedName("dateCreated")
        @Expose
        private Integer dateCreated;
        @SerializedName("dateLastActive")
        @Expose
        private Integer dateLastActive;
        @SerializedName("deleted")
        @Expose
        private Boolean deleted;
        @SerializedName("verified")
        @Expose
        private Boolean verified;

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

        public Integer getBirthdate() {
            return birthdate;
        }

        public void setBirthdate(Integer birthdate) {
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

        public Integer getDateCreated() {
            return dateCreated;
        }

        public void setDateCreated(Integer dateCreated) {
            this.dateCreated = dateCreated;
        }

        public Integer getDateLastActive() {
            return dateLastActive;
        }

        public void setDateLastActive(Integer dateLastActive) {
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

}
