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

import java.util.ArrayList;

/**
 * @author Hannah Paulson
 * @since 3/31/16.
 */
public class FavoriteAdapter extends ArrayAdapter{
    private Context context;
    private ArrayList<String> moviesList;
    @InjectView(R.id.movie_poster)
    ImageView poster_view;


    public FavoriteAdapter(Activity context, ArrayList moviesList) {
        super(context, 0, moviesList);
        this.context = context;
        this.moviesList = moviesList;
    }

    public View getView(int position, View view, ViewGroup parent) {
        String poster = null;
        if (moviesList.get(position) != null) {
            poster = moviesList.get(position);
        }
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.fragment_movie_grid, parent, false);
        }
        Log.i("Adapter",moviesList.get(position));

        ImageView poster_view = (ImageView) view.findViewById(R.id.image_holder);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w342/" + poster).into(poster_view);
        return view;
    }
}
