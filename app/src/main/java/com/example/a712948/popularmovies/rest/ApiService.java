package com.example.a712948.popularmovies.rest;

import com.example.a712948.popularmovies.POJO.MovieDetail;
import com.example.a712948.popularmovies.POJO.Movies;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * @author Hannah Paulson
 * @since 9/10/15.
 */
public interface ApiService {
    @GET("/discover/movie?sorted_by=vote_average.asc&api_key=")
    void getContent(Callback<Movies> callback);
    @GET("/movie/top_rated?api_key=")
    void getTopRated(Callback<Movies> callback);
    @GET("/movie/{id}?api_key=&append_to_response=trailers,reviews")
    void getDetails(@Path("id") String id, Callback<MovieDetail> callback);

}
