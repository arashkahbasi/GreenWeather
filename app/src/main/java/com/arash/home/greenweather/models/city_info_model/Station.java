package com.arash.home.greenweather.models.city_info_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Station {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("dist")
    @Expose
    private Integer dist;
    @SerializedName("kf")
    @Expose
    private Integer kf;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDist() {
        return dist;
    }

    public void setDist(Integer dist) {
        this.dist = dist;
    }

    public Integer getKf() {
        return kf;
    }

    public void setKf(Integer kf) {
        this.kf = kf;
    }
}
