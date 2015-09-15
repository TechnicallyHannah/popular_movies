package com.example.a712948.popularmovies.rest;

import com.example.a712948.popularmovies.DiscoverMovieResponse;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * @author Hannah Paulson
 * @since 9/10/15.
 */
public interface ApiService {
    @GET("/movie?sorted_by=vote_average.asc&api_key=fc47e47a86969055486f846572f8bf83")
    public void getContent(Callback<DiscoverMovieResponse> callback);
}
