package com.asv.champions.model;

import com.prueba.core.base.model.ModelType;

public class DeleteRequest implements ModelType {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
