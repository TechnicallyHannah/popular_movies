package com.example.a712948.popularmovies.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hannah Paulson
 * @since 3/24/16.
 */
public class Trailers {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("quicktime")
    @Expose
    public List<Object> quicktime = new ArrayList<Object>();
    @SerializedName("youtube")
    @Expose
    public List<Youtube> youtube = new ArrayList<Youtube>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Object> getQuicktime() {
        return quicktime;
    }

    public void setQuicktime(List<Object> quicktime) {
        this.quicktime = quicktime;
    }

    public List<Youtube> getYoutube() {
        return youtube;
    }

    public void setYoutube(List<Youtube> youtube) {
        this.youtube = youtube;
    }

}
