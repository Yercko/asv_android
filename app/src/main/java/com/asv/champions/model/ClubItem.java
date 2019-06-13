package com.asv.champions.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prueba.core.base.model.ModelType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClubItem implements ModelType {
    @SerializedName("_id")
    @Expose
    private String _id;
     @SerializedName("name")
    @Expose
    private String name;
     @SerializedName("country")
    @Expose
    private String country;
     @SerializedName("rival")
    @Expose
    private String rival;
     @SerializedName("photo")
    @Expose
    private String photo;
     @SerializedName("champions")
    @Expose
    private List<Date> champions = new ArrayList<>();

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRival() {
        return rival;
    }

    public void setRival(String rival) {
        this.rival = rival;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<Date> getChampions() {
        return champions;
    }

    public void setChampions(List<Date> champions) {
        this.champions = champions;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
