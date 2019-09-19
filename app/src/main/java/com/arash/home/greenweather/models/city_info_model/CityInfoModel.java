package com.arash.home.greenweather.models.city_info_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityInfoModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("coord")
    @Expose
    private Coordinate coord;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("geoname")
    @Expose
    private GeoName geoname;
    @SerializedName("langs")
    @Expose
    private List<String> langs;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("stat")
    @Expose
    private Statistics stat;
    @SerializedName("stations")
    @Expose
    private List<Station> stations;
    @SerializedName("zoom")
    @Expose
    private Integer zoom;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Coordinate getCoord() {
        return coord;
    }

    public void setCoord(Coordinate coord) {
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public GeoName getGeoname() {
        return geoname;
    }

    public void setGeoname(GeoName geoname) {
        this.geoname = geoname;
    }

    public List<String> getLangs() {
        return langs;
    }

    public void setLangs(List<String> langs) {
        this.langs = langs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Statistics getStat() {
        return stat;
    }

    public void setStat(Statistics stat) {
        this.stat = stat;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    public Integer getZoom() {
        return zoom;
    }

    public void setZoom(Integer zoom) {
        this.zoom = zoom;
    }
}
