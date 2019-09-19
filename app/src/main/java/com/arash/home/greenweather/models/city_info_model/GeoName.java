package com.arash.home.greenweather.models.city_info_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeoName {
    @SerializedName("cl")
    @Expose
    private String cl;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("parent")
    @Expose
    private Long parent;

    public String getCl() {
        return cl;
    }

    public void setCl(String cl) {
        this.cl = cl;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }
}
