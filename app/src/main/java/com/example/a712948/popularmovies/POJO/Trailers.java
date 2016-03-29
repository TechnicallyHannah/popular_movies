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
    @SerializedName("quicktime")
    @Expose
    private List<Object> quicktime = new ArrayList<Object>();
    @SerializedName("youtube")
    @Expose
    private List<Youtube> youtube = new ArrayList<Youtube>();

    /**
     * @return The quicktime
     */
    public List<Object> getQuicktime() {
        return quicktime;
    }

    /**
     * @param quicktime The quicktime
     */
    public void setQuicktime(List<Object> quicktime) {
        this.quicktime = quicktime;
    }

    /**
     * @return The youtube
     */
    public List<Youtube> getYoutube() {
        return youtube;
    }

    /**
     * @param youtube The youtube
     */
    public void setYoutube(List<Youtube> youtube) {
        this.youtube = youtube;
    }
}
