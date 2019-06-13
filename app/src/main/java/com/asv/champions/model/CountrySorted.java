package com.asv.champions.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prueba.core.base.model.ModelType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import me.zhouzhuo.zzletterssidebar.anotation.Letter;
import me.zhouzhuo.zzletterssidebar.entity.SortModel;

public class CountrySorted extends SortModel implements ModelType {
    //the sort field must add this anotation
    @SerializedName("name")
    @Expose
    @Letter(isSortField = true)
    private String name;
    @SerializedName("name_code")
    @Expose
    private String name_code;
    @SerializedName("phone_code")
    @Expose
    private String phone_code;

    public CountrySorted(String name, String name_code, String phone_code) {
        this.name = name;
        this.name_code = name_code;
        this.phone_code = phone_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_code() {
        return name_code;
    }

    public void setName_code(String name_code) {
        this.name_code = name_code;
    }

    public String getPhone_code() {
        return phone_code;
    }

    public String getPhone_code_with_plus(){
        return "+"+phone_code;
    }

    public void setPhone_code(String phone_code) {
        this.phone_code = phone_code;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}