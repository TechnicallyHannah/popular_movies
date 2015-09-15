package com.example.a712948.popularmovies;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * @author Hannah Paulson
 * @since 9/2/15.
 */
public interface Retrofit {

    @GET("/movie?sorted_by=vote_average.asc&api_key=fc47e47a86969055486f846572f8bf83")
    void getMovies(Callback<Result> response);
}
