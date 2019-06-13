package com.asv.champions.ui.selector;

import com.prueba.core.base.model.ModelType;

import me.zhouzhuo.zzletterssidebar.anotation.Letter;
import me.zhouzhuo.zzletterssidebar.entity.SortModel;

public class TitleImage  extends SortModel implements ModelType {

    String ident;

    @Letter(isSortField = false)
    String order;
    @Letter(isSortField = true)
    String title;

    int image;

    boolean checked;

    ModelType value;

    public TitleImage(String ident, String title, int image, ModelType value ) {
        this.ident = ident;
        this.title = title;
        this.image = image;
        this.value = value;

    }

    public String getOrder() {
        return order;
    }
    public void setOrder(String order) {
        this.order = order;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public ModelType getValue() {
        return value;
    }

    public void setValue(ModelType value) {
        this.value = value;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void toggle() {
        setChecked(!checked);
    }
}
