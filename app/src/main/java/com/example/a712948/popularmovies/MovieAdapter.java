package com.example.a712948.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import butterknife.InjectView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @author Hannah Paulson
 * @since 8/20/15.
 */
public class MovieAdapter extends ArrayAdapter<DiscoverMovieResponse> {

    @InjectView(R.id.movie_poster)
    ImageView poster_view;


    public MovieAdapter(Activity context, List<DiscoverMovieResponse> movies) {
        super(context, 0, movies);
    }

    public View getView(int position, View view, ViewGroup parent) {
        DiscoverMovieResponse discoverMovieResponse =getItem(position);
        String poster = null;
        Context context = getContext();
        Log.i("Tag",position + "");
        Log.i("TAG", discoverMovieResponse.results.get(position).poster_path + " ");

        //if (movieResponse.poster_path != null) {
         //   poster = movieResponse.poster_path;
       // }
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.fragment_movie_grid, parent, false);
        }
        ImageView poster_view = (ImageView) view.findViewById(R.id.image_holder);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w500/" + poster).into(poster_view);
        return view;
    }
}