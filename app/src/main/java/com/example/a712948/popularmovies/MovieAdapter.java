package com.example.a712948.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import butterknife.InjectView;
import com.example.a712948.popularmovies.POJO.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @author Hannah Paulson
 * @since 8/20/15.
 */
public class MovieAdapter extends ArrayAdapter<Result> {

    private Context context;
    private List<Result> moviesList;
    @InjectView(R.id.movie_poster)
    ImageView poster_view;


    public MovieAdapter(Activity context , List<Result> moviesList) {
        super(context, 0, moviesList);
        this.context = context;
        this.moviesList = moviesList;
    }

    public View getView(int position, View view, ViewGroup parent) {
        String poster = null;
        if (moviesList.get(position).getPosterPath() != null) {
            poster = moviesList.get(position).getPosterPath();
        }
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.fragment_movie_grid, parent, false);
        }
        ImageView poster_view = (ImageView) view.findViewById(R.id.image_holder);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w500/" + poster).into(poster_view);
        return view;
    }

}