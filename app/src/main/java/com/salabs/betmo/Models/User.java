package com.salabs.betmo.Models;

import com.facebook.AccessToken;
import com.parse.ParseClassName;
import com.parse.ParseUser;

import java.util.Date;

@ParseClassName("_User")
public class User extends ParseUser {

    // Ensure that your subclass has a public default constructor
    public User() {
        super();
    }

    // Add a constructor that contains core properties
    public User(String username) {
        super();
        setUsername(username);
    }

    // Getter and Setters for all User properties

    public String getFirstName() {
        return getString("firstName");
    }
    public void setFirstName(String firstName) {
        put("firstName", firstName);
    }

    public String getLastName() {
        return getString("lastName");
    }
    public void setLastName(String lastName) {
        put("lastName", lastName);
    }

    public String getName() {
        return (getFirstName() + " " + getLastName());
    }

    public String getUserEmail() {
        return getString("email");
    }
    public void setUserEmail(String email) {
        put("email", email);
    }

    public String getFbId() {
        return getString("fbId");
    }

    public String getProfileImageUrl() {
        return getString("profileImageUrl");
    }
    public void setProfileImageUrl(String profileImageUrl) {
        put("profileImageUrl", profileImageUrl);
    }

    //Only getter for banner
    public String getBannerImageUrl() {
        return ("https://graph.facebook.com/" + getFbId() + "?fields=cover&access_token=" + AccessToken.getCurrentAccessToken());
    }

    public String getSearchName() {
        return getString("searchName");
    }
    public void setSearchName(String searchName) {
        put("searchName", searchName);
    }

    public Date getLastOpenBetAt() {
        return getDate("lastOpenBetAt");
    }
    public void setLastOpenBetAt(Date lastOpenBetAt) {
        put("lastOpenBetAt", lastOpenBetAt);
    }
}