package com.example.a712948.popularmovies.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Hannah Paulson
 * @since 9/16/15.
 */
public class Movies {
    @Expose
    public Integer page;
    @Expose
    public List<Result> results;
    @SerializedName("total_pages")
    @Expose
    public Integer totalPages;
    @SerializedName("total_results")
    @Expose
    public Integer totalResults;

}
