package com.example.a712948.popularmovies.rest;

import com.example.a712948.popularmovies.POJO.Movies;
import com.example.a712948.popularmovies.POJO.Reviews;
import com.example.a712948.popularmovies.POJO.Trailers;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * @author Hannah Paulson
 * @since 9/10/15.
 */
public interface ApiService {
    @GET("/discover/movie?sorted_by=vote_average.asc&api_key=fc47e47a86969055486f846572f8bf83")
    void getContent(Callback<Movies> callback);
    @GET("/movie/top_rated?api_key=fc47e47a86969055486f846572f8bf83")
    void getTopRated(Callback<Movies> callback);
    @GET("/movie/{id}/trailers?api_key=fc47e47a86969055486f846572f8bf83")
    void getTrailers(@Path("id") String id, Callback<Trailers> callback);
    @GET("/movie/top_rated?api_key=fc47e47a86969055486f846572f8bf83")
    void getReviews(@Path("id") String id, Callback<Reviews> callback);
}
