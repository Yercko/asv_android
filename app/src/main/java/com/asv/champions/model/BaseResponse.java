package com.asv.champions.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prueba.core.base.model.ModelType;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class BaseResponse implements ModelType {
    @SerializedName("hasError")
    @Expose
    boolean hasError = false;

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
